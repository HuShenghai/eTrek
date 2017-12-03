package com.bottle.track.db.table

import com.bottle.track.map.model.Track

/**
 * @ClassName TrekTrack
 * @Author half_bottle
 * @Date 2017/12/3
 * @Description
 */
class TrekTrack {

    var tracks: List<Track>? = null       // 过程中的多段
    var distance: Double? = null          // 总里程
    var time: Int? = null                 // 总耗时，单位秒吧
    var speed: Int? = null                // 平均速度
    var beginTime: Long? = null           // 起始时间
    var endTime: Long? = null             // 结束时间
}