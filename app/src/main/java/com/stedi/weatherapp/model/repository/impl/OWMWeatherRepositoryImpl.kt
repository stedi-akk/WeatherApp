package com.stedi.weatherapp.model.repository.impl

import com.stedi.weatherapp.model.data.CityWeather
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository

class OWMWeatherRepositoryImpl(private val apiKey: String) : WeatherRepository {

    override fun getWeather(cityName: String): CityWeather? {
        return null
    }
}