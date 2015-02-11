package com.jackfluid.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.jackfluid.test.SpringEnabledWebTest;

public class TwitterFeedControllerIntTest extends SpringEnabledWebTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testPopularTwitterTerm(){
		String url = twitterFeedUrl+"?q=obama";
		ResponseEntity<Object> response = template.getForEntity(url, Object.class);
		assertThat(response.getBody() instanceof List, equalTo(true));
		List<String> result = (List<String>) response.getBody();
		assertThat(result, hasSize(greaterThanOrEqualTo(1)));
		System.out.println(result);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testObscureTwitterTerm(){
		String url = twitterFeedUrl+"?q=Feudalism&duration=5";
		ResponseEntity<Object> response = template.getForEntity(url, Object.class);
		assertThat(response.getBody() instanceof List, equalTo(true));
		List<String> result = (List<String>) response.getBody();
		// The import thing to test here is whether the reader times out.
		assertThat(result, hasSize(greaterThanOrEqualTo(0)));
		System.out.println(result);
	}
	
	
}
