package com.jackfluid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jackfluid.entity.Customer;
import com.jackfluid.repo.mongo.CustomerRepo;

@RestController
public class HomeController {

	@Autowired
	private CustomerRepo repository;


	@RequestMapping("/")
	public String index() {
		insertCustomers();
		return "Greetings from Spring Boot!";
	}
	
	public void insertCustomers() {

//		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Jones"));
		repository.save(new Customer("Bob", "Jones"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Jones'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Jones")) {
			System.out.println(customer);
		}
	}

}
