package com.safetynet.api.repository;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DataRepositoryTest {

    @Autowired
    DataRepository dataRepository;

    @MockitoBean
    ObjectMapper objectMapper;

    private final String filePath = "src/main/resources/data.json";

//    @Test
//    @Disabled
//    public void testGetDataWrapper_whenObjectMapperReadFile() throws Exception {
//        //Arrange
//        when(objectMapper.readValue(any(File.class), eq(DataWrapper.class))).thenReturn(new DataWrapper());
//
//        //Act
//        DataWrapper result = dataRepository.getDataWrapper();
//
//        //Assert
//        assertNotNull(result);
//        verify(objectMapper, times(1)).readValue(any(File.class), eq(DataWrapper.class));
//    }
//
//    @Test
//    @Disabled
//    public void testGetDataWrapper_whenObjectMapperReadFileFail() throws Exception {
//
//    }

}
