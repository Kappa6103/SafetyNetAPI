package com.safetynet.api.service;

// IMPLEMENTATION DES TRAITEMENTS METIERS SPECIFIQUES A L'APPLICATION

import com.safetynet.api.model.DataWrapper;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SafetyNetService {

    DataRepository dataRepository = new DataRepository();

    DataWrapper dataWrapper = dataRepository.getDataWrapper();

    DataExtraction dataExtraction = new DataExtraction(dataWrapper);

    // NOT DRY - > SAME CODE AS DATA EXTRACTION
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

    public void findPeopleCoveredByFireStation(String station) {
        //TODO cut the String to remove house numbers ?

        List<FireStation> stations = new ArrayList<>();
        List<Person> personAroundTheStation = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            if (fireStation.getStation().equals(station)) {
                stations.add(fireStation);
            }
        }

        for (Person person : personList) {
            for (FireStation fireStation : stations) {
                if (person.getAddress().equals(fireStation.getAddress())) {
                    personAroundTheStation.add(person);
                }
            }
        }

    }
}
