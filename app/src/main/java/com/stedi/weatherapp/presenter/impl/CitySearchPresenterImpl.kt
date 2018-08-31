package com.stedi.weatherapp.presenter.impl

import com.stedi.weatherapp.di.DefaultScheduler
import com.stedi.weatherapp.di.UiScheduler
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter
import rx.Scheduler
import javax.inject.Inject

class CitySearchPresenterImpl @Inject constructor(
        private val repository: CitiesRepository,
        @DefaultScheduler private val subscribeOn: Scheduler,
        @UiScheduler private val observeOn: Scheduler) : CitySearchPresenter {

    override fun onAttach(ui: CitySearchPresenter.UIImpl) {
    }

    override fun onDetach() {
    }
}