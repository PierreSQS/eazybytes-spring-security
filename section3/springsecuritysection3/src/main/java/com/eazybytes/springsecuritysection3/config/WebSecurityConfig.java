package com.eazybytes.springsecuritysection3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

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

/*   FOR DOCUMENTATION PURPOSES !!!!
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Admin")
                .password("{noop}Admin123")
                .authorities("ADMIN")
                .and()
                .withUser("Pierrot")
                .password("{noop}Pierrot123")
                .authorities("READ");
    }*/

/*    // ANOTHER ALTERNATIVE  !!!!
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails userDetails1 = User.withUsername("Admin")
                .password("{noop}Admin123")
                .authorities("ADMIN")
                .build();

        UserDetails userDetails2 = User.withUsername("Pierrot")
                .password("{noop}Pierrot123")
                .authorities("READ")
                .build();

        inMemoryUserDetailsManager.createUser(userDetails1);
        inMemoryUserDetailsManager.createUser(userDetails2);

        auth.userDetailsService(inMemoryUserDetailsManager);
    }*/

    // ANOTHER ALTERNATIVE With UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
