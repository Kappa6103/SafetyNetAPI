package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.DataWrapper;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;

// INTERACTION AVEC LES SOURCES DE DONNEES EXTERNES
@Repository
public class DataRepository {

    private DataWrapper dataWrapper;

    //TODO : should be a singleton ?

    ObjectMapper objectMapper = new ObjectMapper();

    //TODO: pass the json file path as an argument ?
    public DataWrapper getDataWrapper() {

        try {
            dataWrapper = objectMapper.readValue(new File("src/main/resources/data.json"), DataWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataWrapper;
    }

    public void writeDataWrapper(DataWrapper dataWrapper) {
        //TODO: if dataset modified save to json file
        try {
            objectMapper.writeValue(new File("src/main/resources/data.json"), dataWrapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
