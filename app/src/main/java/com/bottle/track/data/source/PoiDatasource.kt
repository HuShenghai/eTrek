package com.bottle.track.data.source

import com.bottle.track.core.db.schema.TrekPoi

/**
 * @Date 2018/1/2 17:05
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
interface PoiDatasource {

    fun savePoi(trekPoi: TrekPoi)

    fun updatePoi(trekPoi: TrekPoi)

    /**
     * 删除一个兴趣点
     * @param trekPoi
     */
    fun deletePoi(trekPoi: TrekPoi)

    /**
     * 获取单个兴趣点
     * @param id 轨迹id
     */
    fun getPoi(id: String)

    /**
     * 获取所有的兴趣点
     */
    fun getPois()

}