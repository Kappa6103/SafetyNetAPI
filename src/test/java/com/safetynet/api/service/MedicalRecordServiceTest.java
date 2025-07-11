package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
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
public class MedicalRecordServiceTest {

    @MockitoBean
    DataWrapper dataWrapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUpPerTest() {
        medicalRecordList.clear();
    }

    MedicalRecord mrTest = new MedicalRecord("testFirstName", "testLastName", "23/23/23",
            List.of("doliprane"), List.of("peanuts"));

    @Test
    public void testMethodMedicalRecordTest() {
        //Arrange
        medicalRecordList.add(mrTest);
        MedicalRecord result;
        //Act
        result = medicalRecordService.testMethodMedicalRecord();
        //Assert
        assertNotNull(result);
        assertSame(mrTest, result);
        assertEquals("testFirstName", result.getFirstName());
        assertEquals("testLastName", result.getLastName());
        assertEquals("23/23/23", result.getBirthdate());
        assertEquals(List.of("doliprane"), result.getMedications());
        assertEquals(List.of("peanuts"), result.getAllergies());
    }

    @Test
    public void testAddMedicalRecord() {
        //Arrange
        int initialSize = medicalRecordList.size();

        //Act
        medicalRecordService.addMedicalRecord("testFirstName", "testLastName", "23/23/23",
                List.of("doliprane"), List.of("peanuts"));
        //Assert
        assertEquals(initialSize + 1, medicalRecordList.size(), "One more element in the list");
        assertEquals("testFirstName", medicalRecordList.getLast().getFirstName());
        assertEquals("testLastName", medicalRecordList.getLast().getLastName());
        assertEquals("23/23/23", medicalRecordList.getLast().getBirthdate());
        assertEquals(List.of("doliprane"), medicalRecordList.getLast().getMedications());
        assertEquals(List.of("peanuts"), medicalRecordList.getLast().getAllergies());

        verify(dataWrapper, times(1)).setMedicalRecords(medicalRecordList);
    }

    @Test
    public void testUpdateMedicalRecord() {
        //Arrange
        medicalRecordList.add(mrTest);
        MedicalRecord result;
        //Act
        medicalRecordService.updateMedicalRecord("testFirstName", "testLastName", "25/25/25",
                List.of("Aspegic"), List.of("crevette"));
        result = medicalRecordList.getLast();
        //Assert
        assertSame(mrTest, result);
        assertEquals("25/25/25", result.getBirthdate());
        assertEquals(List.of("Aspegic"), result.getMedications());
        assertEquals(List.of("crevette"), result.getAllergies());

        verify(dataWrapper, times(1)).setMedicalRecords(medicalRecordList);
    }

    @Test
    public void testUpdateMedicalRecord_whenMedicalRecordNotInList() {
        //Arrange
        medicalRecordList.add(mrTest);
        MedicalRecord result;
        //Act
        medicalRecordService.updateMedicalRecord("XXXFirstName", "XXXLastName", "25/25/25",
                List.of("Aspegic"), List.of("crevette"));
        result = medicalRecordList.getLast();
        //Assert
        assertSame(mrTest,result);
        assertEquals("testFirstName", medicalRecordList.getLast().getFirstName());
        assertEquals("testLastName", medicalRecordList.getLast().getLastName());
        assertEquals("23/23/23", medicalRecordList.getLast().getBirthdate());
        assertEquals(List.of("doliprane"), medicalRecordList.getLast().getMedications());
        assertEquals(List.of("peanuts"), medicalRecordList.getLast().getAllergies());

        verify(dataWrapper, times(1)).setMedicalRecords(medicalRecordList);
    }

    @Test
    public void testDeleteMedicalRecord() {
        //Arrange
        medicalRecordList.add(mrTest);
        //Act
        assertEquals(1, medicalRecordList.size()); //checking for false positive
        medicalRecordService.deleteMedicalRecord("testFirstName", "testLastName");
        //Assert
        assertTrue(medicalRecordList.isEmpty(), "list should be empty after deletion of its only element");
        verify(dataWrapper, times(1)).setMedicalRecords(medicalRecordList);
    }

    @Test
    public void testDeleteMedicalRecord_whenMedicalRecordNotInList() {
        //Arrange
        medicalRecordList.add(mrTest);
        //Act
        assertEquals(1, medicalRecordList.size()); //checking for false positive
        medicalRecordService.deleteMedicalRecord("XXXX", "2323");
        //Assert
        assertTrue(medicalRecordList.contains(mrTest), "the list element should not be removed");
        verify(dataWrapper, times(1)).setMedicalRecords(medicalRecordList);
    }

}
