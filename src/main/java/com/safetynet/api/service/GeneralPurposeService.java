package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.model.DTO.*;
import com.safetynet.api.util.CalculUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GeneralPurposeService {

    @Autowired
    CalculUtil calculUtil;

    @Autowired
    private List<Person> personList;

    @Autowired
    private List<FireStation> fireStationList;

    @Autowired
    private List<MedicalRecord> medicalRecordList;

    public PeopleCoveredByFireStationDTO findPeopleCoveredByFireStation(String station) {

        List<FireStation> stationsSelected = new ArrayList<>();

        List<Person> personAroundTheStation = new ArrayList<>();

        List<PersonLightDTO> personLightDTOList = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            if (fireStation.getStation().equals(station)) { //TODO Yoda writing or Objets.equals()
                stationsSelected.add(fireStation);
                log.debug("Adding {} to the list of filtered station, the filter is the number {}",
                        fireStation, station);
            }
        }

        for (Person person : personList) {
            for (FireStation fireStation : stationsSelected) {
                if (person.getAddress().equals(fireStation.getAddress())) {
                    personAroundTheStation.add(person);
                    log.debug("Adding {} {} to the list of people covered by the selected stations",
                            person.getFirstName(), person.getLastName());
                }
            }
        }

        for (Person person : personAroundTheStation) {
            PersonLightDTO personLightDTO = new PersonLightDTO();
            personLightDTO.setFirstName(person.getFirstName());
            personLightDTO.setLastName(person.getLastName());
            personLightDTO.setAddress(person.getAddress());
            personLightDTO.setPhoneNumber(person.getPhone());
            personLightDTOList.add(personLightDTO);
            log.debug("Creating a personLightDTO with the person {}", person);
        }

        int numberOfAdult = 0;
        int numberOfChild = 0;
        for (PersonLightDTO personLightDTO : personLightDTOList) {
            if (calculUtil.isThisPersonAnAdult(personLightDTO, medicalRecordList)) {
                numberOfAdult += 1;
                log.debug("As of now, the number of Adults is {}", numberOfAdult);
            } else {
                numberOfChild += 1;
                log.debug("As of now, the number of Children is {}", numberOfChild);
            }
        }
        log.debug("Creating and returning PeopleCoveredByFireStationDTO, instantiated by " +
                "personLightDTOList, numberOfAdult, numberOfChild");
        return new PeopleCoveredByFireStationDTO(personLightDTOList, numberOfAdult, numberOfChild);
    }

    public List<ChildDTO> findChildAtAddress(String address) {
        List<Person> peopleAtAddress = new ArrayList<>();
        List<Person> personAtAddressMinusTheChild = new ArrayList<>();
        List<ChildDTO> childDTOSList = new ArrayList<>();

        for (Person person : personList) {
            if (person.getAddress().equals(address)) { //TODO Yoda writing or Objects.equals();
                peopleAtAddress.add(person);
                log.debug("Adding {} {} to the list of filtered Person, the filter is the address {}",
                        person.getFirstName(),
                        person.getLastName(),
                        address);
            }
        }

        //TODO Three embedded for loop, that's too much
        for (Person person : peopleAtAddress) {
            for (MedicalRecord medicalRecord : medicalRecordList) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())
                        && calculUtil.isThisPersonAChild(person, medicalRecord)
                ) {
                    ChildDTO childDTO = new ChildDTO();
                    childDTO.setFirstName(person.getFirstName());
                    childDTO.setLastName(person.getLastName());
                    childDTO.setAge(calculUtil.calulateAge(person, medicalRecord));
                    log.debug("Creating a ChildDTO with {} {}", person.getFirstName(), person.getLastName());

                    for (Person person2 : peopleAtAddress) {
                        if (person2.getFirstName().equals(person.getFirstName()) && person2.getLastName().equals(person.getLastName())) {
                            continue;
                        } else {
                            personAtAddressMinusTheChild.add(person2);
                            log.debug("Adding {} {} to the list of person that are not children, but living at the address {}",
                                    person2.getFirstName(), person2.getLastName(), address);
                        }
                    }
                    childDTO.setOtherFamilyMember(personAtAddressMinusTheChild);
                    childDTOSList.add(childDTO);
                    log.debug("adding the childDTO {} {} to the list to be returned as well as the other family member",
                            childDTO.getFirstName(),
                            childDTO.getLastName());
                }
            }
        }
    return childDTOSList;
    }

    public List<String> findPhoneNumbersCoveredByFireStation(String firestationNumber) {
        List<FireStation> fireStationsAreNumber = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            if (fireStation.getStation().equals(firestationNumber)) { //TODO Yoda writing or Objects.equals()
                fireStationsAreNumber.add(fireStation);
                log.debug("Adding {} station number {} to the list of filtered station, the filter is the number {}",
                        fireStation.getAddress(), fireStation.getStation(), firestationNumber);
            }
        }

        for (FireStation fireStation : fireStationsAreNumber) {
            for (Person person : personList) {
                if (person.getAddress().equals(fireStation.getAddress())) {
                    phoneNumbers.add(person.getPhone());
                    log.debug("Adding the phoneNumber {} from the person {} {} to the return list because it matches the fire station address {}",
                            person.getPhone(), person.getFirstName(), person.getLastName(), fireStation.getAddress());
                }
            }
        }
        return phoneNumbers;
    }

    public DetailListOfInhabitantsDTO getDetailListOfInhabitants(String address) {
        int stationNumber = -1;
        List<Person> peopleLivingAtAddress = new ArrayList<>();
        List<InhabitantDTO> inhabitantDTOList = new ArrayList<>();

        for(Person person : personList) {
            if (person.getAddress().equals(address)) { //TODO Yoda writing or Objects.equals()
                peopleLivingAtAddress.add(person);
                log.debug("Adding {} {} to the list of filtered people, the filter is the address {}",
                        person.getFirstName(),
                        person.getLastName(),
                        address);
            }
        }
        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddress().equals(address)) {
                stationNumber = Integer.parseInt(fireStation.getStation());
                log.debug("Setting the integer stationNumber to the value {}", stationNumber);
            }
        }

        for(Person person : peopleLivingAtAddress) {
            InhabitantDTO inhabitantDTO = new InhabitantDTO();
            inhabitantDTO.setFirstName(person.getFirstName());
            inhabitantDTO.setLastName(person.getLastName());
            inhabitantDTO.setPhoneNumber(person.getPhone());
            inhabitantDTO.setAge(calculUtil.calulateAge(person, medicalRecordList));
            inhabitantDTO.setMedications(fetchMedication(person));
            inhabitantDTO.setAllergies(fetchAllergies(person));
            inhabitantDTOList.add(inhabitantDTO);
            log.debug("Creating the inhabitantDTO {} {} and adding it to the list to be returned", person.getFirstName(), person.getLastName());
        }

        return new DetailListOfInhabitantsDTO(inhabitantDTOList, stationNumber);

    }
    //TODO move in util package
    private List<String> fetchMedication(Person person) {
        List<String> result = new ArrayList<>(); //TODO Should it be better : Collections.emptyList()
        log.debug("Fetching the List<Medication> for {} {}", person.getFirstName(), person.getLastName());
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getMedications();
                log.debug("Setting the List<Medication> {}", result);
            }
        }
        return result;
    }
    //TODO move in util package
    private List<String> fetchAllergies(Person person) {
        List<String> result = new ArrayList<>(); //TODO Should it be better : Collections.emptyList()
        log.debug("Fetching the List<Allergies> for {} {}", person.getFirstName(), person.getLastName());
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getAllergies();
                log.debug("Setting the List<Allergies> {}", result);
            }
        }
        return result;
    }

    //TODO let's not repeat the address that much, let's make a payloadDTO that wrap a List<DwellingDTO> and the add a Address on top
    public List<DwellingDTO> getDetailListOfDwelling(List<String> stations) {
        List<FireStation> fireStationsChoosen = new ArrayList<>();
        List<Person> personsCoveredByStations = new ArrayList<>();
        List<DwellingDTO> dwellingDTOList = new ArrayList<>();
        List<PersonForDwellingDTO> personForDwellingDTOList = new ArrayList<>();
        DwellingDTO dwellingDTO = new DwellingDTO();

        for (String stationNumber : stations) {
            for (FireStation fireStation : fireStationList) {
                if (Objects.equals(stationNumber, fireStation.getStation())) {
                    fireStationsChoosen.add(fireStation);
                    log.debug("Adding the {} to the list of chosen fire stations", fireStation);
                }
            }
        }

        for (FireStation fireStation : fireStationsChoosen) {
            for (Person person : personList) {
                if (Objects.equals(fireStation.getAddress(), person.getAddress())) {
                    personsCoveredByStations.add(person);
                    log.debug("Adding the person {} {} living at {} to the list of personsCoveredByStations because " +
                                    "they have the same address as the station {}",
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            fireStation.getAddress());
                }
            }
        }

        //Building the DwellingDTO
        log.debug("Starting to loop through the personsCovered by Stations");
        int sizeOfPersonsCoveredByStations = personsCoveredByStations.size();
        for (int i = 0; i < sizeOfPersonsCoveredByStations; i++) {

            Person person = personsCoveredByStations.get(i);

            log.debug("Looking at the person {} {}", person.getFirstName(), person.getLastName());

            StringBuilder nameMedicationAllergies = new StringBuilder();
            log.debug("Building the String with the names and medical record");
            nameMedicationAllergies
                    .append(person.getFirstName())
                    .append(" ")
                    .append(person.getLastName())
                    .append(" ")
                    .append(fetchMedication(person))
                    .append(" ")
                    .append(fetchAllergies(person));


            PersonForDwellingDTO personForDwellingDTO = new PersonForDwellingDTO(
                    nameMedicationAllergies.toString(),
                    person.getPhone(),
                    calculUtil.calulateAge(person, medicalRecordList));
            log.debug("Constructing a new object PersonForDwellingDTO with the string, phone number and age " +
                    "of the person");

            if (i == 0) {

                log.debug("populating the personForDwellinDTOList with its first person");

                personForDwellingDTOList.add(personForDwellingDTO);

                log.debug("adding this personForDwellingDTO to the list personForDwellingDTOList the list " +
                        "should contain only person from the same address");

            } else {

                if (Objects.equals(personsCoveredByStations.get(i - 1).getAddress(), person.getAddress())) {

                    personForDwellingDTOList.add(personForDwellingDTO);


                } else {
                    log.debug("If the person address is different than the last person, let's do that :");

                    log.debug("Create a new dwellingDTO with the list of person and the address linking them");
                    dwellingDTO.setPersonForDwellingDTOList(personForDwellingDTOList);
                    dwellingDTO.setAddress(personsCoveredByStations.get(i - 1).getAddress());
                    log.debug("and adding this dwellingDTO object to the dwellingDTOList to be returned");
                    dwellingDTOList.add(dwellingDTO);

                    log.debug("then, we want to clear the personForDwellingDTOList because we are starting a new house");
                    personForDwellingDTOList = new ArrayList<>();
                    log.debug("same for the dwellingDTO");
                    dwellingDTO = new DwellingDTO();

                    // but need to add that person too !
                    personForDwellingDTOList.add(personForDwellingDTO);

                }

            }

            log.debug("Creating a DwellingDTO with the person {} {} and adding it to the list to be returned",
                    person.getFirstName(),
                    person.getLastName());
        }
        return dwellingDTOList;
    }

    public List<PersonInfoLastNameDTO> getPersonInfoByLastName(String lastName) {
        List<Person> personSelected = new ArrayList<>();
        List<PersonInfoLastNameDTO> personInfoLastNameDTOList = new ArrayList<>();

        for (Person person : personList) {
            if (Objects.equals(person.getLastName(), lastName)) {
                personSelected.add(person);
            }
        }

        for (Person person : personSelected) {
            PersonInfoLastNameDTO personInfoLastNameDTO = new PersonInfoLastNameDTO();
            personInfoLastNameDTO.setFirstName(person.getFirstName());
            personInfoLastNameDTO.setLastName(person.getLastName());
            personInfoLastNameDTO.setAddress(person.getAddress());
            personInfoLastNameDTO.setAge(calculUtil.calulateAge(person, medicalRecordList));
            personInfoLastNameDTO.setEmail(person.getEmail());
            personInfoLastNameDTO.setMedications(fetchMedication(person));
            personInfoLastNameDTO.setAllergies(fetchAllergies(person));
            personInfoLastNameDTOList.add(personInfoLastNameDTO);
        }
        return personInfoLastNameDTOList;
    }
}
