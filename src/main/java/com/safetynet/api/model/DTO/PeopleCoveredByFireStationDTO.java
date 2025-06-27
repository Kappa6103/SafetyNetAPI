package com.safetynet.api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PeopleCoveredByFireStationDTO {

    private List<PersonLightDTO> personLightDTOList;

    private int numberOfAdult = 0;

    private int numberOfChild = 0;

}
