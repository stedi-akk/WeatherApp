package com.stedi.weatherapp.presenter.impl

import com.google.gson.Gson
import com.stedi.weatherapp.di.DefaultScheduler
import com.stedi.weatherapp.di.UiScheduler
import com.stedi.weatherapp.model.data.owmweather.CityWeather
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.model.repository.interfaces.KeyValueRepository
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository
import com.stedi.weatherapp.other.NoNetworkException
import com.stedi.weatherapp.presenter.interfaces.WeatherPresenter
import rx.Scheduler
import rx.Single
import javax.inject.Inject

class WeatherPresenterImpl @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val citiesRepository: CitiesRepository,
        private val keyValueRepository: KeyValueRepository,
        @DefaultScheduler private val subscribeOn: Scheduler,
        @UiScheduler private val observeOn: Scheduler) : WeatherPresenter {

    private val KEY_LAST_WEATHER = "KEY_LAST_WEATHER"

    private var ui: WeatherPresenter.UIImpl? = null

    @Volatile private var selectedCity: String? = null

    override fun onAttach(ui: WeatherPresenter.UIImpl) {
        this.ui = ui
    }

    override fun onDetach() {
        this.ui = null
    }

    override fun getWeatherForSelectedCity() {
        Single.fromCallable {
            selectedCity = null
            val city = citiesRepository.getSelected() ?: throw Exception("no selected city")
            selectedCity = city
            val weather = try {
                weatherRepository.getWeather(city)
            } catch (ex: NoNetworkException) {
                null
            }
            if (weather != null) {
                keyValueRepository.put(KEY_LAST_WEATHER, Gson().toJson(weather))
                return@fromCallable weather
            } else {
                val json: String = keyValueRepository.get(KEY_LAST_WEATHER, null) ?: throw Exception("failed to get weather")
                return@fromCallable Gson().fromJson(json, CityWeather::class.java) ?: throw Exception("failed to get weather")
            }
        }.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe({ weather ->
                    ui?.showWeather(weather!!)
                }, { throwable ->
                    when (throwable.message) {
                        "no selected city" -> ui?.showNoSelectedCity()
                        else -> {
                            throwable.printStackTrace()
                            ui?.showFailedToGetWeather(selectedCity)
                        }
                    }
                })
    }
}