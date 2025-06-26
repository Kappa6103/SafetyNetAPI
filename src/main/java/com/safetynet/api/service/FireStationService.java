package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataRepository;
import com.safetynet.api.util.DataExtractionUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    private DataExtractionUtil dataExtractionUtil;

    private List<FireStation> fireStationList;

    private DataWrapper dataWrapper;

    @PostConstruct
    private void init() {
        dataWrapper = dataRepository.getDataWrapper();
        fireStationList = dataExtractionUtil.getListOfFireStations(dataWrapper);
    }

    public FireStation testMethodFireStation() {
        return fireStationList.getLast();
    }

    public void saveFireStationData(List<FireStation> fireStationList) {

        dataWrapper.setFireStationIterable(fireStationList);

        dataRepository.writeDataWrapper(dataWrapper);

    }

    public void addFireStation(String address, String station) {

        FireStation fireStation = new FireStation(address, station);

        fireStationList.add(fireStation);

        saveFireStationData(fireStationList);
    }

    public void updateFireStation(String address, String station) {
        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddress().equals(address)) {
                fireStation.setStation(station);
            }
        }
        saveFireStationData(fireStationList);
    }

    public void deleteStation(String address, String station) {
        int sizeOfFireStationList = fireStationList.size();
        for (int i = 0; i < sizeOfFireStationList; i++) {
            if (fireStationList.get(i).getAddress().equals(address)) {
                fireStationList.remove(i);
                break;
            }
        }
        saveFireStationData(fireStationList);
    }


}
