package com.safetynet.api.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.DataWrapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.service.GeneralPurposeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FireStationServiceTest {

    @MockitoBean
    DataWrapper dataWrapper;

    @MockitoBean
    private List<FireStation> fireStationList;

    @Autowired
    FireStationService fireStationService;

    @Test
    public void testMethodFireStationTest() {
        //Arrange
        when(fireStationList.getLast()).thenReturn(new FireStation("Ex", "2"));
        FireStation result;
        //Act
        result = fireStationService.testMethodFireStation();
        //Assert
        assertNotNull(result);
        assertEquals("Ex", result.getAddress());
        assertEquals("2", result.getStation());
    }

    @Test
    public void testAddFireStation() {
        //Arrange
        FireStation fireStation = new FireStation("Ex", "2");
        //Act
        fireStationService.addFireStation("Ex", "2");
        //Assert
        verify(fireStationList, times(1)).add(fireStation);
        verify(dataWrapper, times(1)).setFireStations(fireStationList);

    }




}
