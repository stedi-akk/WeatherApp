package com.stedi.weatherapp.presenter.interfaces

import com.stedi.weatherapp.model.data.weather.CityWeather

interface WeatherPresenter : Presenter<WeatherPresenter.UIImpl> {

    fun getWeatherForSelectedCity()

    interface UIImpl : UI {
        fun showWeather(cityWeather: CityWeather)

        fun showNoSelectedCityMessage()

        fun showFailedToGetWeatherMessage()
    }
}