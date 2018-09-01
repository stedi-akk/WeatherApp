package com.stedi.weatherapp.view.components

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stedi.weatherapp.R

class CitiesAdapter(
        private val context: Context,
        private val listener: ClickListener) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val cities = ArrayList<String>()

    interface ClickListener {
        fun onClicked(cityName: String)
    }

    init {
        setHasStableIds(true)
    }

    fun set(cities: List<String>) {
        this.cities.clear()
        this.cities.addAll(cities)
        notifyDataSetChanged()
    }

    override fun getItemCount() = cities.size

    override fun getItemId(position: Int) = cities[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.city_item, parent, false) as TextView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        holder.city = city
        holder.textView.text = city
    }

    inner class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView), View.OnClickListener {
        var city: String = ""

        init {
            textView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener.onClicked(city)
        }
    }
}