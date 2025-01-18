package com.psajd.gbuz;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GbuzApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.out.println(dotenv.get("SPRING_DATASOURCE_URL"));
		System.out.println(dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.out.println(dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		// only for local tests
		System.setProperty("spring.datasource.url", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("spring.datasource.username", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("spring.datasource.password", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		System.setProperty("server.port", dotenv.get("SERVER_PORT"));
//		System.setProperty("logging.level.root", dotenv.get("LOGGING_LEVEL"));

		SpringApplication.run(GbuzApplication.class, args);
	}

}
