package com.freelance.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Freelance Job Portal application.
 * This class contains the main method which is used to run the Spring Boot application.
 */
@SpringBootApplication
public class Application {
    /**
     * The main method that starts the Spring Boot application.
     * @param args Command line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
