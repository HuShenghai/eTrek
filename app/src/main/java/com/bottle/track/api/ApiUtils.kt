package com.bottle.track.api

import com.bottle.track.api.request.UploadTrack
import com.bottle.track.map.model.TrekTrack

/**
 * @Date 2017/12/6 20:55
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */

fun convertToRequestBean(loginToken: String, trekTrack: TrekTrack): UploadTrack{
    return UploadTrack(loginToken,
            trekTrack.tracks,
            trekTrack.distance,
            trekTrack.time,
            trekTrack.speed,
            trekTrack.beginTime,
            trekTrack.endTime,
            trekTrack.description)
}