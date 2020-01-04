package com.stahocorp.etlprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@ComponentScan("com.stahocorp")
@EnableScheduling
@SpringBootApplication
public class EtlProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtlProcessApplication.class, args);
	}

}
