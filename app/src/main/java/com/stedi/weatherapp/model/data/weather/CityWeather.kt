package com.stedi.weatherapp.model.data.weather

data class CityWeather(
        var id: Int?,
        var name: String?,
        var weather: List<Weather>?,
        var main: Main?,
        var visibility: Int?,
        var wind: Wind?,
        var clouds: Clouds?)