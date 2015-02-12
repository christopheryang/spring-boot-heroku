package com.jackfluid.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import com.jackfluid.entity.Tweet;
import com.jackfluid.repo.TweetRepo;
import com.jackfluid.util.JacksonUtil;

@RestController
@RequestMapping(TweetController.TWEET_API_URL)
public class TweetController {

	private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

	public static final String TWEET_API_URL = "/tweet";

	@Autowired
	protected TweetRepo tweetRepo;

	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void processTweet(@RequestBody String msg){
		try{
			Status status = TwitterObjectFactory.createStatus(msg);
			logger.info("######## Received Tweet: "+JacksonUtil.toString(new Tweet(status)));
			tweetRepo.save(status);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
