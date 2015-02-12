package com.jackfluid.entity;

import org.springframework.data.annotation.Id;

public class BaseEntity extends JsonEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	protected String id;
	
	public String getId(){
		return id;
	}
}
