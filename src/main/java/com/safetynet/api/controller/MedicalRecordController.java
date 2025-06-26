package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.GeneralPurposeService;
import com.safetynet.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping
    public MedicalRecord greeting() {
        return medicalRecordService.testMethodMedicalRecord();
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
    }

    @DeleteMapping
    public void deleteMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
    ) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
