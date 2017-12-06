package com.bottle.track.user;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.bottle.track.BaseActivity;
import com.bottle.track.R;
import com.orhanobut.logger.Logger;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2017/12/2 0002.
 * @Description: QQ登录页面
 */

public class LoginActivity extends BaseActivity {

    private Tencent mTencent;
    private LoginListener loginListener;
    private boolean isLogin, isGetUserInfo;
    private String token, openid, expires, nickname, gender, figureurl, unionid;
    private final String TAG = this.getClass().getSimpleName();
    public static final String PAR_KRY = "LoginActivity";

   public static void start(BaseActivity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permission();
        qqLogin();
        if (isLogin == true) {
            getUserInfo();
            getQQUnionid();
            if (isGetUserInfo == true) {
                handleQQUserInfo();
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        } else {
            setResult(RESULT_CANCELED);
        }
    }
    //退出登录
    /*
        public void qqLoginOut() {
            mTencent.logout(this);
        }*/
    private void permission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
    }
    /**
     * 在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，登录成功后无法成功回传数据
     * 可在调用login的Activity或者Fragment重写onActivityResult方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_API) {
            if (requestCode == Constants.REQUEST_LOGIN) {
                mTencent.onActivityResultData(requestCode, resultCode, data, new LoginListener());
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /**
     * @Description: QQ登录, 校验登陆态
     */
    public void qqLogin() {
        loginListener = new LoginListener();
        mTencent = Tencent.createInstance(Config.QQ_LOGIN_APP_ID, this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, Config.QQ_SCOPE, loginListener);
        }
    }
    //实现回调,OpenAPI V3.0。
    class LoginListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Logger.i(TAG, "授权成功" + response.toString());
            try {
                JSONObject jsonObject = (JSONObject) response;
                token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                openid = jsonObject.getString(Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openid)) {
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openid);
                    isLogin = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onError(UiError uiError) {
            Logger.e(TAG, "登录失败" + uiError.toString());
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "QQ登录取消", Toast.LENGTH_LONG).show();
        }
    }

    private void getUserInfo() {
        //sdk提供一个类UserInfo，可以拿到QQ用户的一些信息.getUserInfo（）使用了异步方法调用
        UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
        if (mTencent != null && mTencent.isSessionValid()) {
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(final Object response) {
                    System.out.print("登录成功，返回用户信息：" + response.toString());
                    JSONObject jsonObject = (JSONObject) response;
                    if (((JSONObject) response).has("nickname")) {
                        try {
                            nickname = jsonObject.getString("nickname");
                            gender = jsonObject.getString("gender");
                            figureurl = jsonObject.getString("figureurl");
                            isGetUserInfo = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        isGetUserInfo = false;
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Logger.e(TAG, "获取qq用户信息错误：" + uiError.toString());
                    Toast.makeText(LoginActivity.this, "获取QQ用户信息失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Logger.e(TAG, "获取QQ用户信息取消");
                    Toast.makeText(LoginActivity.this, "获取QQ用户信息取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //返回QQ用户信息给调用者
    public void handleQQUserInfo() {
        QQUserInfo qqUserInfo = new QQUserInfo();
        qqUserInfo.setToken(token);
        qqUserInfo.setExpires(expires);
        qqUserInfo.setUnionid(unionid);
        qqUserInfo.setNickname(nickname);
        qqUserInfo.setGender(gender);
        qqUserInfo.setFigureurl(figureurl);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PAR_KRY, qqUserInfo);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    /**
     * 获取QQ的unionid
     */
    private void getQQUnionid() {
        final String unionidUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + token + "&unionid=1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(unionidUrl.trim());
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                            baos.flush();
                        }
                        is.close();
                        baos.close();
                        String s = baos.toString();
                        if (s.contains("unionid")) {
                            String[] split = s.split(":");
                            s = split[split.length - 1];
                            split = s.split("\"");
                            s = split[1];
                            unionid = s;
                        } else {
                            unionid = "NULL";
                        }
                    }
                } catch (IOException e) {
                    Logger.e(TAG, "获取unionid失败");
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
