package com.stedi.weatherapp.di

import com.stedi.weatherapp.view.activity.CitySearchActivityModel
import com.stedi.weatherapp.view.activity.WeatherActivityModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class)])
@Singleton
interface AppComponent {
    fun inject(model: WeatherActivityModel)

    fun inject(model: CitySearchActivityModel)
}