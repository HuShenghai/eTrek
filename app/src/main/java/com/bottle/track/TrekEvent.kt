package com.bottle.track

class TrekEvent<T>(type: Int, description: String, event: T) {

    var type: Int? = 0
    private set

    var info: String? = "event bus"
    private set

    var event: T? = null
    private set

    init{
        this.type = type
        this.info = description
        this.event = event
    }
}