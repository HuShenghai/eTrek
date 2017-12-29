package com.bottle.track.core.db.schema;

import com.bottle.track.core.db.converter.TrekTrackConverter;
import com.bottle.track.core.model.Track;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import java.util.ArrayList;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @Date 2017/12/5 15:33
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek 本地轨迹数据表
 */
@Entity(indexes = {@Index(value = "beginTime DESC", unique = true)})
public class TrekTrack implements Serializable{

    public static final long serialVersionUID = 1l;

    @Id(autoincrement = true)
    private Long id; // 目前是必须的（see http://greenrobot.org/greendao/documentation/modelling-entities/）
    private int type;                  // 类型
    private double distance;           // 总里程
    private long time;                  // 总耗时，单位毫秒
    private int speed;                 // 平均速度
    private long beginTime;            // 起始时间
    private long endTime;              // 结束时间
    private String title;              // 标题
    private String description;        // 描述
    @Convert(columnType = String.class, converter = TrekTrackConverter.class)
    private ArrayList<Track> tracks;       // 过程中的多段
    @Generated(hash = 10847592)
    public TrekTrack(Long id, int type, double distance, long time, int speed, long beginTime,
            long endTime, String title, String description, ArrayList<Track> tracks) {
        this.id = id;
        this.type = type;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.tracks = tracks;
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
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public double getDistance() {
        return this.distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
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
    public ArrayList<Track> getTracks() {
        return this.tracks;
    }
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

}
