package com.safetynet.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.GeneralPurposeService;
import com.safetynet.api.service.PersonService;
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
    private PersonService personService;

    @Test
    public void testGetPerson() throws Exception {
        //Arrange
        Person person = new Person("John", "Doe", "Somewhere", "OverTheRainbow",
                "67000", "09090909", "johndoe@gmail.com");
        when(personService.testMethodPerson()).thenReturn(person);
        //Act & Assert
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
        verify(personService, times(1)).testMethodPerson();
    }

    @Test
    public void testGetPerson_errorFetching() throws Exception {
        //Arrange
        when(personService.testMethodPerson()).thenReturn(null);
        //Act & Assert
        mockMvc.perform(get("/person"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("error fetching last person in the list"));
        verify(personService, times(1)).testMethodPerson();
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
                .param("email", "adsfasdf@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostPerson_argsMissing() throws Exception {
        mockMvc.perform(post("/person")
                        .param("firstName", "John")
                        .param("lastName","Doe")
                        .param("address", "Somewhere")
                        .param("city","OverTheRainbow")
                        .param("zip", "67000")
                        .param("phone", "")
                        .param("email", ""))
                .andExpect(status().isBadRequest());
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
    public void testPutPerson_argsMissing() throws Exception {
        mockMvc.perform(put("/person")
                        .param("firstName", "John")
                        .param("lastName","Doe")
                        .param("address", "")
                        .param("city","OverTheRainbow")
                        .param("zip", "67000")
                        .param("phone", "029320932093")
                        .param("email", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName","Doe"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson_argMissing() throws Exception {
        mockMvc.perform(delete("/person")
                        .param("firstName", "")
                        .param("lastName","Doe"))
                .andExpect(status().isBadRequest());
    }
}
