package com.stedi.weatherapp.model.repository.impl

import android.content.Context
import com.stedi.weatherapp.di.AppContext
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository

class JSONCitiesRepositoryImpl(
        @AppContext private val context: Context,
        private val assetsJson: String) : CitiesRepository {

    override fun getAll(): List<String> {
        return emptyList()
    }
}