package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// IMPLEMENTATION DES OBJETS METIERS QUI SERONT MANIPULES PAR LES AUTRES COUCHES
@Data
public class DataWrapper {
    //TODO singleton class ?

    @JsonProperty("persons")
    private Iterable<Person> personIterable;

    @JsonProperty("firestations")
    private Iterable<FireStation> fireStationIterable;

    @JsonProperty("medicalrecords")
    private Iterable<MedicalRecord> medicalRecordIterable;

}
