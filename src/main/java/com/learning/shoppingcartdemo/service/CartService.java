package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Flux<ShoppingCart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Mono<ShoppingCart> addCart(ShoppingCart shoppingCart){
        return cartRepository.insert(shoppingCart);
    }

    public Flux<ShoppingCart> getCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public Mono<ShoppingCart> getCartByCartId(String cartId) {
        return cartRepository.findById(cartId);
    }
}
