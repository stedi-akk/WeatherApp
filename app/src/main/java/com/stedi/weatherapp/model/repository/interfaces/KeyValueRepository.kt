package com.stedi.weatherapp.model.repository.interfaces

import android.support.annotation.WorkerThread

interface KeyValueRepository {

    @WorkerThread
    @Throws(Exception::class)
    fun put(key: String, value: Any)

    @WorkerThread
    @Throws(Exception::class)
    fun <R> get(key: String, default: Any?): R?
}