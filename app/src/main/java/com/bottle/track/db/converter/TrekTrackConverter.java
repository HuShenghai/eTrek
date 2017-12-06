package com.bottle.track.db.converter;

import com.bottle.track.map.model.Track;
import com.bottle.util.GsonHelper;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;

/**
 * @Date 2017/12/5 15:55
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek ArrayList<Track> 类型与数据库String的转换
 */
public class TrekTrackConverter implements PropertyConverter<ArrayList<Track>, String> {

    @Override
    public ArrayList<Track> convertToEntityProperty(String databaseValue) {
        return GsonHelper.Companion.getGsonHelper().toAny(databaseValue, new ArrayList<Track>().getClass());
    }

    @Override
    public String convertToDatabaseValue(ArrayList<Track> entityProperty) {
        return GsonHelper.Companion.getGsonHelper().toJson(entityProperty);
    }
}
