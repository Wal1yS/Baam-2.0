package com.example.baam2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Baam2Application {

	public static void main(String[] args) {
		SpringApplication.run(Baam2Application.class, args);
	}

}
