package com.safetynet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.DataWrapper;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiApplication.class, args);


		ObjectMapper objectMapper = new ObjectMapper();
//		Person person = new Person("John Doe");
//
//		objectMapper.writeValue(new File("target/person.json"), person);

		Person person = objectMapper.readValue(new File("target/person.json"), Person.class);
		System.out.println(person.getFirstName());


		DataRepository dataRepository = new DataRepository();

		DataWrapper dataWrapper = dataRepository.getDataWrapper();

		// Example: print the first person's name
		System.out.println("First person: " + dataWrapper.getPersonList().get(0).getFirstName());
		Person p1 = dataWrapper.getPersonList().getLast();
		System.out.println(p1.getFirstName());

	}

}
