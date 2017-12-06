package com.bottle.track.db.schema;

import com.bottle.track.db.converter.TrekTrackConverter;
import com.bottle.track.map.model.Track;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.ArrayList;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @Date 2017/12/5 15:33
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek 本地轨迹数据表
 */
@Entity(indexes = {@Index(value = "beginTime DESC", unique = true)})
public class TrekTrack {

    @Id(autoincrement = true)
    private Long id; // 目前是必须的（see http://greenrobot.org/greendao/documentation/modelling-entities/）

    @Convert(columnType = String.class, converter = TrekTrackConverter.class)
    public ArrayList<Track> tracks;       // 过程中的多段
    public double distance;          // 总里程
    public int time;                 // 总耗时，单位秒吧
    public int speed;                // 平均速度
    public long beginTime;           // 起始时间
    public long endTime;             // 结束时间
    @Generated(hash = 1831790689)
    public TrekTrack(Long id, ArrayList<Track> tracks, double distance, int time, int speed,
            long beginTime, long endTime) {
        this.id = id;
        this.tracks = tracks;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    @Generated(hash = 1236525096)
    public TrekTrack() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ArrayList<Track> getTracks() {
        return this.tracks;
    }
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
    public double getDistance() {
        return this.distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public long getBeginTime() {
        return this.beginTime;
    }
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }
    public long getEndTime() {
        return this.endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

}
