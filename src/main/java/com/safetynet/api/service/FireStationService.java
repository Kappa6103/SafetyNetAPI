package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FireStationService {

    @Autowired
    DataWrapper dataWrapper;

    @Autowired
    private List<FireStation> fireStationList;

    public FireStation testMethodFireStation() {
        FireStation fireStation = fireStationList.getLast();
        log.debug("FireStationService, testMethodFireStation(), returning last fire station {}", fireStation.getAddress());
        return fireStation;
    }

    public void addFireStation(String address, String station) {

        FireStation fireStation = new FireStation(address, station);
        log.debug("FireStationService, addFireStation(), creating the fire station {} number {}", address, station);
        fireStationList.add(fireStation);
        log.debug("FireStationService, addFireStation(), adding the fire station {} number {} to the list", address, station);
        dataWrapper.setFireStations(fireStationList);
        log.debug("FireStationService, addFireStation(), updating the fireStations list of the data wrapper");
    }

    //TODO using a null safe .equals methode. is it working ?
    public void updateFireStation(String address, String station) {
        for (FireStation fireStation : fireStationList) {
            if (Objects.equals(fireStation.getAddress(), address)) {
                fireStation.setStation(station);
                log.debug("FireStationService, updateFireStation(), " +
                        "updating the fire station {} number {}", address, station);
            }
        }
        dataWrapper.setFireStations(fireStationList);
        log.debug("FireStationService, updateFireStation(), updating the fireStations list of the data wrapper");
    }

    public void deleteStation(String address, String station) {
        int sizeOfFireStationList = fireStationList.size();
        for (int i = 0; i < sizeOfFireStationList; i++) {
            if (Objects.equals(fireStationList.get(i).getAddress(), address)) {
                fireStationList.remove(i);
                log.debug("FireStationService, deleteStation(), deleting the fire station {} number {}", address, station);
                break;
            }
        }
        dataWrapper.setFireStations(fireStationList);
        log.debug("FireStationService, deleteStation(), updating the fireStation list of the data wrapper");
    }

}
