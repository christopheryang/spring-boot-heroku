package com.fluidjack.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestHandlerController {

	@RequestMapping("/")
	public String process(@RequestBody String request){
		
		return "OK";
	}
}
