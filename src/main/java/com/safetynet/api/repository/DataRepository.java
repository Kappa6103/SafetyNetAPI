package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.DataWrapper;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// INTERACTION AVEC LES SOURCES DE DONNEES EXTERNES
@Repository
public class DataRepository {

    ObjectMapper objectMapper = new ObjectMapper();

    DataWrapper dataWrapper;

    String filePath = "src/main/resources/data.json";

    @PostConstruct
    private void init() {
        dataWrapper = getDataWrapper();
    }

    @Bean(name = "personList")
    public List<Person> getListOfPersons() {
        return dataWrapper.getPersons();
    }

    @Bean(name = "fireStationList")
    public List<FireStation> getListOfFireStations() {
        return dataWrapper.getFireStations();
    }

    @Bean(name = "medicalRecordList")
    public List<MedicalRecord> getListOfMedicalRecords() {
        return dataWrapper.getMedicalRecords();
    }

    @Bean
    public DataWrapper getDataWrapper() {
        try {
            dataWrapper = objectMapper.readValue(new File(filePath), DataWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataWrapper;
    }

    private void writeDataWrapper() {
        try {
            Path makeShiftJsonDB = Paths.get(filePath);
            Files.deleteIfExists(makeShiftJsonDB);
            objectMapper.writeValue(new File(filePath), dataWrapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void cleanup() {
        Path makeShiftJsonDB = Paths.get(filePath);

        try {
            Files.deleteIfExists(makeShiftJsonDB);
            writeDataWrapper();
            System.out.println("changes saved to disk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
