package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// GESTION DES INTERACTIONS ENTRE L'UTILISATEUR DE L'APPLICATION ET L'APPLICATION
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    //TODO c'est un methode test hors du scope du projet - to delete
    @GetMapping
    public Person greeting() {
        Person person = personService.testMethodPerson();
        log.info("@GetMapping reached in the PersonController. Getting the last person in the list: {} {}", person.getFirstName(), person.getLastName());
        return person;
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
        personService.addPerson(firstName, lastName, address, city, zip, phone, email);
        log.info("@PostMapping reached in the the PersonController. Adding {} {} to the list of people", firstName, lastName);
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
        personService.updatePerson(firstName, lastName, address, city, zip, phone, email);
        log.info("@PutMapping reached in the PersonController. Updating {} {}", firstName, lastName);
    }

    @DeleteMapping
    private void deletePerson(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
            ) {
        personService.deletePerson(firstName, lastName);
        log.info("@DeleteMapping reached in the PersonController. Deleting {} {}", firstName, lastName);
    }

}
