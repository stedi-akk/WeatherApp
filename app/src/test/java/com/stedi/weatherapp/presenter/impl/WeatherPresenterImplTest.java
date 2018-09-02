package com.stedi.weatherapp.presenter.impl;

import com.google.gson.Gson;
import com.stedi.weatherapp.model.data.owmweather.CityWeather;
import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository;
import com.stedi.weatherapp.model.repository.interfaces.KeyValueRepository;
import com.stedi.weatherapp.model.repository.interfaces.WeatherRepository;
import com.stedi.weatherapp.other.NoNetworkException;
import com.stedi.weatherapp.presenter.interfaces.WeatherPresenter;

import org.junit.Before;
import org.junit.Test;

import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

// The tests are written in Java, because
// it is painful to write them in Kotlin when using standard testing libraries.
public class WeatherPresenterImplTest {
    private WeatherPresenterImpl presenter;
    private WeatherRepository weatherRepository;
    private CitiesRepository citiesRepository;
    private KeyValueRepository keyValueRepository;

    private WeatherPresenter.UIImpl ui;

    @Before
    public void before() {
        weatherRepository = mock(WeatherRepository.class);
        citiesRepository = mock(CitiesRepository.class);
        keyValueRepository = mock(KeyValueRepository.class);
        presenter = new WeatherPresenterImpl(weatherRepository, citiesRepository, keyValueRepository, Schedulers.immediate(), Schedulers.immediate());
        ui = mock(WeatherPresenter.UIImpl.class);
    }

    @Test
    public void testGetWeatherNoCity() throws Exception {
        doReturn(null).when(citiesRepository).getSelected();

        presenter.onAttach(ui);
        presenter.getWeatherForSelectedCity();

        verify(ui).showNoSelectedCity();
        verify(keyValueRepository, times(0)).put(anyString(), any());

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testGetWeatherNoData() throws Exception {
        when(citiesRepository.getSelected()).thenReturn("city");
        when(weatherRepository.getWeather("city")).thenReturn(null);
        when(keyValueRepository.get("KEY_LAST_WEATHER", null)).thenReturn(null);

        presenter.onAttach(ui);
        presenter.getWeatherForSelectedCity();

        verify(ui).showFailedToGetWeather("city");
        verify(keyValueRepository, times(0)).put(anyString(), any());

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testGetWeatherSuccess() throws Exception {
        CityWeather weather = new CityWeather(null, null, null, null, null, null);
        when(citiesRepository.getSelected()).thenReturn("city");
        when(weatherRepository.getWeather("city")).thenReturn(weather);

        presenter.onAttach(ui);
        presenter.getWeatherForSelectedCity();

        verify(ui).showWeather(weather);
        verify(keyValueRepository).put("KEY_LAST_WEATHER", new Gson().toJson(weather));

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testGetWeatherLatest() throws Exception {
        CityWeather weather = new CityWeather(null, "city", null, null, null, null);
        String json = new Gson().toJson(weather);
        when(citiesRepository.getSelected()).thenReturn("city");
        when(weatherRepository.getWeather("city")).thenThrow(new NoNetworkException());
        when(keyValueRepository.get("KEY_LAST_WEATHER", null)).thenReturn(json);

        presenter.onAttach(ui);
        presenter.getWeatherForSelectedCity();

        verify(ui).showWeather(weather);
        verify(keyValueRepository, times(0)).put(anyString(), any());

        verifyNoMoreInteractions(ui);
    }

    @Test
    public void testGetWeatherLatestFail() throws Exception {
        CityWeather weather = new CityWeather(null, "city1", null, null, null, null);
        String json = new Gson().toJson(weather);
        when(citiesRepository.getSelected()).thenReturn("city2");
        when(weatherRepository.getWeather("city2")).thenThrow(new NoNetworkException());
        when(keyValueRepository.get("KEY_LAST_WEATHER", null)).thenReturn(json);

        presenter.onAttach(ui);
        presenter.getWeatherForSelectedCity();

        verify(ui).showFailedToGetWeather("city2");
        verify(keyValueRepository, times(0)).put(anyString(), any());

        verifyNoMoreInteractions(ui);
    }
}
