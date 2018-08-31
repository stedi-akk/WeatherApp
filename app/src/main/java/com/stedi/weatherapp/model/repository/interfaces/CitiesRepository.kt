package com.stedi.weatherapp.model.repository.interfaces

interface CitiesRepository {

    fun getAll(): List<String>
}