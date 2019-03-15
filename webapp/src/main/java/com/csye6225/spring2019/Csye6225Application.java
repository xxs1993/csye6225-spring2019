package com.csye6225.spring2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication

public class Csye6225Application {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Csye6225Application.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(Csye6225Application.class, args);
	}

}

