package com.jackfluid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.jackfluid")
@EnableMongoRepositories("com.jackfluid.repo.mongo")
public class AppContext extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 

	protected String getDatabaseName() {
	    return "heroku_app33853397";
	}

	public Mongo mongo() throws Exception {
	    return new MongoClient("ds031551.mongolab.com", 31551);
	}

	@Bean
	public SimpleMongoDbFactory mongoDbFactory() throws Exception {
	    return new SimpleMongoDbFactory(mongo(), getDatabaseName());
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	    final UserCredentials userCredentials = new UserCredentials("dbowner", "asdf1qaz123");

	    final MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName(), userCredentials);
	    mongoTemplate.setWriteConcern(WriteConcern.SAFE);

	    return mongoTemplate;
	}
	

}
