package com.tarbadev.witchcraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
class WitchcraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(WitchcraftApplication.class, args);
	}
}
