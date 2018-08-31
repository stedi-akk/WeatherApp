package com.stedi.weatherapp.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.stedi.weatherapp.other.getApp
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter
import com.stedi.weatherapp.view.components.BaseViewModel
import javax.inject.Inject

class CitySearchActivityModel : BaseViewModel<CitySearchActivity>() {
    @Inject lateinit var presenter: CitySearchPresenter

    override fun onCreate(view: CitySearchActivity) {
        view.getApp().appComponent.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        presenter.onDestroy()
    }
}

class CitySearchActivity : BaseActivity(), CitySearchPresenter.UIImpl {

    private lateinit var viewModel: CitySearchActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CitySearchActivityModel::class.java)
        viewModel.init(this)
    }
}