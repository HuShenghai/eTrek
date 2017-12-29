package com.bottle.track.core.api.request

/**
 * @ClassName LoginUser
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description
 */
class LoginUser {
    var init_token: String? = null
    val thrid_type: String
    val openid: String
    val unionid: String
    val nickname: String
    val  avatar_url: String
    val gender: Int

    constructor(initToken: String,
                type: String,
                openId: String,
                unionId: String,
                nickname: String,
                avatarUrl: String,
                gender: Int){
        this.init_token = initToken
        this.thrid_type = type
        this.openid = openId
        this.unionid = unionId
        this.nickname = nickname
        this.avatar_url = avatarUrl
        this.gender = gender
    }
}