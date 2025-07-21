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

    /**
     * Created to test the endpoint
     * @return the last fire station of the list
     */
    @GetMapping
    public ResponseEntity<?> greeting() {
        FireStation fireStation = fireStationService.testMethodFireStation();

        if (fireStation != null) {
            log.info("@GetMapping reached in the PersonController. Getting the last fire station in the list: {} {}",
                    fireStation.getAddress(), fireStation.getStation());
            return ResponseEntity.ok(fireStation);
        } else {
            String errorMessage = "error fetching last fire station in list";
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> addFireStation(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "station", required = false) String station
    ) {
        if (StringUtils.hasText(address) && StringUtils.hasText(station)) {
            fireStationService.addFireStation(address, station);
            log.info("@PostMapping reached in the FireStationController. Adding the fire station {} number {} to the list",
                    address, station);
            return ResponseEntity.ok("FireStation added successfully");
        } else {
            return errorHandler();
        }
    }

    @PutMapping
    public ResponseEntity<String > updateFireStation(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "station", required = false) String station
    ) {
        if (StringUtils.hasText(address) && StringUtils.hasText(station)) {
            fireStationService.updateFireStation(address, station);
            log.info("@PutMapping reached in the FireStationController. Updating the fire station {} with it's new number {}",
                    address, station);
            String message = String.format("Update successful, fireStation %s has the new number %s", address, station);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFireStation(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "station", required = false) String station
    ) {
        if (StringUtils.hasText(address) && StringUtils.hasText(station)) {
            fireStationService.deleteStation(address, station);
            log.info("@DeleteMapping reached in the FireStationController. Deleting the fire station {} {}", address, station);
            String message = String.format("deletion succesful, fireStation %s was deleted", address);
            return ResponseEntity.ok().body(message);
        } else {
            return errorHandler();
        }
    }

    private ResponseEntity<String> errorHandler() {
        String errorMessage = "Both address and station must be filled";
        log.error(errorMessage);
        return ResponseEntity
                .badRequest()
                .body(errorMessage);
    }

}
