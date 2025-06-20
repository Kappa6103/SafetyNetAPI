package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    SafetyNetService safetyNetService;

    @GetMapping
    public FireStation greeting() {
        return safetyNetService.testMethodFireStation();
    }

    @PostMapping
    public void addFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        safetyNetService.addFireStation(address, station);
    }

    @PutMapping
    public void updateFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        safetyNetService.updateFireStation(address, station);
    }

    @DeleteMapping
    public void deleteFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        safetyNetService.deleteStation(address, station);
    }


}
