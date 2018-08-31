package com.stedi.weatherapp.model.repository.impl

import com.stedi.weatherapp.model.data.weather.CityWeather
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class OWMWeatherRepository(private val apiKey: String) : WeatherRepository {

    private interface GetWeather {
        @GET("data/2.5/weather")
        fun call(@Query("appid") apiKey: String, @Query("q") cityName: String): Call<CityWeather>
    }

    override fun getWeather(cityName: String): CityWeather? {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val response = retrofit.create(GetWeather::class.java).call(apiKey, cityName).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw IOException()
        }
    }
}