package com.jackfluid.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import twitter4j.Status;

public interface TweetRepo extends MongoRepository<Status, String> {

}
