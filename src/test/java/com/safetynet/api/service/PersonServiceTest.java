package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonServiceTest {

    @MockitoBean
    DataWrapper dataWrapper;

    @Autowired
    PersonService personService;

    @Autowired
    List<Person> personList;

    @BeforeEach
    public void setUpPerTest() {
        personList.clear();
    }

    Person pTest = new Person("firstNameTest", "lastNameTest", "addressTest",
            "cityTest", "zipTest", "phoneTest", "emailTest");

    @Test
    public void testMethodPersonTest() {
        //Arrange
        personList.add(pTest);
        Person result;
        //Act
        result = personService.testMethodPerson();
        //Assert
        assertNotNull(result);
        assertSame(pTest, result);
        assertEquals("firstNameTest", result.getFirstName());
        assertEquals("lastNameTest", result.getLastName());
        assertEquals("addressTest", result.getAddress());
        assertEquals("cityTest", result.getCity());
        assertEquals("zipTest", result.getZip());
        assertEquals("phoneTest", result.getPhone());
        assertEquals("emailTest", result.getEmail());
    }

    @Test
    public void testAddPerson() {
        //Arrange
        int initialSize = personList.size();
        //Act
        personService.addPerson("firstNameTest", "lastNameTest", "addressTest",
                "cityTest", "zipTest", "phoneTest", "emailTest");
        //Assert
        assertEquals(initialSize + 1, personList.size(), "One more element in the list");
        assertEquals("firstNameTest", personList.getLast().getFirstName());
        assertEquals("lastNameTest", personList.getLast().getLastName());
        assertEquals("addressTest", personList.getLast().getAddress());
        assertEquals("cityTest", personList.getLast().getCity());
        assertEquals("zipTest", personList.getLast().getZip());
        assertEquals("phoneTest", personList.getLast().getPhone());
        assertEquals("emailTest", personList.getLast().getEmail());

        verify(dataWrapper, times(1)).setPersons(personList);
    }

    @Test
    public void testUpdatePerson() {
        //Arrange
        personList.add(pTest);
        Person result;
        //Act
        personService.updatePerson("firstNameTest", "lastNameTest", "22 rue des lilas",
                "Paris", "67000", "232323232", "email@gmail.com");
        result = personList.getLast();
        //Assert
        assertSame(pTest, result);
        assertEquals("22 rue des lilas", result.getAddress());
        assertEquals("Paris", result.getCity());
        assertEquals("67000", result.getZip());
        assertEquals("232323232", result.getPhone());
        assertEquals("email@gmail.com", result.getEmail());

        verify(dataWrapper, times(1)).setPersons(personList);
    }

    @Test
    public void testUpdatePerson_whenPersonNotInList() {
        //Arrange
        personList.add(pTest);
        Person result;
        //Act
        personService.updatePerson("firstNameTestXXX", "lastNameTestXXX", "22 rue des lilas",
                "Paris", "67000", "232323232", "email@gmail.com");
        result = personList.getLast();
        //Assert
        assertSame(pTest,result);
        assertEquals("firstNameTest", result.getFirstName());
        assertEquals("lastNameTest", result.getLastName());
        assertEquals("addressTest", result.getAddress());
        assertEquals("cityTest", result.getCity());
        assertEquals("zipTest", result.getZip());
        assertEquals("phoneTest", result.getPhone());
        assertEquals("emailTest", result.getEmail());

        verify(dataWrapper, times(1)).setPersons(personList);
    }

    @Test
    public void testDeletePerson() {
        //Arrange
        personList.add(pTest);
        //Act
        assertEquals(1, personList.size()); //checking for false positive
        personService.deletePerson("firstNameTest", "lastNameTest");
        //Assert
        assertTrue(personList.isEmpty(), "list should be empty after deletion of its only element");
        verify(dataWrapper, times(1)).setPersons(personList);
    }

    @Test
    public void testDeletePerson_whenPersonNotInList() {
        //Arrange
        personList.add(pTest);
        //Act
        assertEquals(1, personList.size()); //checking for false positive
        personService.deletePerson("XXXX", "2323");
        //Assert
        assertTrue(personList.contains(pTest), "the list element should not be removed");
        verify(dataWrapper, times(1)).setPersons(personList);
    }

}
