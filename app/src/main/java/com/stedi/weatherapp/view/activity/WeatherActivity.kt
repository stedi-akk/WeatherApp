package com.stedi.weatherapp.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.stedi.weatherapp.model.data.weather.CityWeather
import com.stedi.weatherapp.other.getApp
import com.stedi.weatherapp.presenter.interfaces.WeatherPresenter
import com.stedi.weatherapp.view.components.BaseViewModel
import javax.inject.Inject

class WeatherActivityModel : BaseViewModel<WeatherActivity>() {
    @Inject lateinit var presenter: WeatherPresenter

    override fun onCreate(view: WeatherActivity) {
        view.getApp().appComponent.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        presenter.onDestroy()
    }
}

class WeatherActivity : BaseActivity(), WeatherPresenter.UIImpl {

    private lateinit var viewModel: WeatherActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherActivityModel::class.java)
        viewModel.init(this)
    }

    override fun showWeather(cityWeather: CityWeather) {

    }

    override fun showNoSelectedCityMessage() {

    }

    override fun showFailedToGetWeatherMessage() {

    }
}