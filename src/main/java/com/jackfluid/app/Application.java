package com.jackfluid.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.jackfluid.configuration.AppContext;
import com.jackfluid.repo.mongo.CustomerRepo;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Import(AppContext.class)
public class Application {
    
	@Autowired
	private CustomerRepo repository;

	public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println(ctx==null);
    }
    
}
