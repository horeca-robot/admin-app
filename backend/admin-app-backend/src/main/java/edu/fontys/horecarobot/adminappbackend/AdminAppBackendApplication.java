package edu.fontys.horecarobot.adminappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("edu.fontys.horecarobot.databaselibrary")
@EnableJpaRepositories("edu.fontys.horecarobot.databaselibrary")
public class AdminAppBackendApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(AdminAppBackendApplication.class, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:4000").allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
