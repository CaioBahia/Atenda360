package br.com.atenda360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude=UserDetailsServiceAutoConfiguration.class)
public class Atenda360ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Atenda360ApiApplication.class, args);
	}

}
