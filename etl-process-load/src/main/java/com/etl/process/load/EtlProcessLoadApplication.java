package com.etl.process.load;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class EtlProcessLoadApplication {

	/**
	 *
	 * @param args startup arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EtlProcessLoadApplication.class, args);
	}

}
