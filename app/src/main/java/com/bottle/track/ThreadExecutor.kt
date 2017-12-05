package com.bottle.track

import android.util.Log

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @Date 2017/12/5 15:33
 * @Author halfbottle
 * @Version 1.0.0
 * @Description eTrek 线程池
 */
class ThreadExecutor private constructor() {

    private val TAG = ThreadExecutor::class.java.simpleName
    private var executorService: ExecutorService? = null

    init {
        if (this.executorService == null) {
            val count = Math.min(3, (Runtime.getRuntime().availableProcessors() * 1.2 + 1).toInt())
            this.executorService = ThreadPoolExecutor(
                    count,
                    count,
                    10000,
                    TimeUnit.MILLISECONDS, LinkedBlockingQueue())
        }
    }

    fun doTask(task: Runnable) {
        if (!executorService!!.isShutdown) {
            this.executorService!!.execute(task)
        } else {
            Log.i(TAG, "ExecutorService was already shutdown!")
        }
    }

    fun <T> doTask(task: Callable<T>): Future<T> {
        val future = FutureTask(task)
        this.executorService!!.execute(future)
        return future
    }

    fun shutdown() {
        this.executorService!!.shutdown()
    }

    companion object {
        private var instance: ThreadExecutor? = null

        fun defaultInstance(): ThreadExecutor {
            if (instance == null)
                instance = ThreadExecutor()
            return instance!!
        }
    }
}
