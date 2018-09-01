package com.stedi.weatherapp.other

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
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

@DrawableRes
fun getInternalDrawableFromOWMIcon(icon: String): Int {
    return when (icon) {
        "01d" -> R.drawable.ic_weather_sunny
        "01n" -> R.drawable.ic_weather_night
        "02d" -> R.drawable.ic_weather_partlycloudy
        "02n", "03d", "03n", "04d", "04n" -> R.drawable.ic_weather_cloudy
        "09d", "09n" -> R.drawable.ic_weather_pouring
        "10d", "10n" -> R.drawable.ic_weather_rainy
        "11d", "11n" -> R.drawable.ic_weather_lightning_rainy
        "13d", "13n" -> R.drawable.ic_weather_snowy
        "50d", "50n" -> R.drawable.ic_weather_fog
        else -> R.drawable.ic_weather_error
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