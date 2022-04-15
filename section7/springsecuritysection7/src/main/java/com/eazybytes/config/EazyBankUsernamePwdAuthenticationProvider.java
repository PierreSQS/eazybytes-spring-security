package com.eazybytes.config;

import com.eazybytes.model.Authority;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	private final CustomerRepository customerRepository;
	
	private final PasswordEncoder passwordEncoder;

	public EazyBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Optional<Customer> customerOpt = customerRepository.findByEmail(username);
		if (customerOpt.isPresent()) {
			if (passwordEncoder.matches(pwd, customerOpt.get().getPwd())) {
				return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customerOpt.get().getAuthorities()));
			} else {
				throw new BadCredentialsException("Invalid password!");
			}
		}else {
			throw new BadCredentialsException("No user registered with this details!");
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.collect(Collectors.toList());
    }

	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}
}
