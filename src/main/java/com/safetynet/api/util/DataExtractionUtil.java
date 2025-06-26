package com.safetynet.api.util;

import com.safetynet.api.model.DataWrapper;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataExtractionUtil {

    private final List<Person> listOfPersons = new ArrayList<>();

    private final List<FireStation> listOfFireStations = new ArrayList<>();

    private final List<MedicalRecord> listOfMedicalRecords = new ArrayList<>();


    public List<Person> getListOfPersons(DataWrapper dataWrapper) {
        for (Person person : dataWrapper.getPersonIterable()) {
            listOfPersons.add(person);
        }
        return listOfPersons;
    }

    public List<FireStation> getListOfFireStations(DataWrapper dataWrapper) {
        for (FireStation fireStation : dataWrapper.getFireStationIterable()) {
            listOfFireStations.add(fireStation);
        }
        return listOfFireStations;
    }

    public List<MedicalRecord> getListOfMedicalRecords(DataWrapper dataWrapper) {
        for (MedicalRecord medicalRecord : dataWrapper.getMedicalRecordIterable()) {
            listOfMedicalRecords.add(medicalRecord);
        }
        return listOfMedicalRecords;
    }

}
