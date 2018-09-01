package com.stedi.weatherapp.model.data.owmweather

data class Weather(var id: Int?, var main: String?, var description: String?, var icon: String?) {

    var iconUrl: String? = null
        get() = if (icon != null) String.format("http://openweathermap.org/img/w/%s.png", icon) else null
}