package com.bottle.track.core.db.schema;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * @Date 2017/12/5 15:28
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek 兴趣点的本地数据表
 */
@Entity(indexes = {@Index(value = "logtime DESC", unique = true)})
public class TrekPoi implements Serializable {

    public static final long serialVersionUID = 1l;

    @Id(autoincrement = true)
    private Long id;// 目前是必须的（see http://greenrobot.org/greendao/documentation/modelling-entities/）

    private double longitude;     // 经度
    private double latitude;      // 纬度
    private double altitude;      // 海拔
    private long logtime;         // 记录时间
    private String province;      // 省
    private String city;          // 市
    private String district;      // 区/县
    private String name;          // 地名
    private String title;         // 标题
    private String description;   // 描述，提供给用户，作游记等
    private String tags;          // 兴趣点添加标签

    TrekPoi(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    @Generated(hash = 714176516)
    public TrekPoi(Long id, double longitude, double latitude, double altitude, long logtime,
            String province, String city, String district, String name, String title,
            String description, String tags) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.logtime = logtime;
        this.province = province;
        this.city = city;
        this.district = district;
        this.name = name;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    @Generated(hash = 1905468925)
    public TrekPoi() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public long getLogtime() {
        return this.logtime;
    }

    public void setLogtime(long logtime) {
        this.logtime = logtime;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


}
