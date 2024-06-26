package com.bikerackapp.bike_rack_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BikeRackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeRackServiceApplication.class, args);
	}

}
