package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// GESTION DES INTERACTIONS ENTRE L'UTILISATEUR DE L'APPLICATION ET L'APPLICATION
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    SafetyNetService safetyNetService;

    @GetMapping
    public Person greeting() {
        return safetyNetService.testMethodPerson();
    }

    @PostMapping
    public void addPerson(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "zip") String zip,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email
            ) {
        safetyNetService.addPerson(firstName, lastName, address, city, zip, phone, email);
    }

    //TODO update just one field ?
    @PutMapping
    public void updatePerson(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "zip") String zip,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email
            ) {
        safetyNetService.updatePerson(firstName, lastName, address, city, zip, phone, email);
    }

    @DeleteMapping
    private void deletePerson(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
            ) {
        safetyNetService.deletePerson(firstName, lastName);
    }

}
