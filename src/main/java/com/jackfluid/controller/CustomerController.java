package com.jackfluid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jackfluid.entity.Customer;
import com.jackfluid.repo.mongo.CustomerRepo;

@RestController
@RequestMapping(CustomerController.CUSTOMERS_API_URL)
public class CustomerController {
	
	public static final String CUSTOMERS_API_URL = "/customers";
	
	@Autowired
	protected CustomerRepo custRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	public Customer createCustomer(@RequestBody @Valid Customer customer){
		return custRepo.save(customer);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Customer> getAllCustomers(){
		return custRepo.findAll();
	}
}
