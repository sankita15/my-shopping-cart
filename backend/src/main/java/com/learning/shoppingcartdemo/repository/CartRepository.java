package com.learning.shoppingcartdemo.repository;

import com.learning.shoppingcartdemo.model.ShoppingCart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends ReactiveMongoRepository<ShoppingCart, String> {
    Flux<ShoppingCart> findAll();
    Flux<ShoppingCart> findByUsername(String s);
}
