package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireStationService fireStationService;

    @Test
    public void testGetFireStation() throws Exception {
        //Arrange
        FireStation fireStation = new FireStation("12 rue des lilas, Paris", "2");
        when(fireStationService.testMethodFireStation()).thenReturn(fireStation);
        //Act & Assert
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk());
        verify(fireStationService, times(1)).testMethodFireStation();
    }

    @Test
    public void testGetFireStation_errorFetching() throws Exception {
        //Arrange
        when(fireStationService.testMethodFireStation()).thenReturn(null);
        //Act & Assert
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("error fetching last fire station in the list"));
        verify(fireStationService, times(1)).testMethodFireStation();
    }

    @Test
    public void testPostFireStation() throws Exception {
        mockMvc.perform(post("/firestation")
                .param("address", "32 bld de la Victoire")
                .param("station","78"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostFireStation_argMissing() throws Exception {
        mockMvc.perform(post("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station",""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutFireStation() throws Exception {
        mockMvc.perform(put("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station","78"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testPutFireStation_argMissing() throws Exception {
        mockMvc.perform(put("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station",""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station","78"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFireStation_argMissing() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station",""))
                .andExpect(status().isBadRequest());
    }
}
