package com.example.qrgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Application to generate QR Codes
 */
@SpringBootApplication
public class QrgeneratorApplication {

	/**
	 * Main method, which starts the application on local server.
	 * @param args Main method arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(QrgeneratorApplication.class, args);
	}

}
