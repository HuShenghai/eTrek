package com.bottle.track.api.result

import com.bottle.track.db.schema.TrekUser

/**
 * @ClassName LoginResult
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description
 */
class LoginResult {
    var login_token: String? = null
    var expire_time: Long? = null
    var user_info: TrekUser? = null
}