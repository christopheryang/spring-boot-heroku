package com.jackfluid.dto;

import javax.validation.constraints.NotNull;

public class TwitterFeedReadDto {
	
	@NotNull(message="Query (q) is required.")
	protected String q;
	
	protected Integer max;
	protected Integer secs;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getDuration() {
		return secs;
	}
	public void setDuration(Integer duration) {
		this.secs = duration;
	}
}
