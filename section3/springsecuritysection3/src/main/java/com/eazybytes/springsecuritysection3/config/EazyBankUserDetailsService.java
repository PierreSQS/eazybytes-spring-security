package com.eazybytes.springsecuritysection3.config;

import com.eazybytes.springsecuritysection3.model.Customer;
import com.eazybytes.springsecuritysection3.model.SecurityCustomer;
import com.eazybytes.springsecuritysection3.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EazyBankUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepo;

    public EazyBankUserDetailsService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer foundCustomerInDB = customerRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found for the user : " + username));

        return new SecurityCustomer(foundCustomerInDB);
    }
}
