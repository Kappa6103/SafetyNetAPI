package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    FireStationService fireStationService;

    //TODO out of the exercise's scope
    @GetMapping
    public FireStation greeting() {
        FireStation fireStation = fireStationService.testMethodFireStation();
        log.info("@GetMapping reached in the PersonController. Getting the last fire station in the list: {} {}",
                fireStation.getAddress(), fireStation.getStation());
        return fireStationService.testMethodFireStation();
    }

    @PostMapping
    public void addFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.addFireStation(address, station);
        log.info("@PostMapping reached in the FireStationController. Adding the fire station {} number {} to the list",
                address, station);
    }

    @PutMapping
    public void updateFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.updateFireStation(address, station);
        log.info("@PutMapping reached in the FireStationController. Updating the fire station {} with it's new number {}",
                address, station);
    }

    @DeleteMapping
    public void deleteFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        fireStationService.deleteStation(address, station);
        log.info("@DeleteMapping reached in the FireStationController. Deleting the fire station {} {}", address, station);
    }

}
