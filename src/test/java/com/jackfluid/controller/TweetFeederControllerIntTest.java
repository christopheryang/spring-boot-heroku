package com.jackfluid.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.jackfluid.test.SpringEnabledWebTest;

public class TweetFeederControllerIntTest extends SpringEnabledWebTest {
	
	@Value("classpath:tweetFeederFilterTest.json")
	protected Resource tweetFeederFilterTestResource;
	
	@Value("classpath:tweetFeederSampleTest.json")
	protected Resource tweetFeederSampleTestResource;
	
	
	@Test
	public void testSampleTweets() throws Exception {
		String json = super.fileResourceToString(tweetFeederSampleTestResource).replace("${port}", port+"");
		testTweetFeeder(json);
	}
	
	@Test
	public void testPopularTwitterTerm() {
		String json = super.fileResourceToString(tweetFeederFilterTestResource).replace("${port}", port+"")
				.replace("${searchTerm}", "obama");
		testTweetFeeder(json);
	}
	
	@Test
	public void testObscureTwitterTerm(){
		String json = super.fileResourceToString(tweetFeederFilterTestResource).replace("${port}", port+"")
				.replace("${searchTerm}", "Feudalism");
		testTweetFeeder(json);
	}
	
	
	protected void testTweetFeeder(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<Object> response = template.exchange(tweetFeederUrl, HttpMethod.POST, requestEntity, Object.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
}
