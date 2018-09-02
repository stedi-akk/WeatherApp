package com.stedi.weatherapp.model.repository.impl

import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository

// I like doing "performance" caching in model layer instead of business.
// Because, essentially, cache is just another source of data.
// I usually do this by using Decorator pattern, which allows to "attach" cache to any kind of repository.
class CachedCitiesRepository(private val target: CitiesRepository) : CitiesRepository {

    private var allCities: List<String> = emptyList()
    private var selectedCity: String? = null

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

    @Synchronized
    override fun getAll(): List<String> {
        if (allCities.isEmpty()) {
            allCities = target.getAll()
        }
        return allCities
    }
}