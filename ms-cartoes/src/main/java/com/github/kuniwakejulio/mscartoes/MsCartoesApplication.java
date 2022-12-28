package com.github.kuniwakejulio.mscartoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class MsCartoesApplication {

	public static void main(String[] args) {
		// Exemplo de tipos de logs mais usado
		log.info("Informação : {}", "Teste Info");
		log.error("Erro : {}", "Teste Erro");
		log.warn("Aviso : {}", "Teste Aviso");
		SpringApplication.run(MsCartoesApplication.class, args);
	}

}
