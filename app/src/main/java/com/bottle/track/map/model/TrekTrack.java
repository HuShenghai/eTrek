package com.bottle.track.map.model;

import java.util.ArrayList;

/**
 * @Date 2017/12/5 15:07
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
public class TrekTrack {

    private ArrayList<Track> tracks;       // 过程中的多段
    private double distance;          // 总里程，单位米
    private int time;                 // 总耗时，单位秒
    private int speed;                // 平均速度，单位：m/s，显示的时候根据实际可以显示为km/s
    private long beginTime;           // 起始时间
    private long endTime;             // 结束时间
    public String description;        // 描述

    public TrekTrack(ArrayList<Track> tracks, long beginTime, long endTime, String description){
        this.tracks = tracks;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.time = (int)(endTime - beginTime) / 1000;
        this.speed = 0; // 需要 计算
    }
    public TrekTrack(ArrayList<Track> tracks,
                     long beginTime,
                     long endTime,
                     double distance,
                     int time,
                     int speed,
                     String description){
        this.tracks = tracks;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.time = time;
        this.speed = speed; // 需要 计算
        this.distance = distance;
    }
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public double getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }

    public int getSpeed() {
        return speed;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }
}
