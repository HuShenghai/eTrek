package com.bottle.track.user.result;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class LoginResult {

    private String login_token;
    private Long expire_time;
    private UserInfo user_info;

    public String getLoginToken() {
        return login_token;
    }

    public void setLoginToken(String login_token) {
        this.login_token = login_token;
    }

    public Long getExpireTime() {
        return expire_time;
    }

    public void setExpireTime(Long expire_time) {
        this.expire_time = expire_time;
    }

    public UserInfo getUserInfo() {
        return user_info;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.user_info = userInfo;
    }
}
