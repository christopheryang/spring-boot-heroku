package com.jackfluid.repo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import twitter4j.Status;

public interface TwitterStatusRepo extends MongoRepository<Status, String> {

}
