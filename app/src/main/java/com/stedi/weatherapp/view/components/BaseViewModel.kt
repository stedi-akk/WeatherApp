package com.stedi.weatherapp.view.components

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel<V> : ViewModel() {
    var isInitialized: Boolean = false

    abstract fun onCreate(view: V)

    fun init(view: V) {
        if (!isInitialized) {
            onCreate(view)
            isInitialized = true
        }
    }
}