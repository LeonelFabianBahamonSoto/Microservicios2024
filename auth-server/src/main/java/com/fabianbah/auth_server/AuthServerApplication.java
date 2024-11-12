package com.fabianbah.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication /*implements CommandLineRunner*/  {

	// @Autowired
	// PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	// public void run(String... args) throws Exception {
	// 	System.out.println("USER: " + this.passwordEncoder.encode("super"));
	// 	System.out.println("CLIENT: " + this.passwordEncoder.encode("secret"));
	// }

}
