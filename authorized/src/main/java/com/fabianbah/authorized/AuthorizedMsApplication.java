package com.fabianbah.authorized;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthorizedMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizedMsApplication.class, args);
	}

}
