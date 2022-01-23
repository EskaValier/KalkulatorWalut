package pl.gdynia.amw.lab6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Lab6Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab6Application.class, args);
	}
}
