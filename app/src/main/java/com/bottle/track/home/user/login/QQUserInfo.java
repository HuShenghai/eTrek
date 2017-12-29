package com.bottle.track.home.user.login;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/3 0003.
 *
 * @Description: 登录成功，腾讯服务端返回的QQ用户信息
 */

public class QQUserInfo implements Parcelable {
    private String token;
    private String expires;
    private String openid;
    private String nickname;
    private String gender;
    private String figureurl;
    private String unionid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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


    public static final Creator<QQUserInfo> CREATOR = new Creator<QQUserInfo>() {
        @Override
        public QQUserInfo createFromParcel(Parcel source) {
            QQUserInfo qqUserInfo = new QQUserInfo();
            qqUserInfo.token = source.readString();
            qqUserInfo.expires = source.readString();
            qqUserInfo.openid = source.readString();
            qqUserInfo.nickname = source.readString();
            qqUserInfo.gender = source.readString();
            qqUserInfo.figureurl = source.readString();
            qqUserInfo.unionid = source.readString();
            return qqUserInfo;
        }

        @Override
        public QQUserInfo[] newArray(int size) {
            return new QQUserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(expires);
        dest.writeString(openid);
        dest.writeString(nickname);
        dest.writeString(gender);
        dest.writeString(figureurl);
        dest.writeString(unionid);
    }
}
