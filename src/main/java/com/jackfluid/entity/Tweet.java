package com.jackfluid.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import twitter4j.Status;

@Document(collection="Tweets")
public class Tweet extends JsonEntity {
	
	private static final long serialVersionUID = 1L;
	
	protected Date createdAt;
	protected Long id;
	protected String text;
	protected String source;
	protected String lang;
	protected User user;
	
	public Tweet(Status status){
		this.createdAt = status.getCreatedAt();
		this.id = status.getId();
		this.text = status.getText();
		this.source = status.getSource();
		this.lang = status.getLang();
		this.user = new User(status.getUser());
	}
	
}
