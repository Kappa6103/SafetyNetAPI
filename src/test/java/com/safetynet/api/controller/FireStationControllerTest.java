package com.safetynet.api.controller;

import com.safetynet.api.service.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SafetyNetService safetyNetService;

    @Test
    public void testGetFireStation() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostFireStation() throws Exception {
        mockMvc.perform(post("/firestation")
                .param("address", "32 bld de la Victoire")
                .param("station","78"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutFireStation() throws Exception {
        mockMvc.perform(put("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station","78"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "32 bld de la Victoire")
                        .param("station","78"))
                        .andExpect(status().isOk());
    }
}
