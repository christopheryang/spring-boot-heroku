package com.jackfluid.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jackfluid.entity.TweetFeederDto;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TweetFeederService {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetFeederService.class);
	
	
	@Value("${twitter.api.consumerKey}")
	protected String consumerKey;
	
	@Value("${twitter.api.consuerSecret}")
	protected String consumerSecret;
	
	@Value("${twitter.api.token}")
	protected String token;
	
	@Value("${twitter.api.secret}")
	protected String secret;
	
	protected RestTemplate restTemplate = new RestTemplate();
	
	public void run(TweetFeederDto dto) {
		try{
			readStream(dto);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	protected void readStream(TweetFeederDto dto) throws Exception {
		
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);
		StreamingEndpoint endpoint;
		
		if(dto.getSearchTerm()==null){
			endpoint = new StatusesSampleEndpoint();
			((StatusesSampleEndpoint) endpoint).stallWarnings(false);
		}
		else{
			endpoint = new StatusesFilterEndpoint();
			// add some track terms
			((StatusesFilterEndpoint) endpoint).trackTerms(Lists.newArrayList("twitterapi", dto.getSearchTerm()));
		}

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder()
		.hosts(Constants.STREAM_HOST)
		.endpoint(endpoint)
		.authentication(auth)
		.processor(new StringDelimitedProcessor(queue))
		.build();

		// Establish a connection
		client.connect();

		// Keep a track of duration
		long startTime = System.currentTimeMillis();
		long totalDuration;
		
		// Must be a thread-safe list
		List<String> result = Lists.newCopyOnWriteArrayList();
		while (result.size() < dto.getEffectiveMaxResults()) {
			String msg = queue.poll(dto.getEffectiveMaxTotalDuration(), TimeUnit.SECONDS);
			
			// If it's in English, de-serialize JSON, sends of a POST to Tweet consumer.
			if(!Strings.isNullOrEmpty(msg)){
				if(msg.contains("en")){
					result.add(msg);
					logger.info("######## Sending Tweet "+result.size());
					sendTweet(msg, dto.getSendTo());
				}
			}
			totalDuration = System.currentTimeMillis()-startTime;
			if(totalDuration>dto.getEffectiveMaxTotalDuration()*1000){
				logger.warn("######## Exceeded max time duration. Stopping feed after: "+totalDuration/1000+" seconds");
				break;
			}
		}

		client.stop();
	}
	
	protected void sendTweet(String status, String url){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(status, headers);
		restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
	}
}
