package com.safetynet.api.controller;

import com.safetynet.api.service.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SafetyNetService safetyNetService;

    @Test
    public void testGetMedicalRecord() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMedicalRecord() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                .param("firstName", "John")
                .param("lastName","Doe")
                .param("birthday", "23/23/23")
                .param("medications","OverTheRainbow", "UnderTheRainbow")
                .param("allergies", "peanuts", "shellfish"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutMedicalRecord() throws Exception {
        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName","Doe")
                        .param("birthday", "23/23/23")
                        .param("medications","OverTheRainbow", "UnderTheRainbow")
                        .param("allergies", "peanuts", "shellfish"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName","Doe"))
                        .andExpect(status().isOk());
    }
}
