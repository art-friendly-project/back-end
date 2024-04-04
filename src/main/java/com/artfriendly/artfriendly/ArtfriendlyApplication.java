package com.artfriendly.artfriendly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArtfriendlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtfriendlyApplication.class, args);
	}

}
