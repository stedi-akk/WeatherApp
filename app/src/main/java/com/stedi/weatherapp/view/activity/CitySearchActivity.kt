package com.stedi.weatherapp.view.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.widget.RxTextView
import com.stedi.weatherapp.R
import com.stedi.weatherapp.other.dim2px
import com.stedi.weatherapp.other.getApp
import com.stedi.weatherapp.other.showToastLong
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter
import com.stedi.weatherapp.view.components.BaseViewModel
import com.stedi.weatherapp.view.components.CitiesAdapter
import com.stedi.weatherapp.view.components.ListSpaceDecoration
import java.util.concurrent.TimeUnit
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

class CitySearchActivity : AppCompatActivity(), CitySearchPresenter.UIImpl, CitiesAdapter.ClickListener {

    private val MINIMUM_QUERY_CHARS = 2

    private lateinit var viewModel: CitySearchActivityModel

    @BindView(R.id.city_search_activity_et_search) lateinit var searchField: EditText
    @BindView(R.id.city_search_activity_rv) lateinit var recyclerView: RecyclerView

    private lateinit var adapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CitySearchActivityModel::class.java)
        viewModel.init(this)

        setContentView(R.layout.city_search_activity)
        ButterKnife.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(ListSpaceDecoration(dim2px(R.dimen.common_v_spacing), dim2px(R.dimen.common_lr_spacing)))
        adapter = CitiesAdapter(this, this)
        recyclerView.adapter = adapter

        RxTextView.textChanges(searchField)
                .doOnNext { if (it.length < MINIMUM_QUERY_CHARS) adapter.set(emptyList()) }
                .filter { it.length >= MINIMUM_QUERY_CHARS }
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe { viewModel.presenter.queryCity(it.toString()) }
    }

    override fun onStart() {
        super.onStart()
        viewModel.presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        viewModel.presenter.onDetach()
    }

    override fun onClicked(cityName: String) {
        viewModel.presenter.select(cityName)
    }

    override fun showResult(cities: List<String>) {
        if (searchField.text.length >= MINIMUM_QUERY_CHARS) {
            adapter.set(cities)
        }
    }

    override fun failedToQueryCity() {
        showToastLong(R.string.failed_to_query_cities)
    }

    override fun onCitySelected(cityName: String) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun failedToSelectCity() {
        showToastLong(R.string.failed_to_select_city)
    }
}