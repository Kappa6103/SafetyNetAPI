package com.safetynet.api.model.DTO;

import com.safetynet.api.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class ChildDTO {

    private String firstName;

    private String lastName;

    private int age;
}
