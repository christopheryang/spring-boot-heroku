package com.jackfluid.entity;

public class User {
	
	protected Long id;
	protected String name;
	protected String screenName;

	public User(twitter4j.User user){
		this.id = user.getId();
		this.name = user.getName();
		this.screenName = user.getScreenName();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

}
