package com.jackfluid.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	protected static ObjectMapper mapper = new ObjectMapper();
	
	public static String toString(Object obj){
		try{
			return mapper.writeValueAsString(obj);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
