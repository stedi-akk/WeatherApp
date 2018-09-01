package com.stedi.weatherapp.view.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import com.stedi.weatherapp.R
import com.stedi.weatherapp.model.data.owmweather.CityWeather
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

    private val CITY_SEARCH_REQUEST_CODE = 111

    private lateinit var viewModel: WeatherActivityModel

    @BindView(R.id.weather_activity_iv_weather) lateinit var ivWeather: ImageView
    @BindView(R.id.weather_activity_tv_temperature) lateinit var tvTemperature: TextView
    @BindView(R.id.weather_activity_tv_description) lateinit var tvDescription: TextView
    @BindView(R.id.weather_activity_tv_more_weather_info) lateinit var tvMoreWeatherInfo: TextView
    @BindView(R.id.weather_activity_tv_city_name) lateinit var tvCityName: TextView

    private var cityWeather: CityWeather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherActivityModel::class.java)
        viewModel.init(this)

        setContentView(R.layout.weather_activity)
        ButterKnife.bind(this)

        findViewById<FloatingActionButton>(R.id.weather_activity_fab).setOnClickListener {
            startActivityForResult(Intent(this, CitySearchActivity::class.java), CITY_SEARCH_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CITY_SEARCH_REQUEST_CODE) {
            viewModel.presenter.getWeatherForSelectedCity()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.presenter.onAttach(this)
        if (cityWeather == null) {
            viewModel.presenter.getWeatherForSelectedCity()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.presenter.onDetach()
    }

    override fun showWeather(cityWeather: CityWeather) {
        this.cityWeather = cityWeather
        invalidate()
    }

    override fun showNoSelectedCityMessage() {
        cityWeather = null
        invalidate()
    }

    override fun showFailedToGetWeatherMessage() {
        cityWeather = null
        invalidate()
    }

    private fun invalidate() {
        val unknownValue = "??"

        val cityName = cityWeather?.name
        val weather = cityWeather?.weather?.firstOrNull()
        val iconUrl = weather?.iconUrl
        val temperature = cityWeather?.main?.temp ?: unknownValue
        val description = if (weather?.main != null && weather.description != null) {
            getString(R.string.weather_description, weather.main, weather.description)
        } else {
            getString(R.string.unknown)
        }
        val pressure = cityWeather?.main?.pressure ?: unknownValue
        val humidity = cityWeather?.main?.humidity ?: unknownValue
        val wind = cityWeather?.wind?.speed ?: unknownValue

        if (iconUrl != null) {
            Picasso.get().load(iconUrl).into(ivWeather)
        } else {
            ivWeather.setImageResource(R.drawable.ic_error)
        }

        tvTemperature.text = getString(R.string.temperature_celsius, temperature)
        tvDescription.text = description
        tvMoreWeatherInfo.text = getString(R.string.weather_more_info, pressure, humidity, wind)
        tvCityName.text = cityName ?: getString(R.string.no_selected_city)
    }
}