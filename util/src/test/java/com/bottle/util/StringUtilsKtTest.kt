package com.bottle.util

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by lenovo on 2017/10/19.
 */
class StringUtilsKtTest{

    @Test fun testIsEmpty(){
        var emptyString: String? = null
        val notEmptyString = "hello world"
        assert(isEmpty(emptyString))
        assert(!isEmpty(notEmptyString))
    }
}