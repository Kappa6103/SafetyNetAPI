package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This is the Rest controller for the endpoint "/medicalRecord" using its appropriate service class {@link com.safetynet.api.service.MedicalRecordService}
 */
@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    //TODO out of the exercise's scope
    @GetMapping
    public MedicalRecord greeting() {
        MedicalRecord medicalRecord = medicalRecordService.testMethodMedicalRecord();
        log.info(
                "@GetMapping reached in the MedicalRecordController. Getting the last medical record in the list: {} {}"
                ,medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecord;
    }

    @PostMapping
    public void addMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "medications") List<String> medications,
            @RequestParam(value = "allergies") List<String> allergies
    ) {
        medicalRecordService.addMedicalRecord(firstName, lastName, birthday, medications, allergies);
        log.info("@PostMapping reached in the MedicalRecordController." +
                "Adding a medical record for {} {}", firstName, lastName);
    }

    @PutMapping
    public void updateMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "medications") List<String> medications,
            @RequestParam(value = "allergies") List<String> allergies
    ) {
        medicalRecordService.updateMedicalRecord(firstName, lastName, birthday, medications, allergies);
        log.info("@PutMapping reached in the MedicalRecordController" +
                "Updating the medical record of {} {}", firstName, lastName);
    }

    @DeleteMapping
    public void deleteMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
    ) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        log.info("@DeleteMapping reached in the MedicalRecordController" +
                "Deleting the medical record of {} {}", firstName, lastName);
    }

}
