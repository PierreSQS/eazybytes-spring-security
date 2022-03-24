package com.eazybytes.springsecuritysection2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * /myAccount - secured
     * /myBalance - secured
     * /myLoans - secured
     * /notices - not secured
     * /contact - not secured
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/contact", "/notices").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
            .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Admin")
                .password("Admin123")
                .authorities("ADMIN")
             .and()
                .withUser("Pierrot")
                .password("Pierrot123")
                .authorities("READ");
    }
}
