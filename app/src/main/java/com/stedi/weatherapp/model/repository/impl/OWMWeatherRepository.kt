package com.stedi.weatherapp.model.repository.impl

import com.stedi.weatherapp.model.data.weather.CityWeather
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

class OWMWeatherRepository(
        private val apiKey: String,
        private val interceptor: Interceptor? = null) : WeatherRepository {

    private interface GetWeather {
        @GET("data/2.5/weather")
        fun call(@Query("appid") apiKey: String, @Query("q") cityName: String): Call<CityWeather>
    }

    override fun getWeather(cityName: String): CityWeather? {
        val client = OkHttpClient.Builder()
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        if (interceptor != null) {
            client.addInterceptor(interceptor)
        }

        val retrofit = Retrofit.Builder()
                .client(client.build())
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