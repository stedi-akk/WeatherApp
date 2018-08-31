package com.stedi.weatherapp.model.repository.interfaces

import android.support.annotation.WorkerThread

interface CitiesRepository {

    @WorkerThread
    @Throws(Exception::class)
    fun getAll(): List<String>

    @WorkerThread
    @Throws(Exception::class)
    fun getSelected(): String?

    @WorkerThread
    @Throws(Exception::class)
    fun setSelected(city: String)
}