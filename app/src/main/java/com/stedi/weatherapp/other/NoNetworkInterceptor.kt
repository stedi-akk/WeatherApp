package com.stedi.weatherapp.other

import android.content.Context
import com.stedi.weatherapp.di.AppContext
import okhttp3.Interceptor
import okhttp3.Response

class NoNetworkInterceptor(@AppContext private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.hasNetworkConnection()) {
            throw NoNetworkException()
        }
        return chain.proceed(chain.request())
    }
}