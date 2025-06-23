package com.safetynet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.api.service.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SafetyNetService safetyNetService;

    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostPerson() throws Exception {
        mockMvc.perform(post("/person")
                .param("firstName", "John")
                .param("lastName","Doe")
                .param("address", "Somewhere")
                .param("city","OverTheRainbow")
                .param("zip", "67000")
                .param("phone", "029320932093")
                .param("phone", "029320932093")
                .param("email", "adsfasdf@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutPerson() throws Exception {
        mockMvc.perform(put("/person")
                        .param("firstName", "John")
                        .param("lastName","Doe")
                        .param("address", "Somewhere")
                        .param("city","OverTheRainbow")
                        .param("zip", "67000")
                        .param("phone", "029320932093")
                        .param("phone", "029320932093")
                        .param("email", "adsfasdf@gmail.com"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName","Doe"))
                        .andExpect(status().isOk());
    }
}
