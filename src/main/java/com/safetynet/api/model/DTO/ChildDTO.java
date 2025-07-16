package com.safetynet.api.model.DTO;

import com.safetynet.api.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChildDTO {

    private String firstName;

    private String lastName;

    private int age;

}
