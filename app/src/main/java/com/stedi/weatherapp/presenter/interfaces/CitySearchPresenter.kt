package com.stedi.weatherapp.presenter.interfaces

interface CitySearchPresenter : Presenter<CitySearchPresenter.UIImpl> {

    fun queryCity(query: String)

    fun select(cityName: String)

    interface UIImpl : UI {
        fun showResult(cities: List<String>)

        fun failedToQueryCity()

        fun onCitySelected(cityName: String)

        fun failedToSelectCity()
    }
}