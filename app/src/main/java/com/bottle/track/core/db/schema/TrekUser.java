package com.bottle.track.core.db.schema;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Date 2017/12/5 15:32
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek 本地用户数据表
 */
@Entity(indexes = {@Index(value = "uid DESC", unique = true)})
public class TrekUser {

    @Id(autoincrement = true)
    private Long id;// 目前是必须的（see http://greenrobot.org/greendao/documentation/modelling-entities/）

    private String init_token;
    private String thrid_type;
    private String openid;
    private String nickname;
    private String avatar_url;
    private int gender;
    private String unionid;

    @Index(unique = true)
    public int uid; // uid 唯一

    @Generated(hash = 333693279)
    public TrekUser(Long id, String init_token, String thrid_type, String openid, String nickname,
            String avatar_url, int gender, String unionid, int uid) {
        this.id = id;
        this.init_token = init_token;
        this.thrid_type = thrid_type;
        this.openid = openid;
        this.nickname = nickname;
        this.avatar_url = avatar_url;
        this.gender = gender;
        this.unionid = unionid;
        this.uid = uid;
    }

    @Generated(hash = 403295737)
    public TrekUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInit_token() {
        return this.init_token;
    }

    public void setInit_token(String init_token) {
        this.init_token = init_token;
    }

    public String getThrid_type() {
        return this.thrid_type;
    }

    public void setThrid_type(String thrid_type) {
        this.thrid_type = thrid_type;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return this.avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

  
}
