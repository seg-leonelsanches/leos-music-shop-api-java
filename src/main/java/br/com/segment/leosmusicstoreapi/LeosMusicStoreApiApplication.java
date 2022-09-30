package br.com.segment.leosmusicstoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.segment.leosmusicstoreapi.repositories")
public class LeosMusicStoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeosMusicStoreApiApplication.class, args);
	}

}
