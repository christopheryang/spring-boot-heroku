package com.jackfluid.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.jackfluid.entity.Customer;
import com.jackfluid.test.SpringEnabledWebTest;

public class CustomerControllerIntTest extends SpringEnabledWebTest {
	
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer("John", "Smith");
		ResponseEntity<Customer> response = template.postForEntity(customersUrl, customer, Customer.class);
		assertThat(response.getBody(), notNullValue());
		assertThat(response.getBody().getId(), notNullValue());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindAllCustomers(){
		ResponseEntity<Object> response = template.getForEntity(customersUrl, Object.class);
		assertThat(response.getBody() instanceof List, equalTo(true));
		List<Customer> result = (List<Customer>) response.getBody();
		assertThat(result, hasSize(greaterThanOrEqualTo(1)));
		System.out.println(result);
	}
}
