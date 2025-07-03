package com.safetynet.api.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class InhabitantDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;

}
