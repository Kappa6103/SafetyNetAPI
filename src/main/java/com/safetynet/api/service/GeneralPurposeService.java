package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.model.DTO.*;
import com.safetynet.api.util.CalculUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GeneralPurposeService {

    private static final int AGE_OF_A_CHILD = 18;

    @Autowired
    CalculUtil calculUtil;

    @Autowired
    private List<Person> personList;

    @Autowired
    private List<FireStation> fireStationList;

    @Autowired
    private List<MedicalRecord> medicalRecordList;

    //TODO move in util package
    public List<String> fetchMedication(Person person) {
        List<String> result = new ArrayList<>(); //TODO Should it be better : Collections.emptyList()
        log.debug("Fetching the List<Medication> of {} {}", person.getFirstName(), person.getLastName());
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getMedications();
                log.debug("Returning the List<Medication> {}", result);
            }
        }
        return result;
    }
    //TODO move in util package
    public List<String> fetchAllergies(Person person) {
        List<String> result = new ArrayList<>(); //TODO Should it be better : Collections.emptyList()
        log.debug("Fetching the List<Allergies> of {} {}", person.getFirstName(), person.getLastName());
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getAllergies();
                log.debug("Returning the List<Allergies> {}", result);
            }
        }
        return result;
    }

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

    public ChildAtAddressDTO findChildAtAddress(String address) {
        List<Person> peopleAtAddress = new ArrayList<>();
        List<ChildDTO> listOfChild = new ArrayList<>();
        List<Person> listOfAdult = new ArrayList<>();

        for (Person person : personList) {
            if (Objects.equals(person.getAddress(), address)) {
                peopleAtAddress.add(person);
                log.debug("Adding {} {} to the list of filtered Person, the filter is the address {}",
                        person.getFirstName(),
                        person.getLastName(),
                        address);
            }
        }

        for (Person person : peopleAtAddress) {
            int ageOfThePerson = calculUtil.calulateAge(person, medicalRecordList);
            if (ageOfThePerson <= AGE_OF_A_CHILD) {

                ChildDTO childDTO = new ChildDTO();
                childDTO.setFirstName(person.getFirstName());
                childDTO.setLastName(person.getLastName());
                childDTO.setAge(ageOfThePerson);
                log.debug("Creating a ChildDTO with {} {}", person.getFirstName(), person.getLastName());

                listOfChild.add(childDTO);
                log.debug("Adding the ChildDTO to the list of child");
            } else {
                listOfAdult.add(person);
                log.debug("Adding the person {} {} of age {} to the list of adutls",
                        person.getFirstName(),
                        person.getLastName(),
                        ageOfThePerson);
            }
        }

        if (listOfChild.isEmpty()) {
            listOfAdult.clear();
            log.debug("There is not kids in the house, so clearing all entries in the adult list");
        }

    return new ChildAtAddressDTO(listOfChild, listOfAdult);
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
                                    "they have the same address as the fire station {}",
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            fireStation.getAddress());
                }
            }
        }

        int sizeOfPersonsCoveredByStations = personsCoveredByStations.size();
        for (int i = 0; i < sizeOfPersonsCoveredByStations; i++) {

            Person person = personsCoveredByStations.get(i);

            StringBuilder nameMedicationAllergies = new StringBuilder();
            log.debug("Building the String with the names and medical record for the person {} {}",
                    person.getFirstName(), person.getLastName());
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
                    "of the person {} {}", person.getFirstName(), person.getLastName());

            if (i == 0) {

                log.debug("populating the personForDwellinDTOList with its first person");
                personForDwellingDTOList.add(personForDwellingDTO);

            } else {

                if (Objects.equals(personsCoveredByStations.get(i - 1).getAddress(), person.getAddress())) {

                    personForDwellingDTOList.add(personForDwellingDTO);
                    log.debug("The person {} {} live at the same address as the previous person on the list" +
                            "we therefor add this person to the personForDwellingDTOList", person.getFirstName(),
                            person.getLastName());

                } else {
                    log.debug("The person {} {} doesn't live at the same address as the previous person on the list",
                            person.getFirstName(), person.getLastName());

                    dwellingDTO.setPersonForDwellingDTOList(personForDwellingDTOList);
                    log.debug("Loading the dwellingDTO with the existing personForDwellingDTOList");

                    dwellingDTO.setAddress(personsCoveredByStations.get(i - 1).getAddress());
                    log.debug("and setting its address");

                    dwellingDTOList.add(dwellingDTO);
                    log.debug("and adding this dwellingDTO the list of DTO to be returned");

                    personForDwellingDTOList = new ArrayList<>();
                    log.debug("Then we clear the personForDwellingDTOList");

                    dwellingDTO = new DwellingDTO();
                    log.debug("And we clear the dwellingDTO");

                    personForDwellingDTOList.add(personForDwellingDTO);
                    log.debug("And then add the current person {} {} to the personForDwellingDTOList to start the loop" +
                            " again", person.getFirstName(), person.getLastName());
                    if (i == sizeOfPersonsCoveredByStations - 1) {
                        //last iteration have to save this person
                        dwellingDTO.setPersonForDwellingDTOList(personForDwellingDTOList);
                        log.debug("Loading the dwellingDTO with the existing personForDwellingDTOList");

                        dwellingDTO.setAddress(personsCoveredByStations.get(i).getAddress());
                        log.debug("and setting its address");

                        dwellingDTOList.add(dwellingDTO);
                        log.debug("and adding this dwellingDTO the list of DTO to be returned");
                    }
                }
            }
        }

        return dwellingDTOList;
    }

    public List<PersonInfoLastNameDTO> getPersonInfoByLastName(String lastName) {
        List<Person> personSelected = new ArrayList<>();
        List<PersonInfoLastNameDTO> personInfoLastNameDTOList = new ArrayList<>();

        for (Person person : personList) {
            if (Objects.equals(person.getLastName(), lastName)) {
                personSelected.add(person);
                log.debug("Adding the person {} {} to the list of person selected because they have the last name {}",
                        person.getFirstName(), person.getLastName(), lastName);
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
            log.debug("Creating PersonInfoLastNameDTO for {} {} and adding it to the list to be returned",
                    person.getFirstName(),
                    person.getLastName());
        }
        return personInfoLastNameDTOList;
    }

    public EmailListOfCityInhabitants getEmailsOfInhabitants(String city) {
        EmailListOfCityInhabitants emailListOfCityInhabitants = new EmailListOfCityInhabitants();
        List<String> emailList = new ArrayList<>();


        for (Person person : personList) {
            if (Objects.equals(person.getCity(), city)) {
                emailList.add(person.getEmail());
                log.info("Adding the email {} of {} {} to the list because the person's city matches the requirement: " +
                        "{}",
                        person.getEmail(), person.getFirstName(), person.getLastName(), city);
            }
        }

        emailListOfCityInhabitants.setEmails(emailList);
        log.info("Setting the DTO with the list of emails");

        return emailListOfCityInhabitants;
    }
}
