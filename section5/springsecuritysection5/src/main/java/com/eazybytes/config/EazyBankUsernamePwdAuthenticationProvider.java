package com.eazybytes.config;

import com.eazybytes.model.Customer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eazybytes.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	private final CustomerRepository customerRepository;

	private final PasswordEncoder passwordEncoder;

	public EazyBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// the Authentication Object to Render
		UsernamePasswordAuthenticationToken usernamePwdAuth;
		// get Login User Credentials
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();

		// get User Credentials from DB
		Optional<Customer> customerFromDBOpt = customerRepository.findByEmail(username);

		// validate Login User Credentials or not by comparing with Credentials from DB
		if (customerFromDBOpt.isPresent()) {
			Customer customer = customerFromDBOpt.get();
			if (passwordEncoder.matches(pwd,customer.getPwd())) {
				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
				usernamePwdAuth = new UsernamePasswordAuthenticationToken(username,pwd, authorities);
			} else {
				throw new BadCredentialsException("Invalid Password!!!!");
			}

		} else {
			throw new BadCredentialsException("No user registered with this details!: "+username);
		}

		// return the Authentication Token
		return usernamePwdAuth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
