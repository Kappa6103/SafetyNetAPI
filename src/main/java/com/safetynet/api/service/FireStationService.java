package com.safetynet.api.service;

import com.safetynet.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    @Autowired
    DataWrapper dataWrapper;

    @Autowired
    private List<FireStation> fireStationList;

    public FireStation testMethodFireStation() {
        return fireStationList.getLast();
    }

    public void addFireStation(String address, String station) {

        FireStation fireStation = new FireStation(address, station);

        fireStationList.add(fireStation);

        dataWrapper.setFireStations(fireStationList);
    }

    public void updateFireStation(String address, String station) {
        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddress().equals(address)) {
                fireStation.setStation(station);
            }
        }
        dataWrapper.setFireStations(fireStationList);
    }

    public void deleteStation(String address, String station) {
        int sizeOfFireStationList = fireStationList.size();
        for (int i = 0; i < sizeOfFireStationList; i++) {
            if (fireStationList.get(i).getAddress().equals(address)) {
                fireStationList.remove(i);
                break;
            }
        }
        dataWrapper.setFireStations(fireStationList);
    }

}
