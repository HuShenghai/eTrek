package com.bottle.track.data.source

import com.bottle.track.core.db.schema.TrekTrack

/**
 * @Date 2018/1/2 17:06
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
interface TrackDatasource {

    fun saveTrack(trekTrack: TrekTrack)

    fun updateTrack(trekTrack: TrekTrack)

    fun deleteTrack(trekTrack: TrekTrack)

}