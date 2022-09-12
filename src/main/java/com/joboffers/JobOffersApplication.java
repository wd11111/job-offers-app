package com.joboffers;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class JobOffersApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobOffersApplication.class, args);
	}

}
