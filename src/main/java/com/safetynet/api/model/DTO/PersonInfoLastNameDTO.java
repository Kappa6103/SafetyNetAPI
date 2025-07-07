package com.safetynet.api.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfoLastNameDTO {
    String firstName;
    String lastName;
    String address;
    int age;
    String email;
    List<String> medications;
    List<String> allergies;

}
