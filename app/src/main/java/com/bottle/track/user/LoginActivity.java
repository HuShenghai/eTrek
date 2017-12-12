package com.bottle.track.user;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bottle.track.BaseActivity;
import com.bottle.track.R;
import com.bottle.track.api.Api;
import com.bottle.track.user.request.QQUserInfo;
import com.bottle.track.user.rxapi.IdeaApiService;
import com.orhanobut.logger.Logger;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/2 0002.
 * QQ登录页面
 */

public class LoginActivity extends BaseActivity {

    private Tencent mTencent;
    private LoginListener loginListener;
    //QQ用户信息
    private String qqToken, qqOpenid, qqNickname, qqExpires, qqAvatar_url, qqUnionid;
    private int qqGender;//0-未知，1-男，2-女
    //本地用户信息
    private String loginToken, openid, nickname, avatar_url, unionid, thirdType, initToken;
    private Long expires;
    private int gender, uid;

    private final String TAG = this.getClass().getSimpleName();
    public static final String PAR_KRY = "LoginActivity";


    public static void start(BaseActivity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    /* 获取QQ用户信息成功，该页面就post用户信息给本地服务器。（2次握手请求）
        获取本地服务器返回的令牌和过期时间(令牌Token是QQ客户端的标识，不可变。但可自定义该令牌设置有效期)。
        再把本地服务器返回的结果回调给“设置”界面  */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permission();
        qqLogin();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //退出登录
    /*
        public void qqLoginOut() {
            mTencent.logout(this);
        }*/
    private void permission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, new LoginListener());
    }

    //QQ登录, 校验登陆态
    public void qqLogin() {
        loginListener = new LoginListener();
        mTencent = Tencent.createInstance(Constants.QQ_LOGIN_APP_ID, this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, Constants.QQ_SCOPE, loginListener);
        }
    }

    private QQUserInfo qqUserInfo = new QQUserInfo();
    //实现回调,OpenAPI V3.0。
    class LoginListener implements IUiListener {
        @Override
        public void onComplete(Object response) {

            Logger.d(TAG, "授权成功" + response.toString());
            try {
                JSONObject jsonObject = (JSONObject) response;
                qqToken = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                qqExpires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                qqOpenid = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(qqToken) && !TextUtils.isEmpty(qqExpires)
                        && !TextUtils.isEmpty(qqOpenid)) {
                    //若要设置自定义的token过期时间，在这里设置本地用户返回的expires即可。
                    mTencent.setAccessToken(qqToken, qqExpires);
                    mTencent.setOpenId(qqOpenid);
                    qqUserInfo.setToken(qqToken);
                    qqUserInfo.setOpenid(qqOpenid);
                    getUserInfo();
                }
            } catch (Exception e) {
                //这里还要做网络异常处理
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onError(UiError uiError) {
            //这里要做错误情况处理
            Logger.e(TAG, "登录失败" + uiError.toString());
            setResult(RESULT_CANCELED);
            finish();
        }

        @Override
        public void onCancel() {
            //例如登录过程中，按了返回键。
            Toast.makeText(LoginActivity.this, "QQ登录取消", Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void getUserInfo() {
        //sdk提供一个类UserInfo，可以拿到QQ用户的一些信息.getUserInfo（）使用了异步方法调用
        UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
        if (mTencent != null && mTencent.isSessionValid()) {
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(final Object response) {
                    Log.d(TAG, "登录成功：" + Thread.currentThread().getName().toString());
                    Toast.makeText(LoginActivity.this, "登录成功" + response.toString(), Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = (JSONObject) response;
                    if (((JSONObject) response).has("nickname")) {
                        try {
                            qqNickname = jsonObject.getString("nickname");
                            String stringGender = jsonObject.getString("gender");
                            qqAvatar_url = jsonObject.getString("figureurl");
                            if (!stringGender.isEmpty()) {
                                if (stringGender.equals("男")) {
                                    qqGender = 1;
                                } else {
                                    qqGender = 2;
                                }
                            } else {
                                qqGender = 0;
                            }

                            qqUserInfo.setNickname(qqNickname);
                            qqUserInfo.setGender(qqGender);
                            qqUserInfo.setFigureurl(qqAvatar_url);
                            getQQUnionid();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger.e(TAG, e.getMessage());
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Logger.e(TAG, "获取qq用户信息错误：" + uiError.toString());
                    Toast.makeText(LoginActivity.this, "获取QQ用户信息失败", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                }

                @Override
                public void onCancel() {
                    Logger.e(TAG, "获取QQ用户信息取消");
                    Toast.makeText(LoginActivity.this, "获取QQ用户信息取消", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
    }

    /**
     * 获取QQ的unionid
     */
    private void getQQUnionid() {
        //这种方法不确定能不能获取unoinid，但只要程序运行到这里，就给unionid设一个假数据NULL用于后面与本地服务器的测试
        qqUnionid = "NULL";
     /*   final String unionidUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + qqToken + "&unionid=1";
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
                            qqUnionid = s;
                        }
                    }
                } catch (IOException e) {
                    Logger.e(TAG, "获取unionid失败");
                    e.printStackTrace();
                }
            }
        }).start();*/
        qqUserInfo.setUnionid(unionid);
       // mTencent.logout(this);
        connectLocal();


    }

    private IdeaApiService httpService = new Api().getHttpServiceUser();

    //与本地服务器进行二次握手连接，初始登录获取的init_token在2分钟内有效。第二次连接获取用户数据。
    @SuppressWarnings("all")
    private void connectLocal() {
        String firstConnect = "InitLoading";
       httpService.authInit(new com.bottle.track.user.BaseRequestBean<String>(firstConnect))//
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseBean<com.bottle.track.user.result.InitLoginResult>>() {
                    @Override
                    public void accept(com.bottle.track.user.BaseResponseBean<com.bottle.track.user.result.InitLoginResult> b) throws Exception {
                        Toast.makeText(getApplicationContext(), "本地取数据连接成功"
                                + b.getRescode() + b.getInfo(), Toast.LENGTH_LONG).show();
                        if (b.getInfo().contains("success")) {
                            initToken = b.getResult().getInitToken();
                            qqUserInfo.setToken(initToken);
                            qqUserInfo.setThirdType(THIRD_TYPE);
                            connectSecondTime();
                            Logger.e("初次登录：" + "initToken"
                                    + initToken + "返回码" + b.getRescode() + b.getInfo());
                        }
                    }
                },new Consumer<Throwable>() {
           @Override
           public void accept(Throwable throwable) throws Exception {

           }
       });

    }

    public static final String THIRD_TYPE = "qq";

    @SuppressWarnings("all")
    private void connectSecondTime() {
        httpService.login(new com.bottle.track.user.BaseRequestBean<QQUserInfo>(qqUserInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseBean<com.bottle.track.user.result.LoginResult>>() {
                    @Override
                    public void accept(com.bottle.track.user.BaseResponseBean<com.bottle.track.user.result.LoginResult> b) throws Exception {
                        Toast.makeText(getApplicationContext(), "本地取数据连接成功"
                                + b.getRescode() + b.getInfo(), Toast.LENGTH_LONG).show();
                        Logger.e(TAG, "第二次连接本地状态：" + b.getInfo());
                        if (b.getInfo().contains("success")) {
                            com.bottle.track.user.result.LoginResult result = b.getResult();
                            loginToken = result.getLoginToken();
                            expires = result.getExpireTime();
                            com.bottle.track.user.result.UserInfo userInfo = result.getUserInfo();
                            uid = userInfo.getUid();
                            gender = userInfo.getGender();
                            thirdType = userInfo.getThirdType();
                            openid = userInfo.getOpenid();
                            nickname = userInfo.getNickname();
                            avatar_url = userInfo.getAvatarUrl();
                            unionid = userInfo.getUnionid();

                            //返回本地用户信息给调用者
                            LocalUserInfo localUserInfo = new LocalUserInfo();
                            localUserInfo.setLoginToken(loginToken);
                            localUserInfo.setExpires(expires);
                            localUserInfo.setUnionid(unionid);
                            localUserInfo.setOpenid(openid);
                            localUserInfo.setNickname(nickname);
                            localUserInfo.setGender(gender);
                            localUserInfo.setAvatarUrl(avatar_url);
                            localUserInfo.setThirdType(thirdType);
                            localUserInfo.setUid(uid);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(PAR_KRY, localUserInfo);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                        }
                        finish();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
