package com.stedi.weatherapp.model.repository.interfaces

import android.support.annotation.WorkerThread

interface CitiesRepository {

    @WorkerThread
    fun getAll(): List<String>

    @WorkerThread
    fun getSelected(): String?

    @WorkerThread
    fun setSelected(city: String)
}