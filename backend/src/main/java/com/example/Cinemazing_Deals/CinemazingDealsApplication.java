package com.example.Cinemazing_Deals;

import com.example.Cinemazing_Deals.security.TokenInformation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class CinemazingDealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemazingDealsApplication.class, args);
	}

	@Bean
	public Map<UUID, TokenInformation> tokens(){
		return new HashMap<>();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
