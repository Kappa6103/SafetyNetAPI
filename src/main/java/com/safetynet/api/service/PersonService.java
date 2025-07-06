package com.safetynet.api.service;

import com.safetynet.api.model.*;
import com.safetynet.api.repository.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PersonService {

    @Autowired
    DataWrapper dataWrapper;

    @Autowired
    private List<Person> personList;

    public Person testMethodPerson() {
        Person person = personList.getLast();
        log.debug("PersonService, testMethodPerson(), returning the last person {} {}",
                person.getFirstName(), person.getLastName());
        return person;
    }

    public void addPerson(
            String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        Person newPerson = new Person(firstName, lastName, address, city, zip, phone, email);
        log.debug("PersonService, addPerson(), creating the person {} {}", firstName, lastName);
        personList.add(newPerson);
        log.debug("PersonService, addPerson(), adding the person {} {} to the list", firstName, lastName);
        dataWrapper.setPersons(personList);
        log.debug("PersonService, addPerson(), updating the personList list of the data wrapper");
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
                log.debug("PersonService, updatePerson(), updating the person {} {}", firstName, lastName);
            }
        }
        dataWrapper.setPersons(personList);
        log.debug("PersonService, updatePerson(), updating the personList list of the data wrapper");
    }

    public void deletePerson(String firstName, String lastName) {
        int sizeOfList = personList.size();
        for (int i = 0; i < sizeOfList; i++) {
            if (personList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName)) {
                personList.remove(i);
                log.debug("PersonService, deletePerson(), deleting the person {} {}", firstName, lastName);
                break;
            }
        }
        dataWrapper.setPersons(personList);
        log.debug("PersonService, deletePerson(), updating the personList list of the data wrapper");
    }

}
