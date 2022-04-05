package com.eazybytes.springsecuritysection6.config;

import com.eazybytes.springsecuritysection6.model.Customer;
import com.eazybytes.springsecuritysection6.model.SecurityCustomer;
import com.eazybytes.springsecuritysection6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EazyBankUserDetails implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User details not found for the user : " + username));

		return new SecurityCustomer(customer);
	}

}
