package com.bottle.track.user.result;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class UserInfo {

    private String openid;
    private String nickname;
    private String avatar_url;
    private String unionid;
    private String third_type;
    private int gender;
    private int uid;



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

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUnionid() { return unionid; }

    public void setUnionid(String unionid) { this.unionid = unionid; }

    public String getThirdType() { return third_type; }

    public void setThirdType(String thirdType) { this.third_type = thirdType; }

    public int getGender() { return gender; }

    public void setGender(int gender) { this.gender = gender; }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

}
