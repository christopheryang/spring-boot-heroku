package com.jackfluid.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.jackfluid.configuration.AppContext;
import com.jackfluid.entity.Customer;
import com.jackfluid.repo.mongo.CustomerRepo;

@EnableAutoConfiguration
@Import(AppContext.class)
public class Application implements CommandLineRunner {
    
	@Autowired
	private CustomerRepo repository;

	public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
    
	@Override
	public void run(String... args) throws Exception {

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
