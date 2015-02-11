package com.jackfluid.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.jackfluid.test.SpringEnabledWebTest;

public class HomeControllerIntTest extends SpringEnabledWebTest {

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), notNullValue());
		System.out.println(response.getBody());
	}
	
}