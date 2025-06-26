package com.safetynet.api.service;

// IMPLEMENTATION DES TRAITEMENTS METIERS SPECIFIQUES A L'APPLICATION

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Service
public class SafetyNetService {

    DataRepository dataRepository = new DataRepository();

    DataWrapper dataWrapper = dataRepository.getDataWrapper();

    DataExtraction dataExtraction = new DataExtraction(dataWrapper);

    @Autowired
    PayLoadOneDTO payLoad;

    private final List<Person> personList = dataExtraction.getListOfPersons();

    private final List<FireStation> fireStationList = dataExtraction.getListOfFireStations();

    private final List<MedicalRecord> medicalRecordList = dataExtraction.getListOfMedicalRecords();

    public Person testMethodPerson() {
        return personList.getLast();
    }

    public FireStation testMethodFireStation() {
        return fireStationList.getLast();
    }

    public MedicalRecord testMethodMedicalRecord() {
        return medicalRecordList.getLast();
    }

    public void savePersonData(List<Person> personList) {

        dataWrapper.setPersonIterable(personList);

        dataRepository.writeDataWrapper(dataWrapper);

    }

    public void saveFireStationData(List<FireStation> fireStationList) {

        dataWrapper.setFireStationIterable(fireStationList);

        dataRepository.writeDataWrapper(dataWrapper);

    }

    public void saveMedicalRecordData(List<MedicalRecord> medicalRecordList) {

        dataWrapper.setMedicalRecordIterable(medicalRecordList);

        dataRepository.writeDataWrapper(dataWrapper);

    }

    public void addPerson(
            String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        Person newPerson = new Person(firstName, lastName, address, city, zip, phone, email);

        personList.add(newPerson);

        savePersonData(personList);
    }

    public void updatePerson(
            String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                person.setAddress(address);
                person.setCity(city);
                person.setZip(zip);
                person.setPhone(phone);
                person.setEmail(email);
            }
        }
        savePersonData(personList);
    }

    public void deletePerson(String firstName, String lastName) {
        int sizeOfList = personList.size();
        for (int i = 0; i < sizeOfList; i++) {
            if (personList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName)) {
                personList.remove(i);
                break;
            }
        }
        savePersonData(personList);
    }

    public void addFireStation(String address, String station) {

        FireStation fireStation = new FireStation(address, station);

        fireStationList.add(fireStation);

        saveFireStationData(fireStationList);
    }

    public void updateFireStation(String address, String station) {
        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddress().equals(address)) {
                fireStation.setStation(station);
            }
        }
        saveFireStationData(fireStationList);
    }

    public void deleteStation(String address, String station) {
        int sizeOfFireStationList = fireStationList.size();
        for (int i = 0; i < sizeOfFireStationList; i++) {
            if (fireStationList.get(i).getAddress().equals(address)) {
                fireStationList.remove(i);
                break;
            }
        }
        saveFireStationData(fireStationList);
    }


    public void addMedicalRecord(
            String firstName, String lastName, String birthday, List<String> medications, List<String> allergies
    ) {
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthday, medications, allergies);

        medicalRecordList.add(medicalRecord);

        saveMedicalRecordData(medicalRecordList);
    }

    //TODO not updating the existing strings in the List<String> for medication and allergies.
    public void updateMedicalRecord(
            String firstName, String lastName, String birthday, List<String> medications, List<String> allergies
    ) {
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if ((medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))) {
                medicalRecord.setBirthdate(birthday);
                medicalRecord.setMedications(medications);
                medicalRecord.setAllergies(allergies);
            }
        }
        saveMedicalRecordData(medicalRecordList);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        int sizeOfMedicalRecordsList = medicalRecordList.size();
        for (int i = 0; i < sizeOfMedicalRecordsList; i++) {
            if (medicalRecordList.get(i).getFirstName().equals(firstName)
                    && medicalRecordList.get(i).getLastName().equals(lastName)) {
                medicalRecordList.remove(i);
                break;
            }
        }
        saveMedicalRecordData(medicalRecordList);
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
            if (isThisPersonAnAdult(personLightDTO)) {
                payLoad.incrementAdult();
            } else {
                payLoad.incrementChild();
            }
        }
        return payLoad;
    }

    //Class util (CalculUtil par exemple) - comme le reste des calcul
    private boolean isThisPersonAnAdult(PersonLightDTO personLightDTO) {
        boolean result = false;
        LocalDate nowTime = LocalDate.now();
        LocalDate dateOfBirth = null;
        Period period;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //better to do a fori loop with a break ?
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (personLightDTO.getFirstName().equals(medicalRecord.getFirstName()) && personLightDTO.getLastName().equals(medicalRecord.getLastName())) {
                try {
                    dateOfBirth = LocalDate.parse(medicalRecord.getBirthdate(), formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (dateOfBirth != null) {
            period = Period.between(dateOfBirth, nowTime);

            result = period.getYears() >= 18;
        }
        return result;
    }
}
