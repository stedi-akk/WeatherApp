package com.stedi.weatherapp.view.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.stedi.weatherapp.R
import com.stedi.weatherapp.model.data.owmweather.CityWeather
import com.stedi.weatherapp.other.getApp
import com.stedi.weatherapp.other.getInternalDrawableFromOWMIcon
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

class WeatherActivity : AppCompatActivity(), WeatherPresenter.UIImpl {

    private val CITY_SEARCH_REQUEST_CODE = 111

    enum class WeatherError {
        NO_SELECTED_CITY,
        NO_DATA_FOR_CITY,
        INTERNAL
    }

    private lateinit var viewModel: WeatherActivityModel

    @BindView(R.id.weather_activity_iv_weather) lateinit var ivWeather: ImageView
    @BindView(R.id.weather_activity_tv_temperature) lateinit var tvTemperature: TextView
    @BindView(R.id.weather_activity_tv_description) lateinit var tvDescription: TextView
    @BindView(R.id.weather_activity_tv_more_weather_info) lateinit var tvMoreWeatherInfo: TextView
    @BindView(R.id.weather_activity_tv_city_name) lateinit var tvCityName: TextView

    private var cityWeather: CityWeather? = null
    private var cityName: String? = null
    private var weatherError: WeatherError? = null

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
        this.cityName = cityWeather.name
        this.weatherError = null
        invalidate()
    }

    override fun showNoSelectedCity() {
        cityWeather = null
        cityName = null
        weatherError = WeatherError.NO_SELECTED_CITY
        invalidate()
    }

    override fun showFailedToGetWeather(forCity: String?) {
        cityWeather = null
        cityName = forCity
        weatherError = if (forCity != null) {
            WeatherError.NO_DATA_FOR_CITY
        } else {
            WeatherError.INTERNAL
        }
        invalidate()
    }

    private fun invalidate() {
        val unknownValue = "??"
        when (weatherError) {
            null -> {
                val weather = cityWeather?.weather?.firstOrNull()
                val icon = weather?.icon ?: unknownValue
                val temperature = cityWeather?.main?.temp ?: unknownValue
                val description = weather?.description ?: unknownValue
                val pressure = cityWeather?.main?.pressure ?: unknownValue
                val humidity = cityWeather?.main?.humidity ?: unknownValue
                val wind = cityWeather?.wind?.speed ?: unknownValue
                val cloudiness = cityWeather?.clouds?.all ?: unknownValue
                val cityName = cityName ?: unknownValue
                ivWeather.setImageResource(getInternalDrawableFromOWMIcon(icon))
                tvTemperature.text = getString(R.string.temperature_celsius, temperature)
                tvDescription.text = getString(R.string.weather_description, description)
                tvMoreWeatherInfo.text = getString(R.string.weather_more_info, cloudiness, pressure, humidity, wind)
                tvCityName.text = cityName
            }
            WeatherError.NO_SELECTED_CITY -> {
                ivWeather.setImageResource(R.drawable.ic_cloud_search)
                tvTemperature.text = getString(R.string.temperature_celsius, unknownValue)
                tvDescription.text = getString(R.string.no_selected_city)
                tvMoreWeatherInfo.text = ""
                tvCityName.text = getString(R.string.no_selected_city)
            }
            else -> {
                ivWeather.setImageResource(R.drawable.ic_cloud_question)
                tvTemperature.text = getString(R.string.temperature_celsius, unknownValue)
                tvDescription.text = cityName?.let { getString(R.string.no_weather_for, it) } ?: getString(R.string.failed_to_get_weather)
                tvMoreWeatherInfo.text = ""
                tvCityName.text = cityName ?: getString(R.string.no_selected_city)
            }
        }
    }
}