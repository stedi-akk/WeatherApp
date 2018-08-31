package com.stedi.weatherapp.model.repository.impl

import android.content.Context
import com.stedi.weatherapp.di.AppContext
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository
import com.stedi.weatherapp.model.repository.interfaces.KeyValueRepository
import org.json.JSONObject

class JSONCitiesRepository(
        @AppContext private val context: Context,
        private val assetsJson: String,
        private val keyValueRepository: KeyValueRepository) : CitiesRepository {

    private val KEY_SELECTED_CITY = "KEY_SELECTED_CITY"

    override fun getSelected(): String? {
        return keyValueRepository.get(KEY_SELECTED_CITY, null)
    }

    override fun setSelected(city: String) {
        keyValueRepository.put(KEY_SELECTED_CITY, city)
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