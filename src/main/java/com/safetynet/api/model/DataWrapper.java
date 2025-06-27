package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// IMPLEMENTATION DES OBJETS METIERS QUI SERONT MANIPULES PAR LES AUTRES COUCHES
@Data
public class DataWrapper {

    @JsonProperty("persons")
    private List<Person> persons;

    @JsonProperty("firestations")
    private List<FireStation> fireStations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;

}
