package com.safetynet.api.controller;

import com.safetynet.api.service.GeneralPurposeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GeneralPurposeController.class)
public class GeneralPurposeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GeneralPurposeService generalPurposeService;

    private final String address = "22 rue des lilas";

    @Test
    public void testPeopleCoveredByFireStation() throws Exception {
        mockMvc.perform(
                get("/firestation")
                        .param("stationNumber", "2")
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .findPeopleCoveredByFireStation("2");
    }

    @Test
    public void testChildAtAddress() throws Exception {
        mockMvc.perform(
                get("/childAlert")
                        .param("address", address)
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .findChildAtAddress(address);

    }

    @Test
    public void testPhoneNumbers() throws Exception {
        mockMvc.perform(
                get("/phoneAlert")
                        .param("firestation", "2")
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .findPhoneNumbersCoveredByFireStation("2");
    }

    @Test
    public void testGetAllDetailOfInhabitantsAtAddress() throws Exception {
        mockMvc.perform(
                get("/fire")
                        .param("address", address)
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .getDetailListOfInhabitants(address);
    }

    @Test
    public void testDetailListOfDwellingCoveredByFireStation() throws Exception {
        mockMvc.perform(
                get("/flood/stations")
                        .param("stations", "2")
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .getDetailListOfDwelling(List.of("2"));
    }

    @Test
    public void testPersonInfoLastNameDTO() throws Exception {
        mockMvc.perform(
                get("/personInfo")
                        .param("lastName", "John")
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .getPersonInfoByLastName("John");
    }

    @Test
    public void testFetchingAllEmailsOfInhabitants() throws Exception {
        mockMvc.perform(
                get("/communityEmail")
                        .param("city", "Paris")
                )
                .andExpect(status().isOk());
        verify(generalPurposeService, times(1))
                .getEmailsOfInhabitants("Paris");
    }


}
