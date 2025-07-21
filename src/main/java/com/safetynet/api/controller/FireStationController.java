package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * This is the Rest controller for the endpoint "/firestation" using its appropriate service class {@link com.safetynet.api.service.FireStationService}
 */
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
        return fireStation;
    }

    @PostMapping
    public void addFireStation(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "station") String station
    ) {
        if (StringUtils.hasText(address) && StringUtils.hasText(station)) {
            fireStationService.addFireStation(address, station);
            log.info("@PostMapping reached in the FireStationController. Adding the fire station {} number {} to the list",
                    address, station);
            ResponseEntity.ok("FireStation added successfully");
        } else {

        }
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
