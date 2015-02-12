package com.jackfluid.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jackfluid.dto.TwitterFeedReadDto;
import com.jackfluid.entity.Tweet;
import com.jackfluid.repo.mongo.TwitterStatusRepo;
import com.jackfluid.util.JacksonUtil;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterFeedReader {
	
	private static final Logger logger = LoggerFactory.getLogger(TwitterFeedReader.class);
	
	
	@Value("${twitter.api.consumerKey}")
	protected String consumerKey;
	
	@Value("${twitter.api.consuerSecret}")
	protected String consumerSecret;
	
	@Value("${twitter.api.token}")
	protected String token;
	
	@Value("${twitter.api.secret}")
	protected String secret;
	
	@Autowired
	protected TwitterStatusRepo tsRepo;
	
	public List<Status> read(TwitterFeedReadDto dto) {
		try{
			return readStream(dto);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	protected List<Status> readStream(TwitterFeedReadDto dto) throws Exception {
		
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList("twitterapi", dto.getQ()));

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

		// Do whatever needs to be done with messages
		long startTime = System.currentTimeMillis();
		long totalDuration;
		List<Status> result = Lists.newArrayList();
		for (int msgRead = 0; msgRead < dto.getMax(); msgRead++) {
			String msg = queue.poll(dto.getSecs(), TimeUnit.SECONDS);
			if(!Strings.isNullOrEmpty(msg)){
				// De-serialize JSON
				Status status = TwitterObjectFactory.createStatus(msg);
				if("en".equalsIgnoreCase(status.getLang())){
					tsRepo.save(status);
					result.add(status);
					Tweet tweet = new Tweet(status);
					logger.info(JacksonUtil.toString(tweet));
				}
			}
			totalDuration = System.currentTimeMillis()-startTime;
			if(totalDuration>dto.getSecs()*1000){
				break;
			}
		}

		client.stop();
		return result;
	}
}
