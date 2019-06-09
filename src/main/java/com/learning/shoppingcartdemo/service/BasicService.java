package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BasicService {

    private final ProductRepository productRepository;

    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Mono<Product> updateProductDetails(Product product, String id) {
        return productRepository.save(product);
    }

    public Mono<Product> addProductDetails(Product product) {
        return productRepository.insert(product);
    }

    public Mono<Void> deleteProductDetails(String id) {
        return productRepository.deleteById(id);
    }
}
