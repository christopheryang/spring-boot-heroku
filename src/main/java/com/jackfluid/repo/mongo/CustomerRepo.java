package com.jackfluid.repo.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jackfluid.entity.Customer;

public interface CustomerRepo extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}
