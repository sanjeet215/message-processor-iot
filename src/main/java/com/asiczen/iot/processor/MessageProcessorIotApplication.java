package com.asiczen.iot.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageProcessorIotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageProcessorIotApplication.class, args);
	}

}
