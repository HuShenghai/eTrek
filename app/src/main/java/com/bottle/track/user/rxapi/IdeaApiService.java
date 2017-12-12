package com.bottle.track.user.rxapi;


import com.bottle.track.user.BaseRequestBean;
import com.bottle.track.user.BaseResponseBean;
import com.bottle.track.user.request.QQUserInfo;
import com.bottle.track.user.result.InitLoginResult;
import com.bottle.track.user.result.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    String HOST = "http://z-smile.com";


    // 登录初始化
    @POST("/api/auth/init")
    Observable<BaseResponseBean<InitLoginResult>> authInit(@Body BaseRequestBean<String> baseRequestBean);

    // 登录
    @POST("/api/auth/login")
    Observable<BaseResponseBean<LoginResult>> login(@Body BaseRequestBean<QQUserInfo> baseRequestBean);

}
