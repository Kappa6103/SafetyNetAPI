package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataRepository;
import com.safetynet.api.util.DataExtractionUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    private DataExtractionUtil dataExtractionUtil;

    private DataWrapper dataWrapper;

    private List<Person> personList;

    @PostConstruct
    private void init() {
        dataWrapper = dataRepository.getDataWrapper();
        personList = dataExtractionUtil.getListOfPersons(dataWrapper);
    }

    public Person testMethodPerson() {
        return personList.getLast();
    }

    public void savePersonData(List<Person> personList) {

        dataWrapper.setPersonIterable(personList);

        dataRepository.writeDataWrapper(dataWrapper);

    }

    public void addPerson(
            String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        Person newPerson = new Person(firstName, lastName, address, city, zip, phone, email);

        personList.add(newPerson);

        savePersonData(personList);
    }

    public void updatePerson(
            String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                person.setAddress(address);
                person.setCity(city);
                person.setZip(zip);
                person.setPhone(phone);
                person.setEmail(email);
            }
        }
        savePersonData(personList);
    }

    public void deletePerson(String firstName, String lastName) {
        int sizeOfList = personList.size();
        for (int i = 0; i < sizeOfList; i++) {
            if (personList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName)) {
                personList.remove(i);
                break;
            }
        }
        savePersonData(personList);
    }

}
