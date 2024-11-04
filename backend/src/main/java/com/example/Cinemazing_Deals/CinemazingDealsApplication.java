package com.example.Cinemazing_Deals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemazingDealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemazingDealsApplication.class, args);
	}

}
