package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataWrapper {

    @JsonProperty("persons")
    private List<Person> personList;

    @JsonProperty("firestations")
    private List<FireStation> fireStationList;

    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecordList;

}
