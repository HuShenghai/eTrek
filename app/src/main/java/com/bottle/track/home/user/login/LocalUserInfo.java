package com.bottle.track.home.user.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/7 0007.
 * 本地用户信息
 */
public class LocalUserInfo implements Parcelable {
    private String loginToken;
    private String openid;
    private String nickname;
    private String avatar_url;
    private String unionid;
    private String thirdType;
    private int gender;
    private int uid;
    private Long expires;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
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

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUnionid() { return unionid; }

    public void setUnionid(String unionid) { this.unionid = unionid; }

    public String getThirdType() { return thirdType; }

    public void setThirdType(String thirdType) { this.thirdType = thirdType; }

    public int getGender() { return gender; }

    public void setGender(int gender) { this.gender = gender; }

    public int getUid() { return uid; }

    public void setUid(int gender) { this.uid = uid; }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }


    public static final Creator<LocalUserInfo> CREATOR = new Creator<LocalUserInfo>() {
        @Override
        public LocalUserInfo createFromParcel(Parcel source) {
            LocalUserInfo localUserInfo = new LocalUserInfo();
            localUserInfo.loginToken = source.readString();
            localUserInfo.openid = source.readString();
            localUserInfo.nickname = source.readString();
            localUserInfo.avatar_url = source.readString();
            localUserInfo.unionid = source.readString();
            localUserInfo.thirdType = source.readString();
            localUserInfo.gender = source.readInt();
            localUserInfo.uid = source.readInt();
            localUserInfo.expires = source.readLong();

            return localUserInfo;
        }

        @Override
        public LocalUserInfo[] newArray(int size) {
            return new LocalUserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginToken);
        dest.writeString(openid);
        dest.writeString(nickname);
        dest.writeString(avatar_url);
        dest.writeString(unionid);
        dest.writeString(thirdType);
        dest.writeInt(gender);
        dest.writeInt(uid);
        dest.writeLong(expires);
    }
}
