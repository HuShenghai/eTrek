package com.bottle.track.map.model;

/**
 * @Date 2017/12/5 15:03
 * @Author halfbottle
 * @Version 1.0.0
 * @Description Trek 中的POI，继承自GeoPoint，即包含坐标位置信息，自定义其它属性，如描述，地名等
 */
public class TrekPoi extends GeoPoint {

    public long logtime;         // 记录时间
    public String province;      // 省
    public String city;          // 市
    public String district;      // 区/县
    public String name;          // 地名
    public String description;   // 描述，提供给用户，作游记等

    public TrekPoi(double longitude, double latitude, double altitude) {
        super(longitude, latitude, altitude);
    }

}
