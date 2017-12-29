package com.bottle.track.core.model;

/**
 * @Date 2017/12/5 15:03
 * @Author halfbottle
 * @Version 1.0.0
 * @Description Trek 中的POI，继承自GeoPoint，即包含坐标位置信息，自定义其它属性，如描述，地名等
 */
public class TrekPoi extends GeoPoint {

    private long logtime;         // 记录时间
    private String province;      // 省
    private String city;          // 市
    private String district;      // 区/县
    private String name;          // 地名
    private String title;         // 标题
    private String description;   // 描述，提供给用户，作游记等
    private String tags;          // 兴趣点添加标签

    public TrekPoi(double longitude, double latitude, double altitude) {
        super(longitude, latitude, altitude);
    }

    public long getLogtime() {
        return logtime;
    }

    public void setLogtime(long logtime) {
        this.logtime = logtime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
