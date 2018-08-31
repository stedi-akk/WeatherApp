package com.stedi.weatherapp.presenter.impl

import com.stedi.weatherapp.di.DefaultScheduler
import com.stedi.weatherapp.di.UiScheduler
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository
import com.stedi.weatherapp.presenter.interfaces.WeatherPresenter
import rx.Scheduler
import javax.inject.Inject

class WeatherPresenterImpl @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val citiesRepository: CitiesRepository,
        @DefaultScheduler private val subscribeOn: Scheduler,
        @UiScheduler private val observeOn: Scheduler) : WeatherPresenter {

    override fun onAttach(ui: WeatherPresenter.UIImpl) {
    }

    override fun onDetach() {

    }
}