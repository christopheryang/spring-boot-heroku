package com.jackfluid.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping("/loaderio-f4b4f879a17005b5bcd9a0f929da6e94/")
	public String loadIOVerification(){
		return "loaderio-f4b4f879a17005b5bcd9a0f929da6e94";
	}

}
