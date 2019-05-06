package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicService {
    public Flux<Product> getProducts() {
        Product product1 = new Product("A", 2500, "Used for cutting");
        Product product2 = new Product("B", 5600, "Used for baking");
        Product product3 = new Product("C", 250, "Used for cycling");
        Product product4 = new Product("D", 8900, "Used for running");
        Product product5 = new Product("E", 20, "Used for cooling");

        return Flux.just(product1, product2, product3, product4, product5);
    }
}
