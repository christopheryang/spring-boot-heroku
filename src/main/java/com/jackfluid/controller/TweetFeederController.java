package com.jackfluid.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jackfluid.entity.TweetFeederDto;
import com.jackfluid.service.TweetFeederService;
import com.jackfluid.util.JacksonUtil;

@RestController
@RequestMapping(TweetFeederController.TWEET_FEED_API_URL)
public class TweetFeederController {
	private static final Logger logger = LoggerFactory.getLogger(TweetFeederController.class);
	
	public static final String TWEET_FEED_API_URL = "/tweet/feed";
	
	@Autowired
	protected TweetFeederService tfService;
	
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void readFeed(@RequestBody @Valid TweetFeederDto dto){
		logger.info("######## Tweet Feed DTO: "+JacksonUtil.toString(dto));
		tfService.run(dto);
	}
	
	
}
