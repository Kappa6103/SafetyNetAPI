package com.safetynet.api.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DataWrapper {

    @JsonProperty("persons")
    private List<Person> persons;

    @JsonProperty("firestations")
    private List<FireStation> fireStations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;

    public List<MedicalRecord> getMedicalRecords() {
        log.debug("DataWrapper, getMedicalRecords(), returning the List<MedicalRecord>");
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
        log.debug("DataWrapper, setMedicalRecords(), setting the List<MedicalRecord>");
    }

    public List<FireStation> getFireStations() {
        log.debug("DataWrapper, getFireStation(), returning the List<FireStation>");
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
        log.debug("DataWrapper, setFireStations(), setting the List<FireStation>");
    }

    public List<Person> getPersons() {
        log.debug("DataWrapper, getPersons(), returning the List<Person>");
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        log.debug("DataWrapper, setPersons(), setting the List<Person>");
    }

}
