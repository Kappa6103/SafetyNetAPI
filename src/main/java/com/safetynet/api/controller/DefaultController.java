package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    SafetyNetService safetyNetService;

    @RequestMapping("/firestation")
    @GetMapping(params = "stationNumber")
    public FireStation peopleCoveredByFireStation(
            @RequestParam(value = "stationNumber") String station) {
        return safetyNetService.findPeopleCoveredByFireStation(station);
    }
}
