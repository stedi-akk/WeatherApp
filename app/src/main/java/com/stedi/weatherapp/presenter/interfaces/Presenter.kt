package com.stedi.weatherapp.presenter.interfaces

import java.io.Serializable

interface UI

interface Presenter<in T : UI> {
    fun onAttach(ui: T)

    fun onDetach()

    // Presenters can be attached and detached many times, therefore onDestroy callback is essential in some cases.
    fun onDestroy() {}
}

// While current presenters don't store anything that is needed to be retained,
// this interface is still a core of presenters logic, and future presenters should use it for restoring state.
interface RetainedPresenter<in T : UI> : Presenter<T> {
    fun onRestore(state: Serializable)

    fun onRetain(): Serializable?
}