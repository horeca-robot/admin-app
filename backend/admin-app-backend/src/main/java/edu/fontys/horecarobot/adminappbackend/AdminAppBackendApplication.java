package edu.fontys.horecarobot.adminappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
}
