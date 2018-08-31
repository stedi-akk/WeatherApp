package com.stedi.weatherapp.di

import android.content.Context
import com.stedi.weatherapp.App
import com.stedi.weatherapp.model.repository.impl.JSONCitiesRepositoryImpl
import com.stedi.weatherapp.model.repository.impl.OWMWeatherRepositoryImpl
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository
import com.stedi.weatherapp.presenter.impl.CitySearchPresenterImpl
import com.stedi.weatherapp.presenter.impl.WeatherPresenterImpl
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter
import com.stedi.weatherapp.presenter.interfaces.WeatherPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [(AppModule.Declarations::class)])
class AppModule(private val app: App) {

    @Provides
    @Singleton
    @AppContext
    fun provideAppContext(): Context = app

    @Provides
    @DefaultScheduler
    fun provideDefaultScheduler(): Scheduler = Schedulers.io()

    @Provides
    @UiScheduler
    fun provideAndroidScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository = OWMWeatherRepositoryImpl("aeb37c75289802db55ca23d32118b154")

    @Provides
    @Singleton
    fun provideCitiesRepository(@AppContext context: Context): CitiesRepository = JSONCitiesRepositoryImpl(context, "world-cities_json.json")

    @Module
    interface Declarations {
        @Binds
        fun provideWeatherPresenter(presenter: WeatherPresenterImpl): WeatherPresenter

        @Binds
        fun provideCitySearchPresenter(presenter: CitySearchPresenterImpl): CitySearchPresenter

    }
}