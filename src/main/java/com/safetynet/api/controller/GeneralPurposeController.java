package com.safetynet.api.controller;

import com.safetynet.api.model.DTO.*;
import com.safetynet.api.service.GeneralPurposeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GeneralPurposeController {

    @Autowired
    GeneralPurposeService generalPurposeService;

    @GetMapping(value = "/firestation", params = "stationNumber")
    public PeopleCoveredByFireStationDTO peopleCoveredByFireStation(
            @RequestParam(value = "stationNumber") String station) {
        log.info("GeneralPurposeController reached. Fetching all people covered by the station number {}", station);
        return generalPurposeService.findPeopleCoveredByFireStation(station);
    }

    @GetMapping(value = "/childAlert", params = "address")
    public ChildAtAddressDTO childAtAddress(@RequestParam(value = "address") String address) {
        log.info("GeneralPurposeController reached. Fetching all the kids at address {}", address);
        return generalPurposeService.findChildAtAddress(address);
    }

    @GetMapping(value = "/phoneAlert", params = "firestation")
    public List<String> phoneNumbers(
            @RequestParam(value = "firestation") String firestationNumber) {
        log.info("GeneralPurposeController reached. Fetching all phone numbers covered by the station number {}"
                , firestationNumber);
        return generalPurposeService.findPhoneNumbersCoveredByFireStation(firestationNumber);

    }

    @GetMapping(value = "/fire", params = "address")
    public DetailListOfInhabitantsDTO getAllDetailOfInhabitantsAtAddress (
            @RequestParam(value = "address") String address) {
        log.info("GeneralPurposeController reached. Fetching detailed list of people living at the address {}"
                , address);
        return generalPurposeService.getDetailListOfInhabitants(address);
    }

    @GetMapping(value = "/flood/stations", params = "stations")
    public List<DwellingDTO> detailListOfDwellingCoveredByFireStation(
            @RequestParam(value = "stations") List<String> stations) {
        log.info("GeneralPurposeController reached. Fetching the detailed list of Dwelling covered by the stations {}",
                stations);
        return generalPurposeService.getDetailListOfDwelling(stations);
    }

    @GetMapping(value = "/personInfo", params = "lastName")
    public List<PersonInfoLastNameDTO> personInfoLastNameDTO(
            @RequestParam(value = "lastName") String lastName) {
        log.info("GeneralPurposeController reached. Fetching the list of people sharing the same last name {}", lastName);
        return generalPurposeService.getPersonInfoByLastName(lastName);
    }

    @GetMapping(value = "/communityEmail", params = "city")
    public EmailListOfCityInhabitants fetchingAllEmailsOfInhabitants(@RequestParam(value = "city") String city) {
        log.info("GeneralPurposeController reached. Fetching all the emails of the city inhabitants");
        return generalPurposeService.getEmailsOfInhabitants(city);
    }
}
