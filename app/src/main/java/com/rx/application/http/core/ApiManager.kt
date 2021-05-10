package com.rx.application.http.core

import io.reactivex.disposables.Disposable
import java.util.concurrent.ConcurrentHashMap

class ApiManager {
    companion object{
        private var instance: ApiManager? = null
        @Synchronized
        fun getInstance(): ApiManager?{
            if (instance == null){
                instance = ApiManager()
            }
            return instance
        }
    }

    private var arrayMaps: ConcurrentHashMap<Any, Disposable>? = ConcurrentHashMap()

    fun add(tag: Any?, disposable: Disposable?) {
        arrayMaps!![tag!!] = disposable!!
    }

    fun remove(tag: Any?) {
        if (!arrayMaps!!.isEmpty()) {
            arrayMaps!!.remove(tag!!)
        }
    }

    fun removeAll() {
        if (!arrayMaps!!.isEmpty()) {
            arrayMaps!!.clear()
        }
    }

    fun cancel(tag: Any?) {
        if (arrayMaps!!.isEmpty()) {
            return
        }
        if (arrayMaps!![tag!!] == null) {
            return
        }
        if (!arrayMaps!![tag]!!.isDisposed) {
            arrayMaps!![tag]!!.dispose()
            arrayMaps!!.remove(tag)
        }
    }

    fun cancelAll() {
        if (arrayMaps!!.isEmpty()) {
            return
        }
        val keys: Set<Any> = arrayMaps!!.keys
        for (apiKey in keys) {
            cancel(apiKey)
        }
    }
}