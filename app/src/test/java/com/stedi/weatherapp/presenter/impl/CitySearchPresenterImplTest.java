package com.stedi.weatherapp.presenter.impl;

import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository;
import com.stedi.weatherapp.presenter.interfaces.CitySearchPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

// The tests are written in Java, because
// it is painful to write them in Kotlin when using standard testing libraries.
public class CitySearchPresenterImplTest {
    private CitySearchPresenterImpl presenter;
    private CitiesRepository citiesRepository;
    private ArgumentCaptor<List<String>> citiesCaptor;

    private CitySearchPresenter.UIImpl ui;

    @Before
    public void before() {
        citiesRepository = mock(CitiesRepository.class);
        presenter = new CitySearchPresenterImpl(citiesRepository, Schedulers.immediate(), Schedulers.immediate());
        ui = mock(CitySearchPresenter.UIImpl.class);
        //noinspection unchecked
        citiesCaptor = ArgumentCaptor.forClass(List.class);
    }

    @Test
    public void testQueryCitySuccess() throws Exception {
        when(citiesRepository.getAll()).thenReturn(Arrays.asList("city1", "city2", "city3", "city4"));

        presenter.onAttach(ui);
        presenter.queryCity("abcd");

        verify(ui).showResult(citiesCaptor.capture());
        List<String> cities = citiesCaptor.getValue();
        assertTrue(cities.isEmpty());

        presenter.queryCity("city1");

        verify(ui, times(2)).showResult(citiesCaptor.capture());
        cities = citiesCaptor.getValue();
        assertTrue(cities.size() == 1);
        assertTrue(cities.get(0).equals("city1"));

        presenter.queryCity("city");

        verify(ui, times(3)).showResult(citiesCaptor.capture());
        cities = citiesCaptor.getValue();
        assertTrue(cities.size() == 4);
        assertTrue(cities.get(0).equals("city1"));
        assertTrue(cities.get(1).equals("city2"));
        assertTrue(cities.get(2).equals("city3"));
        assertTrue(cities.get(3).equals("city4"));

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testQueryCityFail() throws Exception {
        doThrow(new Exception("nope")).when(citiesRepository).getAll();

        presenter.onAttach(ui);
        presenter.queryCity("abcd");

        verify(ui).failedToQueryCity();

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testSelect() throws Exception {
        presenter.onAttach(ui);
        presenter.select("city1");

        verify(citiesRepository).setSelected("city1");
        verify(ui).onCitySelected("city1");

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testSelectFail() throws Exception {
        doThrow(new Exception("nope")).when(citiesRepository).setSelected(anyString());

        presenter.onAttach(ui);
        presenter.select("city1");

        verify(citiesRepository).setSelected("city1");
        verify(ui).failedToSelectCity();

        verifyNoMoreInteractions(ui);
    }
}
