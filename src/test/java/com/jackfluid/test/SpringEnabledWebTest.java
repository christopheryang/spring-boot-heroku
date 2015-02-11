package com.jackfluid.test;

import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.jackfluid.app.Application;
import com.jackfluid.controller.CustomerController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"}) // Random port
@Ignore
public class SpringEnabledWebTest {

	@Value("${local.server.port}") // Actual port
	protected int port;

	protected URL base;
	protected RestTemplate template;

	protected String customersUrl;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port);
		customersUrl = base.toString()+CustomerController.CUSTOMERS_API_URL;
		template = new TestRestTemplate();
	}

}
