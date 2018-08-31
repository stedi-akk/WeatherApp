package com.stedi.weatherapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.stedi.weatherapp.di.AppComponent
import com.stedi.weatherapp.di.AppModule
import com.stedi.weatherapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}