package com.stedi.weatherapp.model.repository.interfaces

import android.support.annotation.WorkerThread
import com.stedi.weatherapp.model.data.weather.CityWeather

interface WeatherRepository {

    @WorkerThread
    @Throws(Exception::class)
    fun getWeather(cityName: String): CityWeather?
}