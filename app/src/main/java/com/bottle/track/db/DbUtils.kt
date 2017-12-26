package com.bottle.track.db

import com.bottle.track.db.schema.TrekPoi
import com.bottle.track.db.schema.TrekTrack

/**
 * @Date 2017/12/6 19:49
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
fun convertToDbClass(trekTrack: com.bottle.track.map.model.TrekTrack): TrekTrack{
    val trek = TrekTrack()
    trek.type = trekTrack.type
    trek.tracks = trekTrack.tracks
    trek.distance = trekTrack.distance
    trek.time = trekTrack.time
    trek.speed = trekTrack.speed
    trek.beginTime = trekTrack.beginTime
    trek.endTime = trekTrack.endTime
    trek.title = trekTrack.title
    trek.description = trekTrack.description
    return trek
}

fun convertToModelClass(trekTrack: TrekTrack): com.bottle.track.map.model.TrekTrack{
    return com.bottle.track.map.model.TrekTrack(trekTrack.tracks,
            trekTrack.beginTime,
            trekTrack.endTime,
            trekTrack.distance,
            trekTrack.time,
            trekTrack.speed,
            trekTrack.description)
}

fun convertToModelClass(trekPoi: TrekPoi): com.bottle.track.map.model.TrekPoi{
    val model = com.bottle.track.map.model.TrekPoi(trekPoi.longitude, trekPoi.latitude, trekPoi.altitude)
    model.logtime = trekPoi.logtime
    model.province = trekPoi.province
    model.city = trekPoi.city
    model.district = trekPoi.district
    model.name = trekPoi.name
    model.title = trekPoi.title
    model.description = trekPoi.description
    model.tags = trekPoi.tags
    return model
}
