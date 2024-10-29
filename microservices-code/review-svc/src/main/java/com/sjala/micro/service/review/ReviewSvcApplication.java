package com.sjala.micro.service.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReviewSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewSvcApplication.class, args);
	}

}
