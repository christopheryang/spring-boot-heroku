package com.jackfluid.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class TwitterFeedReadDto {
	
	public static final int DEFAULT_MAX_READS = 10;
	public static final int DEFAULT_MAX_TOTAL_READ_DURATION = 10; // In seconds
	
	@NotBlank(message="Query (q) is required.")
	protected String q;
	
	@Max(value=1000, message="Max number of Tweets to read is 1000")
	@Min(value=0, message="Min number of Tweets to read is 0")
	protected Integer max;
	
	@Max(value=600, message="Max Tweet feed read duration is 10 minutes")
	@Min(value=0, message="Min Tweet feed read duration is 0 second")
	protected Integer secs;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Integer getMax() {
		return max==null? DEFAULT_MAX_READS: max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getSecs() {
		return secs==null? DEFAULT_MAX_TOTAL_READ_DURATION: secs;

	}
	public void setSecs(Integer secs) {
		this.secs = secs;
	}
}
