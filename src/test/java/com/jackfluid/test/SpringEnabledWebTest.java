package com.jackfluid.test;

import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.CharStreams;
import com.jackfluid.app.Application;
import com.jackfluid.controller.TweetFeederController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"}) // Random port
@Ignore 
public class SpringEnabledWebTest {

	@Value("${local.server.port}") // Actual port
	protected int port;

	protected URL base;
	protected RestTemplate template;
	
	protected String tweetFeederUrl;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port);
		tweetFeederUrl = base.toExternalForm()+TweetFeederController.TWEET_FEED_API_URL;
		template = new TestRestTemplate();
	}
	
	/**
	 * 
	 * @param fileResource
	 * @return
	 */
	protected String fileResourceToString(Resource fileResource){
		try{
			return CharStreams.toString(new InputStreamReader(fileResource.getInputStream()));
		}
		catch(Exception ex){
			// Cannot recover, throw a RuntimException
			throw new RuntimeException(ex);
		}
	}
}
