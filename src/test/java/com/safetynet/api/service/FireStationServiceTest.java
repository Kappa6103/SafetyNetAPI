package com.safetynet.api.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.DataWrapper;
import org.mockito.Mock;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationServiceTest {

    @MockitoBean
    DataWrapper dataWrapper;

    @Autowired
    FireStationService fireStationService;

    @Autowired
    List<FireStation> fireStationList;

    @BeforeEach
    public void setUpPerTest() {
        fireStationList.clear();
    }

    FireStation fsTest = new FireStation("Ex", "2");

    @Test
    public void testMethodFireStationTest() {
        //Arrange
        fireStationList.add(fsTest);
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
        int initialSize = fireStationList.size();

        //Act
        fireStationService.addFireStation("Ex", "2");
        //Assert
        assertEquals(initialSize + 1, fireStationList.size(), "One more element in the list");
        assertEquals("Ex", fireStationList.getLast().getAddress(), "The Addresses should match");
        assertEquals("2", fireStationList.getLast().getStation(), "The Station numbers should match");

        verify(dataWrapper, times(1)).setFireStations(fireStationList);

    }

    @Test
    public void testUpdateFireStation() {
        //Arrange
        fireStationList.add(fsTest);
        FireStation result;

        //Act
        fireStationService.updateFireStation("Ex", "5");
        result = fireStationList.getLast();

        //Assert
        assertEquals("5", result.getStation(), "Value is updated, must be 5");
        assertSame(fsTest, result);
        verify(dataWrapper, times(1)).setFireStations(fireStationList);
    }

    @Test
    public void testDeleteStation() {
        //Arrange
        fireStationList.add(fsTest);

        //Act
        assertEquals(1, fireStationList.size()); //checking for false positive
        fireStationService.deleteStation("Ex", "2");

        //Assert
        assertTrue(fireStationList.isEmpty(), "list should be empty after deletion of its only element");
        verify(dataWrapper, times(1)).setFireStations(fireStationList);
    }




}
