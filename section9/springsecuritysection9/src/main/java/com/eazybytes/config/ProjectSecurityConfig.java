package com.eazybytes.config;

import com.eazybytes.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.filter.RequestValidationBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
//@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(request ->  {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			})
				.and()
					.csrf().ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and().
					addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
					.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
					.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
				.authorizeRequests()
					.antMatchers("/myAccount").hasRole("USER")
					.antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
					.antMatchers("/myLoans").hasRole("ROOT")
					.antMatchers("/myCards").hasAnyRole("USER","ADMIN")
					.antMatchers("/user").authenticated()
					.antMatchers("/notices").permitAll()
					.antMatchers("/contact").permitAll()
				.and()
					.httpBasic();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
