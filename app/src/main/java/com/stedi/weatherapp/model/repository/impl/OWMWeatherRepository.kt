package com.stedi.weatherapp.model.repository.impl

import android.content.Context
import com.stedi.weatherapp.R
import com.stedi.weatherapp.di.AppContext
import com.stedi.weatherapp.model.data.owmweather.CityWeather
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
        @AppContext private val context: Context,
        private val apiKey: String,
        private val interceptors: List<Interceptor> = emptyList()) : WeatherRepository {

    private interface GetWeather {
        @GET("data/2.5/weather")
        fun call(@Query("appid") apiKey: String, @Query("q") cityName: String, @Query("units") units: String, @Query("lang") lang: String): Call<CityWeather>
    }

    override fun getWeather(cityName: String): CityWeather? {
        val client = OkHttpClient.Builder()
        client.connectTimeout(5, TimeUnit.SECONDS)
        client.readTimeout(5, TimeUnit.SECONDS)
        client.writeTimeout(5, TimeUnit.SECONDS)
        interceptors.forEach { client.addInterceptor(it) }

        val retrofit = Retrofit.Builder()
                .client(client.build())
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val response = retrofit.create(GetWeather::class.java)
                .call(apiKey, cityName, "metric", context.getString(R.string.openweathermap_lang)).execute()

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw IOException()
        }
    }
}