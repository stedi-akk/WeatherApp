package com.stedi.weatherapp.model.repository.impl

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.stedi.weatherapp.di.AppContext
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import org.json.JSONObject

class JSONCitiesRepositoryImpl(
        @AppContext private val context: Context,
        private val assetsJson: String) : CitiesRepository {

    private val KEY_SELECTED_CITY = "KEY_SELECTED_CITY"

    override fun getSelected(): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_SELECTED_CITY, null)
    }

    @SuppressLint("ApplySharedPref")
    override fun setSelected(city: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(KEY_SELECTED_CITY, city).commit()
    }

    override fun getAll(): List<String> {
        context.assets.open(assetsJson).use {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            val jsonArray = JSONObject(java.lang.String(buffer, "UTF-8").toString()).getJSONArray("data")
            val list = ArrayList<String>(jsonArray.length())
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.getJSONObject(i).getString("name"))
            }
            return list
        }
    }
}