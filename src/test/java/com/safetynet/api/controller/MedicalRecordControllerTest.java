package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testGetMedicalRecord() throws Exception {
        //Arrange
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "03/06/1984",
                List.of("Ibuprofen"), List.of("cacahuete"));
        when(medicalRecordService.testMethodMedicalRecord()).thenReturn(medicalRecord);
        //Act & Assert
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
        verify(medicalRecordService, times(1)).testMethodMedicalRecord();
    }

    @Test
    public void testGetMedicalRecord_errorFetching() throws Exception {
        //Arrange
        when(medicalRecordService.testMethodMedicalRecord()).thenReturn(null);
        //Act & Assert
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("error fetching last medical record in the list"));
        verify(medicalRecordService, times(1)).testMethodMedicalRecord();
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
    public void testPostMedicalRecord_argsMissing() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName","Doe")
                        .param("birthday", "23/23/23")
                        .param("medications","")
                        .param("allergies", ""))
                .andExpect(status().isBadRequest());
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
    public void testPutMedicalRecord_argsMissing() throws Exception {
        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName","")
                        .param("birthday", "")
                        .param("medications","OverTheRainbow", "UnderTheRainbow")
                        .param("allergies", "peanuts", "shellfish"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName","Doe"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord_argMissing() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName",""))
                .andExpect(status().isBadRequest());
    }
}
