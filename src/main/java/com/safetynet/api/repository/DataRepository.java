package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This Class read the makeshift json database serialising its data into objects in the data wrapper class
 * {@link com.safetynet.api.repository.DataWrapper}
 * and when called, returns unique lists of different objects models.
 * When the application is shutdown, the class write the changes made to the database.
 */
@Slf4j
@Repository
public class DataRepository {

    @Autowired
    ObjectMapper objectMapper;

    DataWrapper dataWrapper;

    private final String filePath = "src/main/resources/data.json";

    @PostConstruct
    private void init() {
        dataWrapper = getDataWrapper();
        log.debug("Execution of @PostConstruct of the DataRepository. getting the Data wrapper");
    }

    @Bean(name = "personList")
    public List<Person> getListOfPersons() {
        log.debug("Getting the list of persons from the data wrapper, it is a Bean");
        return dataWrapper.getPersons();
    }

    @Bean(name = "fireStationList")
    public List<FireStation> getListOfFireStations() {
        log.debug("Getting the list of fire stations from the data wrapper, it is a Bean");
        return dataWrapper.getFireStations();
    }

    @Bean(name = "medicalRecordList")
    public List<MedicalRecord> getListOfMedicalRecords() {
        log.debug("Getting the list of medical records from the data wrapper, it is a Bean");
        return dataWrapper.getMedicalRecords();
    }

    @Bean
    public DataWrapper getDataWrapper() {
        if (dataWrapper == null) {
            try {
                dataWrapper = objectMapper.readValue(new File(filePath), DataWrapper.class);
                log.debug("Success when deserializing the json file. data wrapper could be instantiated, it is a Bean");
            } catch (IOException e) {
                log.error("Problem when deserializing the json file. dataWrapper could not be instantiated");
                throw new RuntimeException(e);
            }
        }

        return dataWrapper;
    }

    private void writeDataWrapper() {
        try {
            Path makeShiftJsonDB = Paths.get(filePath);
            Files.deleteIfExists(makeShiftJsonDB);
            objectMapper.writeValue(new File(filePath), dataWrapper);
            log.debug("Success when serializing the json file. Data written on disk");
        } catch (IOException e) {
            log.error("Problem when serializing the data wrapper. Data not written on disk");
            throw new RuntimeException(e);
        }
    }

    //TODO do i have to keep it ?
    @PreDestroy
    public void cleanup() {
        Path makeShiftJsonDB = Paths.get(filePath);

        try {
            Files.deleteIfExists(makeShiftJsonDB);
            writeDataWrapper();
            log.debug("Executing the @PreDestroy method of the DataRepository, executing the writeDataWrapper()");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failing to execute the @PreDestroy method of the DataRepository");
        }
    }

}
