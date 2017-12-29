package com.bottle.track.core.model;

import com.bottle.track.R;

/**
 * @ClassName TrackType
 * @Author halfbottle
 * @Date 2017/12/10
 * @Description 轨迹类型：徒步、驾车、自由行
 */
public enum TrackType {

    PEDESTRIANISM(1, R.string.pedestrianism),  // 徒步
    DRIVE(2, R.string.drive),                  // 驾车
    FREE_STROKE(3, R.string.free_stroke);      // 自由行

    private int type;
    private int name;

    private TrackType(int type, int name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public int getName() {
        return name;
    }
}
