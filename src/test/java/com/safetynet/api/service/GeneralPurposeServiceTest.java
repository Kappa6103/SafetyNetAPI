package com.safetynet.api.service;

import com.safetynet.api.model.DTO.*;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.util.CalculUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.accessibility.AccessibleTable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeneralPurposeServiceTest {

    @Mock
    CalculUtil calculUtil;

    @Mock
    private List<Person> personList;

    @Mock
    private List<FireStation> fireStationList;

    @Mock
    private List<MedicalRecord> medicalRecordList;

    @InjectMocks
    private GeneralPurposeService generalPurposeService;


    private boolean containsPersonLight(Person personArg, List<PersonLightDTO> personLightDTOList) {
        for (PersonLightDTO personLightDTO : personLightDTOList) {
            if (Objects.equals(personArg.getFirstName(), personLightDTO.getFirstName()) &&
                    Objects.equals(personArg.getLastName(), personLightDTO.getLastName())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsPerson(Person personArg, List<Person> PersonList) {
        for (Person person : PersonList) {
            if (Objects.equals(personArg.getFirstName(), person.getFirstName()) &&
                    Objects.equals(personArg.getLastName(), person.getLastName())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsChild(ChildDTO childDTOArg, List<ChildDTO> childDTOList) {
        for (ChildDTO childDTO : childDTOList) {
            if (Objects.equals(childDTOArg.getFirstName(), childDTO.getFirstName()) &&
                    Objects.equals(childDTOArg.getLastName(), childDTO.getLastName())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsInhabitant(List<Person> personList, DetailListOfInhabitantsDTO inhabitantsList) {
        boolean isInhabitantsIntheList = false;
        for (Person person : personList) {
            for (InhabitantDTO inhabitantDTO : inhabitantsList.getListOfInhabitant()) {
                if (Objects.equals(person.getFirstName(), inhabitantDTO.getFirstName()) &&
                        Objects.equals(person.getLastName(), inhabitantDTO.getLastName())) {
                    isInhabitantsIntheList = true;
                } else {
                    isInhabitantsIntheList = false;
                }
            }
        }
        return isInhabitantsIntheList;
    }

    @Test
    @DisplayName("Should find people covered by fire station and correctly count adults/children")
    void TestFindPeopleCoveredByFireStation_ReturnsCorrectDTO() {
        // Arrange
        String stationNumber = "1";

        FireStation fireStation1 = new FireStation("10 Downing St", "1");
        FireStation fireStation2 = new FireStation("10 Downing St", "2"); // A different station for the same address

        Person adult1 = new Person("John", "Doe", "10 Downing St", "London",
                "123-456-7890","2323232323", "john.doe@example.com");
        Person child1 = new Person("Jane", "Doe", "10 Downing St", "London",
                "123-456-7891","2323232323", "jane.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "Neverland", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered

        // Mock data
        when(fireStationList.iterator()).thenReturn(Arrays.asList(fireStation1, fireStation2).iterator());

        when(personList.iterator()).thenReturn(Arrays.asList(adult1, child1, adult2).iterator());

        // Mock calculUtil behavior for specific persons
        when(calculUtil.isThisPersonAnAdult(any(PersonLightDTO.class), any(List.class)))
                .thenAnswer(invocation -> {
                    PersonLightDTO personLightDTO = invocation.getArgument(0);
                    if (personLightDTO.getFirstName().equals("John") && personLightDTO.getLastName().equals("Doe") ||
                    Objects.equals("Peter", personLightDTO.getFirstName()) && Objects.equals("Pan", personLightDTO.getLastName())) {
                        return true; // John and Peter are adult
                    } else if (personLightDTO.getFirstName().equals("Jane") && personLightDTO.getLastName().equals("Doe")) {
                        return false; // Jane is a child
                    }
                    return true; // Default for others if any
                });


        // Act
        PeopleCoveredByFireStationDTO result = generalPurposeService.findPeopleCoveredByFireStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getPersonLightDTOList().size()); // John and Jane
        assertEquals(1, result.getNumberOfAdult()); // John
        assertEquals(1, result.getNumberOfChild()); // Jane

        // Verify content of personLightDTOList
        List<PersonLightDTO> personDTOs = result.getPersonLightDTOList();

        assertTrue(containsPersonLight(adult1, personDTOs));
        assertTrue(containsPersonLight(child1, personDTOs));
        assertFalse(containsPersonLight(adult2, personDTOs));
    }

    @Test
    @DisplayName("Should return empty DTO when no fire station matches the number")
    void TestFindPeopleCoveredByFireStation_NoFireStationFound_ReturnsEmptyDTO() {
        // Arrange
        String stationNumber = "99"; // Non-existent station

        when(fireStationList.iterator()).thenReturn(Collections.<FireStation>emptyList().iterator());
        when(personList.iterator()).thenReturn(Collections.<Person>emptyList().iterator()); // Person list doesn't matter much here

        // Act
        PeopleCoveredByFireStationDTO result = generalPurposeService.findPeopleCoveredByFireStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertTrue(result.getPersonLightDTOList().isEmpty());
        assertEquals(0, result.getNumberOfAdult());
        assertEquals(0, result.getNumberOfChild());
    }

    @Test
    @DisplayName("Should return empty DTO when fire station exists but no people are covered")
    void TestFindPeopleCoveredByFireStation_FireStationExistsNoPeopleCovered_ReturnsEmptyDTO() {
        // Arrange
        String stationNumber = "1";
        FireStation fireStation1 = new FireStation("10 Downing St", "1");
        Person personAtOtherAddress = new Person("Alice", "Wonderland", "Rabbit Hole", "Fantasy",
                "111-222-3333", "2323232323", "fake@gmail.com");

        when(fireStationList.iterator()).thenReturn(Arrays.asList(fireStation1).iterator());
        when(personList.iterator()).thenReturn(Arrays.asList(personAtOtherAddress).iterator());

        // Act
        PeopleCoveredByFireStationDTO result = generalPurposeService.findPeopleCoveredByFireStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertTrue(result.getPersonLightDTOList().isEmpty());
        assertEquals(0, result.getNumberOfAdult());
        assertEquals(0, result.getNumberOfChild());
    }

    @Test
    @DisplayName("Should correctly count all covered people as adults")
    void TestFindPeopleCoveredByFireStation_AllAdults_ReturnsCorrectCounts() {
        // Arrange
        String stationNumber = "3";
        FireStation fireStation = new FireStation("Maple Street", "3");

        Person adult1 = new Person("Bob", "Builder", "Maple Street", "Town",
                "111-111-1111","2323232323", "bob@example.com");
        Person adult2 = new Person("Wendy", "Worker", "Maple Street", "Town",
                "222-222-2222","2323232323", "wendy@example.com");

        when(fireStationList.iterator()).thenReturn(Arrays.asList(fireStation).iterator());
        when(personList.iterator()).thenReturn(Arrays.asList(adult1, adult2).iterator());

        // Ensure calculUtil always returns true for adults
        when(calculUtil.isThisPersonAnAdult(any(PersonLightDTO.class), any(List.class))).thenReturn(true);

        // Act
        PeopleCoveredByFireStationDTO result = generalPurposeService.findPeopleCoveredByFireStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getPersonLightDTOList().size());
        assertEquals(2, result.getNumberOfAdult());
        assertEquals(0, result.getNumberOfChild());
    }

    @Test
    @DisplayName("Should correctly count all covered people as children")
    void TestFindPeopleCoveredByFireStation_AllChildren_ReturnsCorrectCounts() {
        // Arrange
        String stationNumber = "4";
        FireStation fireStation = new FireStation("Sesame Street", "4");

        Person child1 = new Person("Elmo", "Monster", "Sesame Street", "NYC",
                "333-333-3333","2323232323", "elmo@example.com");
        Person child2 = new Person("Cookie", "Monster", "Sesame Street", "NYC",
                "444-444-4444","2323232323", "cookie@example.com");

        when(fireStationList.iterator()).thenReturn(Arrays.asList(fireStation).iterator());
        when(personList.iterator()).thenReturn(Arrays.asList(child1, child2).iterator());

        // Ensure calculUtil always returns false for children
        when(calculUtil.isThisPersonAnAdult(any(PersonLightDTO.class), any(List.class))).thenReturn(false);

        // Act
        PeopleCoveredByFireStationDTO result = generalPurposeService.findPeopleCoveredByFireStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getPersonLightDTOList().size());
        assertEquals(0, result.getNumberOfAdult());
        assertEquals(2, result.getNumberOfChild());
    }

    @Test
    @DisplayName("Should find child(s) at address and other people living at address")
    void TestFindChildAtAddress_ReturnsCorrectDTO() {
        // Arrange
        String address = "22 rue des lilas";

        Person adult1 = new Person("John", "Doe", "22 rue des lilas", "London",
                "123-456-7890","2323232323", "john.doe@example.com");
        Person child1 = new Person("Jane", "Doe", "22 rue des lilas", "London",
                "123-456-7891","2323232323", "jane.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "Neverland", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered
        Person child2 = new Person("Rebecca", "Doe", "10 Downing St", "London",
                "123-456-7891","2323232323", "jane.doe@example.com"); // Not covered
        ChildDTO child1DTO = new ChildDTO("Jane", "Doe", 17);
        // Mock data
        when(personList.iterator()).thenReturn(Arrays.asList(adult1, adult2, child1, child2).iterator());

        // Mock calculUtil
        when(calculUtil.calulateAge(any(Person.class), any(List.class)))
                .thenAnswer(invocation -> {
                    Person person = invocation.getArgument(0);
                    if (Objects.equals("John", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                    Objects.equals("Peter", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 25;
                    } else if (Objects.equals("Jane", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Rebecca", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 17;
                    }
                    return -1;
                });

        // Act
        ChildAtAddressDTO result = generalPurposeService.findChildAtAddress(address);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getListOfAdult().size());
        assertEquals(1, result.getListOfChild().size());

        // Verify content of ChildAtAddressDTO
        List<ChildDTO> childListResult = result.getListOfChild();
        List<Person> adultListResult = result.getListOfAdult();

        assertTrue(containsPerson(adult1, adultListResult));
        assertTrue(containsChild(child1DTO, childListResult));
        assertFalse(containsPerson(adult2, adultListResult));

    }

    @Test
    @DisplayName("Should return an empty list because no child at address")
    void TestFindChildAtAddress_NoChildAtAddress_ReturnsEmptyList() {
        // Arrange
        String address = "22 rue des lilas";

        Person adult1 = new Person("John", "Doe", "22 rue des lilas", "London",
                "123-456-7890","2323232323", "john.doe@example.com");
        Person child1 = new Person("Jane", "Doe", "22 rue de paris", "London",
                "123-456-7891","2323232323", "jane.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "Neverland", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered
        Person child2 = new Person("Rebecca", "Doe", "10 Downing St", "London",
                "123-456-7891","2323232323", "jane.doe@example.com"); // Not covered
        ChildDTO child1DTO = new ChildDTO("Jane", "Doe", 17);
        // Mock data
        when(personList.iterator()).thenReturn(Arrays.asList(adult1, adult2, child1, child2).iterator());

        // Mock calculUtil
        when(calculUtil.calulateAge(any(Person.class), any(List.class)))
                .thenAnswer(invocation -> {
                    Person person = invocation.getArgument(0);
                    if (Objects.equals("John", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Peter", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 25;
                    } else if (Objects.equals("Jane", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Rebecca", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 17;
                    }
                    return -1;
                });

        // Act
        ChildAtAddressDTO result = generalPurposeService.findChildAtAddress(address);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getListOfAdult().size());
        assertEquals(0, result.getListOfChild().size());

        // Verify content of ChildAtAddressDTO
        List<ChildDTO> childListResult = result.getListOfChild();
        List<Person> adultListResult = result.getListOfAdult();

        assertFalse(containsPerson(adult1, adultListResult));
        assertFalse(containsChild(child1DTO, childListResult));
    }

    @Test
    @DisplayName("Should return a list of phone numbers covered by the station")
    void testFindPhoneNumbersCoveredByFireStation() {
        //Arrange
        String station = "2";

        FireStation fireStation1 = new FireStation("22 rue de Paris", "2");
        FireStation fireStation2 = new FireStation("22 rue des lilas", "1");

        Person adult1 = new Person("John", "Doe", "22 rue de Paris", "London",
                "123-456-7890","0388411234", "john.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "22 rue des lilas", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered

        when(fireStationList.iterator()).thenReturn(Arrays.asList(fireStation1, fireStation2).iterator());
        when(personList.iterator()).thenReturn(List.of(adult1, adult2).iterator());



        //Act
        List<String> result = generalPurposeService.findPhoneNumbersCoveredByFireStation(station);

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("0388411234", result.getLast());

    }

    @Test
    @DisplayName("Should return a detailed list of inhabitant(s) for an address")
    void testGetDetailListOfInhabitants() {
        String address = "22 rue des lilas";

        Person adult1 = new Person("John", "Doe", "22 rue des lilas", "London",
                "123-456-7890","2323232323", "john.doe@example.com");
        Person child1 = new Person("Jane", "Doe", "22 rue des lilas", "London",
                "123-456-7891","2323232323", "jane.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "Neverland", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered
        Person child2 = new Person("Rebecca", "Doe", "10 Downing St", "London",
                "123-456-7891","2323232323", "jane.doe@example.com"); // Not covered

        MedicalRecord adult1MedicalRecord = new MedicalRecord("John", "Doe", "20/02/1991",
                List.of("Doliprane"), List.of("Peanuts"));
        MedicalRecord child1MedicalRecord = new MedicalRecord("Jane", "Doe", "20/02/1991",
                List.of("Aspegic"), List.of("Shrimp"));

        FireStation fireStation1 = new FireStation("22 rue de Paris", "2");
        FireStation fireStation2 = new FireStation("22 rue des lilas", "1");

        when(personList.iterator()).thenReturn(List.of(adult1, adult2, child1, child2).iterator());
        when(fireStationList.iterator()).thenReturn(List.of(fireStation1, fireStation2).iterator());
        when(medicalRecordList.iterator()).thenReturn(List.of(adult1MedicalRecord, child1MedicalRecord).iterator());

        // Mock calculUtil
        when(calculUtil.calulateAge(any(Person.class), any(List.class)))
                .thenAnswer(invocation -> {
                    Person person = invocation.getArgument(0);
                    if (Objects.equals("John", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Peter", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 25;
                    } else if (Objects.equals("Jane", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Rebecca", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 17;
                    }
                    return -1;
                });

        //Act
        DetailListOfInhabitantsDTO result = generalPurposeService.getDetailListOfInhabitants(address);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.getListOfInhabitant().size());

        assertEquals(1, result.getFireStation());
        assertTrue(containsInhabitant(List.of(adult1, child1), result));
        assertFalse(containsInhabitant(List.of(adult2, child2), result));

    }

    @Test
    @DisplayName("Should return all the houses covered by a list of fire stations")
    void testGetDetailListOfDwelling() {
        //Arrange
        List<String> stations = List.of("1", "2");

        Person adult1 = new Person("John", "Doe", "22 rue des lilas", "London",
                "123-456-7890","2323232323", "john.doe@example.com");
        Person child1 = new Person("Jane", "Doe", "22 rue des lilas", "London",
                "123-456-7891","2323232323", "jane.doe@example.com");
        Person adult2 = new Person("Peter", "Pan", "14 bld d'anvers", "Fantasy",
                "987-654-3210","2323232323", "peter.pan@example.com"); // Not covered
        Person child2 = new Person("Rebecca", "Doe", "10 Downing St", "London",
                "123-456-7891","2323232323", "jane.doe@example.com"); // Not covered

        MedicalRecord adult1MedicalRecord = new MedicalRecord("John", "Doe", "20/02/1991",
                List.of("Doliprane"), List.of("Peanuts"));
        MedicalRecord child1MedicalRecord = new MedicalRecord("Jane", "Doe", "20/02/1991",
                List.of("Aspegic"), List.of("Shrimp"));
        MedicalRecord adult2MedicalRecord = new MedicalRecord("Peter", "Pan", "20/02/1991",
                List.of("Doliprane"), List.of("Peanuts"));
        MedicalRecord child2MedicalRecord = new MedicalRecord("Rebecca", "Doe", "20/02/1991",
                List.of("Aspegic"), List.of("Shrimp"));

        FireStation fireStation1 = new FireStation("22 rue des lilas", "1");
        FireStation fireStation2 = new FireStation("14 bld d'anvers", "2");
        FireStation fireStation3 = new FireStation("22 rue des lilas", "3");
        FireStation fireStation4 = new FireStation("22 rue de Paris", "4");

        //when(stations.iterator()).thenReturn(stations.iterator());
        when(fireStationList.iterator()).thenAnswer(invocation -> List.of(fireStation1, fireStation2, fireStation3, fireStation4).iterator());
        when(personList.iterator()).thenAnswer(invocation -> (List.of(adult1, adult2, child1, child2).iterator()));
        when(medicalRecordList.iterator()).thenAnswer(invocation -> List.of(adult1MedicalRecord, adult2MedicalRecord,
                child1MedicalRecord, child2MedicalRecord).iterator());
        // Mock calculUtil
        when(calculUtil.calulateAge(any(Person.class), any(List.class)))
                .thenAnswer(invocation -> {
                    Person person = invocation.getArgument(0);
                    if (Objects.equals("John", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Peter", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 25;
                    } else if (Objects.equals("Jane", person.getFirstName()) && Objects.equals("Doe", person.getLastName()) ||
                            Objects.equals("Rebecca", person.getFirstName()) && Objects.equals("Pan", person.getLastName())) {
                        return 17;
                    }
                    return -1;
                });

        //Act
        List<DwellingDTO> result = generalPurposeService.getDetailListOfDwelling(stations);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        DwellingDTO firstHouse = result.getFirst();
        DwellingDTO secondHouse = result.getLast();
        assertEquals("22 rue des lilas", firstHouse.getAddress());
        assertEquals("14 bld d'anvers", secondHouse.getAddress());
        assertTrue(firstHouse.getPersonForDwellingDTOList().getFirst().getNameWithMedicationsAndAllergies().contains("John Doe"));
        assertTrue(firstHouse.getPersonForDwellingDTOList().getLast().getNameWithMedicationsAndAllergies().contains("Jane Doe"));
        assertTrue(secondHouse.getPersonForDwellingDTOList().getFirst().getNameWithMedicationsAndAllergies().contains("Peter Pan"));

    }


}
