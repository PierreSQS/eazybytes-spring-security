package com.eazybytes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext appCtx){
		return args -> Arrays.stream(appCtx.getBeanDefinitionNames())
				.filter(name -> name.contains("eazy"))
				.sorted()
				.forEach(System.out::println);
	}

}
