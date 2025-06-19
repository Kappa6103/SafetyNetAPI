package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.DataWrapper;
import java.io.File;
import java.io.IOException;

public class DataRepository {

    ObjectMapper objectMapper = new ObjectMapper();

    public DataWrapper getDataWrapper() {
        DataWrapper dataWrapper;
        try {
            dataWrapper = objectMapper.readValue(new File("src/main/resources/data.json"), DataWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataWrapper;
    }

    public void setDataWrapper(DataWrapper dataWrapper) {
        //TODO: if dataset modified save to json file
    }
}
