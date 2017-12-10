package com.bottle.track.map.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @Date 2017/12/5 14:48
 * @Author halfbottle
 * @Version 1.0.0
 * @Description Trek几何模型中的线，一条线有有序的坐标点相连组成
 */
public class Polyline implements Serializable{

    private GeoPoint startPoint; // 起点
    private GeoPoint endPoint;   // 终点
    private double distance;     // 总长度
    private ArrayList<GeoPoint> points;

    public Polyline(@NonNull ArrayList<GeoPoint> points){
        this.points = points;
        this.startPoint = points.get(0);
        this.endPoint = points.get(points.size() - 1);
        this.distance = 0.0; // 需要计算一下
    }

    public GeoPoint getStartPoint() {
        return startPoint;
    }

    public GeoPoint getEndPoint() {
        return endPoint;
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<GeoPoint> getPoints() {
        return points;
    }
}
