package com.jackfluid.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jackfluid.repo.mongo.TwitterStatusRepo;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterFeedReader {
	
	public static final int DEFAULT_MAX_TWEETS = 10;
	
	@Autowired
	protected TwitterStatusRepo tsRepo;
	
	public List<Status> read(String searchTerm, Integer maxRead) {
		maxRead = (maxRead==null)? DEFAULT_MAX_TWEETS: maxRead;
		try{
			return readStream(searchTerm, maxRead);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	protected List<Status> readStream(String searchTerm, int maxRead) throws Exception {
		
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList("twitterapi", searchTerm));

		String consumerKey = "0t1Nn2LzqCa33hUBaqy4Dz2YX";
		String consumerSecret = "w7Nhudjqgrj8ZMwa3or11aJ7jS7bazyjX45MDNTVi7QP7R7oyb";
		String token = "139700982-SJSq4qnKMvdxiW9GCb1w77lHi7fHhbt01VQMFo3j";
		String secret = "k2N55LbxZouNmmIWjkVPYCEpromse9hOmGfiaXsyatJjJ";
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
		List<Status> result = Lists.newArrayList();
		for (int msgRead = 0; msgRead < maxRead; msgRead++) {
			String msg = queue.take();
			if(!Strings.isNullOrEmpty(msg)){
				System.out.println(msg);
				Status status = TwitterObjectFactory.createStatus(msg);
				tsRepo.save(status);
				result.add(status);
			}
		}

		client.stop();
		return result;
	}
}
