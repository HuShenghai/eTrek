package com.bottle.track.map.model;

import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

/**
 * @Date 2017/12/5 14:54
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
public class Track extends Polyline implements Serializable{

    private long beginTime;     // 起始时间
    private long endTime;       // 结束时间
    private int speed;          // 速度，单位：m/s
    private int time;           // 耗时，单位：秒
    public String description;  // 描述

    public Track(@NonNull ArrayList<GeoPoint> points, long beginTime, long endTime){
        super(points);
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.speed = 0; // 需要计算
        this.time = (int)(endTime - beginTime) / 1000;
    }
}
