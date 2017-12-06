package com.bottle.track.api.request

/**
 * @ClassName QueryPoi
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description 查询POI请求参数
 * {baseinfo:{device_id: "lajfklaifefepi434oa"},reqbody: {query:{begintime:1111368701,endtime:1511368701}}}
 */
class QueryPoi {

    val query: Map<String, Any>

    constructor(begintime: Long, endtime: Long){
        this.query = mapOf("begintime" to begintime, "endtime" to endtime)
    }
}