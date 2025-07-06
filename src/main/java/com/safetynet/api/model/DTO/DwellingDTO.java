package com.safetynet.api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DwellingDTO {

    String nameWithMedicationsAndAllergies;

    String phoneNumber;

    int age;

}
