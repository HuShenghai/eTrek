package com.bottle.track.core.db.converter;

import com.bottle.track.core.model.Track;
import com.bottle.util.GsonHelper;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
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
        Type type = new TypeToken<ArrayList<Track>>(){}.getType();
        Object obj = GsonHelper.Companion.getGsonHelper().toObject(databaseValue, type);
        return (ArrayList<Track>) obj;
    }

    @Override
    public String convertToDatabaseValue(ArrayList<Track> entityProperty) {
        return GsonHelper.Companion.getGsonHelper().toJson(entityProperty);
    }
}
