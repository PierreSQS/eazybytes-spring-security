package com.eazybytes.springsecuritysection3.repository;

import com.eazybytes.springsecuritysection3.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    Optional<Customer> findByEmail(String email);
}
