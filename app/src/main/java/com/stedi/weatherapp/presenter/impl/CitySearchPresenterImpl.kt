package com.stedi.weatherapp.presenter.impl

import com.stedi.weatherapp.di.DefaultScheduler
import com.stedi.weatherapp.di.UiScheduler
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter
import rx.Completable
import rx.Scheduler
import rx.Single
import javax.inject.Inject

class CitySearchPresenterImpl @Inject constructor(
        private val repository: CitiesRepository,
        @DefaultScheduler private val subscribeOn: Scheduler,
        @UiScheduler private val observeOn: Scheduler) : CitySearchPresenter {

    private var ui: CitySearchPresenter.UIImpl? = null

    override fun onAttach(ui: CitySearchPresenter.UIImpl) {
        this.ui = ui
    }

    override fun onDetach() {
        this.ui = null
    }

    override fun queryCity(query: String) {
        Single.fromCallable {
            repository.getAll().filter { it.startsWith(query, true) }
        }.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe({ result ->
                    ui?.showResult(result)
                }, { throwable ->
                    throwable.printStackTrace()
                    ui?.failedToQueryCity()
                })
    }

    override fun select(cityName: String) {
        Completable.fromCallable { repository.setSelected(cityName) }
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe({
                    ui?.onCitySelected(cityName)
                }, { throwable ->
                    throwable.printStackTrace()
                    ui?.failedToSelectCity()
                })
    }
}