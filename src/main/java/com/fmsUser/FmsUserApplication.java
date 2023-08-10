package com.fmsUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class FmsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmsUserApplication.class, args);
	}

}
