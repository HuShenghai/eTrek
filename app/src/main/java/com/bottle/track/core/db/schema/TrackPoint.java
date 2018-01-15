package com.bottle.track.core.db.schema;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName TrackPoint
 * @Author half_bottle
 * @Date 2018/1/15
 * @Description 记录轨迹的点的本地数据表，每次保存后删除所有点，不做多对多关联
 */
@Entity(indexes = {@Index(value = "logTime DESC", unique = true)})
public class TrackPoint {

    @Id
    private Long id;
    private double longitude;// 经度
    private double latitude; // 纬度
    private double altitude; // 海拔
    private long logTime;    // 记录的时间

    public TrackPoint(double longitude, double latitude, double altitude, long logTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.logTime = logTime;
    }

    @Generated(hash = 2053794853)
    public TrackPoint(Long id, double longitude, double latitude, double altitude,
            long logTime) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.logTime = logTime;
    }

    @Generated(hash = 546431815)
    public TrackPoint() {
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

    public long getLogTime() {
        return this.logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

}
