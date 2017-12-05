package com.bottle.track.map.model;

/**
 * @Date 2017/12/5 14:45
 * @Author halfbottle
 * @Version 1.0.0
 * @Description Trek几何模型中的点，球坐标表示(lng, lat, alt)
 */
public class GeoPoint {

    private double longitude;// 经度
    private double latitude; // 纬度
    private double altitude; // 海拔

    public GeoPoint(double longitude, double latitude, double altitude){
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }
}
