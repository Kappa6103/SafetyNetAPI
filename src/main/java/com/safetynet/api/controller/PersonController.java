package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * This is the Rest controller for the endpoint "/person" using its appropriate service class {@link com.safetynet.api.service.PersonService}
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    /**
     * Created to test the endpoint
     * @return the last person in the list
     */
    @GetMapping
    public ResponseEntity<?> greeting() {
        Person person = personService.testMethodPerson();
        if (person != null) {
            log.info("@GetMapping reached in the PersonController. Getting the last person in the list: {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.ok(person);
        } else {
            String errorMessage = "error fetching last person in the list";
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> addPerson(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
            ) {
        if (isInputFilled(firstName, lastName, address, city, zip, phone, email)) {
            personService.addPerson(firstName, lastName, address, city, zip, phone, email);
            log.info("@PostMapping reached in the the PersonController. Adding {} {} to the list of people", firstName, lastName);
            String message = String.format("Successful creation of the person %s %s", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    //TODO update just one field ?
    @PutMapping
    public ResponseEntity<String> updatePerson(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
            ) {
        if (isInputFilled(firstName, lastName, address, city, zip, phone, email)) {
            personService.updatePerson(firstName, lastName, address, city, zip, phone, email);
            log.info("@PutMapping reached in the PersonController. Updating {} {}", firstName, lastName);
            String message = String.format("Profile of %s %s successfully updated", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName
            ) {
        if (isInputFilled(firstName, lastName)) {
            personService.deletePerson(firstName, lastName);
            log.info("@DeleteMapping reached in the PersonController. Deleting {} {}", firstName, lastName);
            String message = String.format("Successfully deleting the person %s %s", firstName, lastName);
            return ResponseEntity.ok(message);
        } else {
            return errorHandler();
        }
    }

    private ResponseEntity<String> errorHandler() {
        String errorMessage = "All parameters must be filled";
        log.error(errorMessage);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private boolean isInputFilled(String firstName, String lastName, String address, String city, String zip
            , String phone, String email) {
        return StringUtils.hasText(firstName) && StringUtils.hasText(lastName) &&
                StringUtils.hasText(address) && StringUtils.hasText(city) &&
                StringUtils.hasText(zip) && StringUtils.hasText(phone) &&
                StringUtils.hasText(email);
    }

    private boolean isInputFilled(String firstName, String lastName) {
        return StringUtils.hasText(firstName) && StringUtils.hasText(lastName);
    }
}
