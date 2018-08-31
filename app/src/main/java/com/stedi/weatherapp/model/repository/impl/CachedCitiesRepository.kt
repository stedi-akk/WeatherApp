package com.stedi.weatherapp.model.repository.impl

import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository

class CachedCitiesRepository(private val target: CitiesRepository) : CitiesRepository {

    private var allCities: List<String> = emptyList()
    private var selectedCity: String? = null

    @Synchronized
    override fun getAll(): List<String> {
        if (allCities.isEmpty()) {
            allCities = target.getAll()
        }
        return allCities
    }

    @Synchronized
    override fun getSelected(): String? {
        if (selectedCity == null) {
            selectedCity = target.getSelected()
        }
        return selectedCity
    }

    @Synchronized
    override fun setSelected(city: String) {
        target.setSelected(city)
        selectedCity = target.getSelected()
    }
}