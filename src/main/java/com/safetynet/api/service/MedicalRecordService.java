package com.safetynet.api.service;

import com.safetynet.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    DataWrapper dataWrapper;

    @Autowired
    private List<MedicalRecord> medicalRecordList;

    //TODO some logging maybe ?
//    @PostConstruct
//    private void init() {
//        dataWrapper = dataRepository.getDataWrapper();
//        medicalRecordList = dataExtractionUtil.getListOfMedicalRecords(dataWrapper);
//    }

    public MedicalRecord testMethodMedicalRecord() {
        return medicalRecordList.getLast();
    }

    public void addMedicalRecord(
            String firstName, String lastName, String birthday, List<String> medications, List<String> allergies
    ) {
        MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName, birthday, medications, allergies);

        medicalRecordList.add(medicalRecord);

        dataWrapper.setMedicalRecords(medicalRecordList);
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
            }
        }
        dataWrapper.setMedicalRecords(medicalRecordList);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        int sizeOfMedicalRecordsList = medicalRecordList.size();
        for (int i = 0; i < sizeOfMedicalRecordsList; i++) {
            if (medicalRecordList.get(i).getFirstName().equals(firstName)
                    && medicalRecordList.get(i).getLastName().equals(lastName)) {
                medicalRecordList.remove(i);
                break;
            }
        }
        dataWrapper.setMedicalRecords(medicalRecordList);
    }

}
