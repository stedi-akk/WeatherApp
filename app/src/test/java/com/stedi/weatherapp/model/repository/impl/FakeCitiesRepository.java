package com.stedi.weatherapp.model.repository.impl;

import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class FakeCitiesRepository implements CitiesRepository {
    private String selected;

    @Nullable
    @Override
    public String getSelected() throws Exception {
        return selected;
    }

    @Override
    public void setSelected(@NotNull String city) throws Exception {
        this.selected = city;
    }

    @NotNull
    @Override
    public List<String> getAll() throws Exception {
        return Arrays.asList("city1", "city2", "city3", "city4", "city5");
    }
}
