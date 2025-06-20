package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalRecord")
public class MedicalRecordController {

    @Autowired
    SafetyNetService safetyNetService;

    @GetMapping
    public MedicalRecord greeting() {
        return safetyNetService.testMethodMedicalRecord();
    }

    @PostMapping
    public void addMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "medications") List<String> medications,
            @RequestParam(value = "allergies") List<String> allergies
    ) {
        safetyNetService.addMedicalRecord(firstName, lastName, birthday, medications, allergies);
    }

    @PutMapping
    public void updateMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "medications") List<String> medications,
            @RequestParam(value = "allergies") List<String> allergies
    ) {
        safetyNetService.updateMedicalRecord(firstName, lastName, birthday, medications, allergies);
    }

    @DeleteMapping
    public void deleteMedicalRecord(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
    ) {
        safetyNetService.deleteMedicalRecord(firstName, lastName);
    }

}
