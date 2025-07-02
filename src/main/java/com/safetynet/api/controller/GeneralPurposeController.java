package com.safetynet.api.controller;

import com.safetynet.api.model.DTO.ChildDTO;
import com.safetynet.api.model.DTO.PeopleCoveredByFireStationDTO;
import com.safetynet.api.service.GeneralPurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneralPurposeController {

    @Autowired
    GeneralPurposeService generalPurposeService;

    @GetMapping(value = "/firestation", params = "stationNumber")
    public PeopleCoveredByFireStationDTO peopleCoveredByFireStation(
            @RequestParam(value = "stationNumber") String station) {
        return generalPurposeService.findPeopleCoveredByFireStation(station);
    }

    @GetMapping(value = "/childAlert", params = "address")
    public List<ChildDTO> childAtAddress(@RequestParam(value = "address") String address) {
        return generalPurposeService.findChildAtAddress(address);
    }

    @GetMapping(value = "/phoneAlert", params = "firestation")
    public List<String> phoneNumbers(
            @RequestParam(value = "firestation") String firestationNumber) {
        return generalPurposeService.findPhoneNumbersCoveredByFireStation(firestationNumber);

    }

    @GetMapping(value = "/fire", params = "address")
    public List<inhabitantDTO> getAllDetailOfInhabitantsAtAddress (
            @RequestParam(value = "address") String address) {
        return generalPurposeService.getDetailListOfInhabitants(address);
    }

}
