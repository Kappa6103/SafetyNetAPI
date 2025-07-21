package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for the endpoint "/medicalRecord"
 */
@Slf4j
@Service
public class MedicalRecordService {

    @Autowired
    DataWrapper dataWrapper;

    @Autowired
    private List<MedicalRecord> medicalRecordList;

    public MedicalRecord testMethodMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordList.getLast();
        log.debug("MedicalRecord, testMethodMedicalRecord(), returning last medical record : {} {}",
                medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecord;
    }

    public void addMedicalRecord(
            String firstName, String lastName, String birthday, List<String> medications, List<String> allergies
    ) {
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthday, medications, allergies);
        log.debug("MedicalRecordService, addMedicalRecord(), creating the medical record for {} {}", firstName, lastName);
        medicalRecordList.add(medicalRecord);
        log.debug("MedicalRecordService, addMedicalRecord(), adding the medical record of {} {} to the list"
                , firstName, lastName);
        dataWrapper.setMedicalRecords(medicalRecordList);
        log.debug("MedicalRecordService, addMedicalRecord, updating the medicalReportList of the data wrapper");
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
                log.debug("MedicalRecordService, updateMedicalRecord(), updating the medical record of {} {}"
                        , firstName, lastName);
            }
        }
        dataWrapper.setMedicalRecords(medicalRecordList);
        log.debug("MedicalRecordService, updateMedicalRecord(), updating the medical record list of the data wrapper");
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        int sizeOfMedicalRecordsList = medicalRecordList.size();
        for (int i = 0; i < sizeOfMedicalRecordsList; i++) {
            if (medicalRecordList.get(i).getFirstName().equals(firstName)
                    && medicalRecordList.get(i).getLastName().equals(lastName)) {
                medicalRecordList.remove(i);
                log.debug("MedicalRecordService, deleteMedicalRecord(), deleting the medical record of {} {}",
                        firstName, lastName);
                break;
            }
        }
        dataWrapper.setMedicalRecords(medicalRecordList);
        log.debug("MedicalRecordService, deleteMedicalRecord(), updating the medical record list of the data wrapper");
    }

}
