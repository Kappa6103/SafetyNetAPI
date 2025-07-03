package com.safetynet.api.service;

// IMPLEMENTATION DES TRAITEMENTS METIERS SPECIFIQUES A L'APPLICATION

import com.safetynet.api.model.*;
import com.safetynet.api.model.DTO.*;
import com.safetynet.api.util.CalculUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            if (fireStation.getStation().equals(station)) {
                stationsSelected.add(fireStation);
            }
        }

        for (Person person : personList) {
            for (FireStation fireStation : stationsSelected) {
                if (person.getAddress().equals(fireStation.getAddress())) {
                    personAroundTheStation.add(person);
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
        }

        int numberOfAdult = 0;
        int numberOfChild = 0;
        for (PersonLightDTO personLightDTO : personLightDTOList) {
            if (calculUtil.isThisPersonAnAdult(personLightDTO, medicalRecordList)) {
                numberOfAdult += 1;
            } else {
                numberOfChild += 1;
            }
        }
        return new PeopleCoveredByFireStationDTO(personLightDTOList, numberOfAdult, numberOfChild);
    }

    public List<ChildDTO> findChildAtAddress(String address) {
        List<Person> peopleAtAddress = new ArrayList<>();
        List<Person> peopleAtAddressThatAreChild = new ArrayList<>();
        List<ChildDTO> childDTOS = new ArrayList<>();

        for (Person person : personList) {
            if (person.getAddress().equals(address)) {
                peopleAtAddress.add(person);
            }
        }

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
                    List<Person> personAtAddressMinusTheChild = new ArrayList<>();
                    for (Person person2 : peopleAtAddress) {
                        if (person2.getFirstName().equals(person.getFirstName()) && person2.getLastName().equals(person.getLastName())) {
                            continue;
                        } else {
                            personAtAddressMinusTheChild.add(person2);
                        }
                    }
                    childDTO.setOtherFamilyMember(personAtAddressMinusTheChild);
                    childDTOS.add(childDTO);
                }
            }
        }


    return childDTOS;
    }

    public List<String> findPhoneNumbersCoveredByFireStation(String firestationNumber) {
        List<FireStation> fireStationsAreNumber = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            if (fireStation.getStation().equals(firestationNumber)) {
                fireStationsAreNumber.add(fireStation);
            }
        }

        for (FireStation fireStation : fireStationsAreNumber) {
            for (Person person : personList) {
                if (person.getAddress().equals(fireStation.getAddress())) {
                    phoneNumbers.add(person.getPhone());
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
            if (person.getAddress().equals(address)) {
                peopleLivingAtAddress.add(person);
            }
        }
        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddress().equals(address)) {
                stationNumber = Integer.parseInt(fireStation.getStation());
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
        }

        return new DetailListOfInhabitantsDTO(inhabitantDTOList, stationNumber);

    }
    private List<String> fetchMedication(Person person) {
        List<String> result = null;
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getMedications();
            }
        }
        return result;
    }
    private List<String> fetchAllergies(Person person) {
        List<String> result = null;
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                result = medicalRecord.getAllergies();
            }
        }
        return result;
    }
}
