package com.stedi.weatherapp.presenter.interfaces

interface WeatherPresenter : Presenter<WeatherPresenter.UIImpl> {

    interface UIImpl : UI {

    }
}