package com.safetynet.api.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DwellingDTO {

    String Address;

    List<PersonForDwellingDTO> personForDwellingDTOList;

}
