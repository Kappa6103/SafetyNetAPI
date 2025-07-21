package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

    /**
     * Created to test the endpoint
     * @return the last medical record in the list
     */
    @GetMapping
    public ResponseEntity<?> greeting() {
        MedicalRecord medicalRecord = medicalRecordService.testMethodMedicalRecord();
        if (medicalRecord != null) {
            log.info(
                    "@GetMapping reached in the MedicalRecordController. Getting the last medical record in the list: {} {}"
                    ,medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.ok(medicalRecord);
        } else {
            String errorMessage = "error fetching last medical record in the list";
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> addMedicalRecord(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "birthday", required = false) String birthday,
            @RequestParam(value = "medications", required = false) List<String> medications,
            @RequestParam(value = "allergies", required = false) List<String> allergies
    ) {
        if (isInputFilled(firstName,lastName,birthday,medications,allergies)) {
            medicalRecordService.addMedicalRecord(firstName, lastName, birthday, medications, allergies);
            log.info("@PostMapping reached in the MedicalRecordController." +
                    "Adding a medical record for {} {}", firstName, lastName);
            String message = String.format("successful creating of a medical record for %s %s", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMedicalRecord(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "birthday", required = false) String birthday,
            @RequestParam(value = "medications", required = false) List<String> medications,
            @RequestParam(value = "allergies", required = false) List<String> allergies
    ) {
        if (isInputFilled(firstName, lastName, birthday, medications, allergies)) {
            medicalRecordService.updateMedicalRecord(firstName, lastName, birthday, medications, allergies);
            log.info("@PutMapping reached in the MedicalRecordController" +
                    "Updating the medical record of {} {}", firstName, lastName);
            String message = String.format("Successful update of %s %s's medical record", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName
    ) {
        if (isInputFilled(firstName, lastName)) {
            medicalRecordService.deleteMedicalRecord(firstName, lastName);
            log.info("@DeleteMapping reached in the MedicalRecordController" +
                    "Deleting the medical record of {} {}", firstName, lastName);
            String message = String.format("%s %s's medical record has been successfully deleted", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    private ResponseEntity<String> errorHandler() {
        String errorMessage = "All parameters must be filled";
        log.error(errorMessage);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private boolean isInputFilled(String firstName, String lastName, String birthday, List<String> medications,
                                  List<String> allergies) {
        return StringUtils.hasText(firstName) && StringUtils.hasText(lastName)
                && StringUtils.hasText(birthday) && !medications.isEmpty() && !allergies.isEmpty();
    }

    private boolean isInputFilled(String firstName, String lastName) {
        return StringUtils.hasText(firstName) && StringUtils.hasText(lastName);
    }

}
