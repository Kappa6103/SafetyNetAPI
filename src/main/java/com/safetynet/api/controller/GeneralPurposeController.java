package com.safetynet.api.controller;

import com.safetynet.api.model.DTO.PeopleCoveredByFireStationDTO;
import com.safetynet.api.service.GeneralPurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralPurposeController {

    @Autowired
    GeneralPurposeService generalPurposeService;

    @GetMapping(value = "/firestation", params = "stationNumber")
    public PeopleCoveredByFireStationDTO peopleCoveredByFireStation(
            @RequestParam(value = "stationNumber") String station) {
        return generalPurposeService.findPeopleCoveredByFireStation(station);
    }
}
