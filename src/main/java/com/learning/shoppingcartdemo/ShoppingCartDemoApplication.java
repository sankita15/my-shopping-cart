package com.learning.shoppingcartdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
public class ShoppingCartDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartDemoApplication.class, args);
    }
}
