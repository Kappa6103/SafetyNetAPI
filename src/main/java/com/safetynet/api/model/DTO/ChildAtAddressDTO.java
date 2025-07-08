package com.safetynet.api.model.DTO;

import com.safetynet.api.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChildAtAddressDTO {

    List<ChildDTO> listOfChild;

    List<Person> listOfAdult;

}
