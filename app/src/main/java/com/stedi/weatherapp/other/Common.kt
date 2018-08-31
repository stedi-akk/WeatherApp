package com.stedi.weatherapp.other

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.util.TypedValue
import android.widget.Toast
import com.stedi.weatherapp.App
import java.text.SimpleDateFormat
import java.util.*

fun Context.getApp() = applicationContext as App

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