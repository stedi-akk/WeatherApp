package com.stedi.weatherapp.model.repository.interfaces

import android.support.annotation.WorkerThread

interface KeyValueRepository {

    @WorkerThread
    fun put(key: String, value: Any)

    @WorkerThread
    fun <R> get(key: String, default: R?): R?
}