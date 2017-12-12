package com.bottle.track.user.request;

/**
 * Created by Administrator on 2017/12/3 0003.
 *
 * @Description: 登录成功，腾讯服务端返回的QQ用户信息
 */

public class QQUserInfo {
    private String token;
    private String third_type;
    private String openid;
    private String nickname;
    private int gender;
    private String figureurl;
    private String unionid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getThirdType() {
        return third_type;
    }

    public void setThirdType(String third_type) {
        this.third_type = third_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getUnionid() {return unionid;}
    public void setUnionid(String unionid) {this.unionid = unionid;}

}
