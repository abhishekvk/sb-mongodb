/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }
}
