package com.oracle.comventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComventoryApplication.class, args);
    }
}