package com.safetynet.api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonForDwellingDTO {

    String nameWithMedicationsAndAllergies;

    String phoneNumber;

    int age;

}
