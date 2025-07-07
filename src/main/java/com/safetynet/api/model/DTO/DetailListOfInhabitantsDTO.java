package com.safetynet.api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DetailListOfInhabitantsDTO {

    List<InhabitantDTO> listOfInhabitant;

    int fireStation;

}
