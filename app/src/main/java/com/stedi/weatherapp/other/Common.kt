package com.stedi.weatherapp.other

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.util.TypedValue
import android.widget.Toast
import com.stedi.weatherapp.App
import com.stedi.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.getApp() = applicationContext as App

fun Context.hasNetworkConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false
    if (cm is ConnectivityManager) {
        val activeNetwork = cm.activeNetworkInfo ?: return false
        return activeNetwork.isConnectedOrConnecting
    } else {
        return false
    }
}

fun getInternalDrawablesFromOWMIcon(icon: String): Pair<Int, Int> {
    return when (icon) {
        "01d" -> R.drawable.ic_weather_sunny to R.drawable.background_sunny
        "01n" -> R.drawable.ic_weather_night to R.drawable.background_default
        "02d" -> R.drawable.ic_weather_partlycloudy to R.drawable.background_clouds
        "02n", "03d", "03n", "04d", "04n" -> R.drawable.ic_weather_cloudy to R.drawable.background_clouds
        "09d", "09n" -> R.drawable.ic_weather_pouring to R.drawable.background_rain
        "10d", "10n" -> R.drawable.ic_weather_rainy to R.drawable.background_rain
        "11d", "11n" -> R.drawable.ic_weather_lightning_rainy to R.drawable.background_rain
        "13d", "13n" -> R.drawable.ic_weather_snowy to R.drawable.background_snow
        "50d", "50n" -> R.drawable.ic_weather_fog to R.drawable.background_fog
        else -> R.drawable.ic_weather_error to R.drawable.background_default
    }
}

fun Context.dim2px(@DimenRes id: Int): Int {
    return resources.getDimensionPixelOffset(id)
}

fun Context.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.showToastLong(@StringRes id: Int) {
    Toast.makeText(this, id, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(@StringRes id: Int) {
    Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
}

fun Context.showToastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this == 1

fun formatTime(millis: Long): String = LazyCommon.dateFormat.format(Date(millis))

@SuppressLint("SimpleDateFormat")
private object LazyCommon {
    val dateFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("dd.MM.yyyy HH:mm")
    }
}