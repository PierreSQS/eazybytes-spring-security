package com.eazybytes.springsecuritysection6.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.springsecuritysection6.model.Accounts;

import java.util.Optional;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	
	Optional<Accounts> findByCustomerId(int customerId);

}
