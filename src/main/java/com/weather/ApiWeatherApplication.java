package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApiWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiWeatherApplication.class, args);
	}

}
