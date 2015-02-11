package com.jackfluid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Status;

import com.jackfluid.dto.TwitterFeedReadDto;
import com.jackfluid.service.TwitterFeedReader;

@RestController
@RequestMapping(TwitterFeedController.TWITTER_FEED_API_URL)
public class TwitterFeedController {
	
	public static final String TWITTER_FEED_API_URL = "/twitter/feed";
	
	@Autowired
	protected TwitterFeedReader tfReader;
	
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Status> readFeed(@ModelAttribute @Valid TwitterFeedReadDto dto){
		
		return tfReader.read(dto);
		
	}
	
}
