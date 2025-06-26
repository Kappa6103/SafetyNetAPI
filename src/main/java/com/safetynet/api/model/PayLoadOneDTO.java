package com.safetynet.api.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class PayLoadOneDTO {

    private List<PersonLightDTO> personLightDTOList = new ArrayList<>();

    private int numberOfAdult = 0;

    private int numberOfChild = 0;

    public void addAPersonLightDTO(PersonLightDTO personLightDTO) {
        personLightDTOList.add(personLightDTO);
    }

    public void incrementAdult() {
        numberOfAdult++;
    }
    public void incrementChild() {
        numberOfChild++;
    }

    public void reset() {
        this.personLightDTOList.clear();
        this.numberOfChild = 0;
        this.numberOfAdult = 0;
    }
}
