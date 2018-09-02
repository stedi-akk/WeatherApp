package com.stedi.weatherapp.model.repository.impl;

import com.stedi.weatherapp.model.repository.interfaces.CitiesRepository;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CachedCitiesRepositoryTest {
    private CitiesRepository targetRepository;
    private CachedCitiesRepository cachedRepository;

    @Before
    public void before() {
        targetRepository = spy(new FakeCitiesRepository());
        cachedRepository = new CachedCitiesRepository(targetRepository);
    }

    @Test
    public void testSelectedNotExist() throws Exception {
        cachedRepository.setSelected("test");
        verify(targetRepository).setSelected("test");
        verify(targetRepository).getSelected();

        assertTrue(cachedRepository.getSelected().equals("test"));
        assertTrue(cachedRepository.getSelected().equals("test"));

        verifyNoMoreInteractions(targetRepository);
    }

    @Test
    public void testSelectedExist() throws Exception {
        targetRepository.setSelected("test");
        verify(targetRepository).setSelected("test");

        assertTrue(cachedRepository.getSelected().equals("test"));
        verify(targetRepository).getSelected();

        assertTrue(cachedRepository.getSelected().equals("test"));

        verifyNoMoreInteractions(targetRepository);
    }

    @Test
    public void testGetAll() throws Exception {
        assertTrue(!cachedRepository.getAll().isEmpty());
        verify(targetRepository).getAll();

        assertTrue(!cachedRepository.getAll().isEmpty());

        verifyNoMoreInteractions(targetRepository);
    }
}
