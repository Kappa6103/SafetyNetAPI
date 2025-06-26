package com.safetynet.api.service;

// IMPLEMENTATION DES TRAITEMENTS METIERS SPECIFIQUES A L'APPLICATION

import com.safetynet.api.model.*;
import com.safetynet.api.model.DTO.PeopleCoveredByFireStationDTO;
import com.safetynet.api.model.DTO.PersonLightDTO;
import com.safetynet.api.repository.DataRepository;
import com.safetynet.api.util.CalculUtil;
import com.safetynet.api.util.DataExtractionUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralPurposeService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    CalculUtil calculUtil;

    @Autowired
    private DataExtractionUtil dataExtractionUtil;

    private DataWrapper dataWrapper;

    private List<Person> personList;

    private List<FireStation> fireStationList;

    private List<MedicalRecord> medicalRecordList;

    @PostConstruct
    private void init() {
        dataWrapper = dataRepository.getDataWrapper();
        personList = dataExtractionUtil.getListOfPersons(dataWrapper);
        fireStationList = dataExtractionUtil.getListOfFireStations(dataWrapper);
        medicalRecordList = dataExtractionUtil.getListOfMedicalRecords(dataWrapper);
    }

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

}
