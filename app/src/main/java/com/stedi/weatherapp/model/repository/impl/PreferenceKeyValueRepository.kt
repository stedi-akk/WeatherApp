package com.stedi.weatherapp.model.repository.impl

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.stedi.weatherapp.di.AppContext
import com.stedi.weatherapp.model.repository.interfaces.KeyValueRepository

class PreferenceKeyValueRepository(
        @AppContext private val context: Context) : KeyValueRepository {

    @SuppressLint("ApplySharedPref")
    @Synchronized
    override fun put(key: String, value: Any) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> return
        }
        editor.commit()
    }

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <R> get(key: String, default: R?): R? {
        return PreferenceManager.getDefaultSharedPreferences(context).all?.get(key) as R ?: default
    }
}