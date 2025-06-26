package com.safetynet.api.util;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.DTO.PersonLightDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class CalculUtil {

    public boolean isThisPersonAnAdult(PersonLightDTO personLightDTO, List<MedicalRecord> medicalRecordList) {
        boolean result = false;
        LocalDate nowTime = LocalDate.now();
        LocalDate dateOfBirth = null;
        Period period;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //better to do a fori loop with a break ?
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (personLightDTO.getFirstName().equals(medicalRecord.getFirstName()) && personLightDTO.getLastName().equals(medicalRecord.getLastName())) {
                try {
                    dateOfBirth = LocalDate.parse(medicalRecord.getBirthdate(), formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (dateOfBirth != null) {
            period = Period.between(dateOfBirth, nowTime);

            result = period.getYears() >= 18;
        }
        return result;
    }
}
