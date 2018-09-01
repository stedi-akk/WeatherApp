package com.stedi.weatherapp.model.data.owmweather

data class CityWeather(
        var id: Int?,
        var name: String?,
        var weather: List<Weather>?,
        var main: Main?,
        var wind: Wind?,
        var clouds: Clouds?)