package com.stedi.weatherapp.model.repository.interfaces

import com.stedi.weatherapp.model.data.CityWeather

interface WeatherRepository {

    fun getWeather(cityName: String): CityWeather?
}