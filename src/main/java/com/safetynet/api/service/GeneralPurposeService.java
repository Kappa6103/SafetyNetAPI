package com.safetynet.api.service;

// IMPLEMENTATION DES TRAITEMENTS METIERS SPECIFIQUES A L'APPLICATION

import com.safetynet.api.model.*;
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
    PayLoadOneDTO payLoad;

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

    public PayLoadOneDTO findPeopleCoveredByFireStation(String station) {
        //TODO cut the String to remove house numbers ?

        //TODO Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers
        //correspondante.
        // Donc, si le numéro de station = 1, elle doit renvoyer les habitants
        //couverts par la station numéro 1.
        // La liste doit inclure les informations spécifiques
        //suivantes : prénom, nom, adresse, numéro de téléphone.
        // De plus, elle doit fournir un
        //décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
        //moins) dans la zone desservie

        List<FireStation> stationsSelected = new ArrayList<>();

        List<Person> personAroundTheStation = new ArrayList<>();

        List<PersonLightDTO> personLightDTOList = new ArrayList<>();

        payLoad.reset();

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

        for (PersonLightDTO personLightDTO : personLightDTOList) {
            payLoad.addAPersonLightDTO(personLightDTO);
            if (calculUtil.isThisPersonAnAdult(personLightDTO, medicalRecordList)) {
                payLoad.incrementAdult();
            } else {
                payLoad.incrementChild();
            }
        }
        return payLoad;
    }

}
