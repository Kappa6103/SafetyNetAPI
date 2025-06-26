package com.safetynet.api.model.DTO;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PeopleCoveredByFireStationDTO {

    private List<PersonLightDTO> personLightDTOList;

    private int numberOfAdult = 0;

    private int numberOfChild = 0;

}
