package com.github.kuniwakejulio.mscartoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsCartoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCartoesApplication.class, args);
	}

}