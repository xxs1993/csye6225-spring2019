package com.csye6225.spring2019;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.csye6225.spring2019.repository")
public class Csye6225Application {

	public static void main(String[] args) {
		SpringApplication.run(Csye6225Application.class, args);
	}

}

