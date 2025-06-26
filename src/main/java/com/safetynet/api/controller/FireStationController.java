package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import com.safetynet.api.service.GeneralPurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    FireStationService fireStationService;

    @GetMapping
    public FireStation greeting() {
        return fireStationService.testMethodFireStation();
    }

    @PostMapping
    public void addFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.addFireStation(address, station);
    }

    @PutMapping
    public void updateFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.updateFireStation(address, station);
    }

    @DeleteMapping
    public void deleteFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.deleteStation(address, station);
    }

}
