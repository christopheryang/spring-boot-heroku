package com.jackfluid.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetFeederDto extends JsonEntity {
	
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_MAX_READS = 10;
	public static final int DEFAULT_MAX_TOTAL_READ_DURATION = 10; // In seconds
	
	protected String searchTerm;
	
	@Max(value=20000, message="Max number of Tweets to read is 20000")
	@Min(value=0, message="Min number of Tweets to read is 0")
	protected Integer maxResults;
	
	@Max(value=600, message="Max Tweet feed read duration is 10 minutes")
	@Min(value=0, message="Min Tweet feed read duration is 0 second")
	protected Integer maxTotDuration;
	
	@NotBlank(message="sendTo URL is required.")
	protected String sendTo;
	
	// Decorative methods
	@JsonProperty("effectiveMaxResults")
	public int getEffectiveMaxResults(){
		return maxResults==null? DEFAULT_MAX_READS: maxResults;
	}
	@JsonProperty("effectiveMaxTotDuration")
	public int getEffectiveMaxTotalDuration(){
		return maxTotDuration==null? DEFAULT_MAX_TOTAL_READ_DURATION: maxTotDuration;
	}
	
	
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	public Integer getMaxTotDuration() {
		return maxTotDuration;
	}
	public void setMaxTotDuration(Integer maxTotDuration) {
		this.maxTotDuration = maxTotDuration;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
}
