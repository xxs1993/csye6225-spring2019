package com.csye6225.spring2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableAutoConfiguration
@SpringBootApplication
public class Csye6225Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Csye6225Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Csye6225Application.class);
	}

}

