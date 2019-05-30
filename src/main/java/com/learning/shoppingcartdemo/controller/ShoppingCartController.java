package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CartService cartService;

    @GetMapping
    public Flux<ShoppingCart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @PostMapping(value = "/add")
    public Mono<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart){
        return cartService.addCart(shoppingCart);
    }

    @GetMapping(value = "/user/{username}")
    public Flux<ShoppingCart> getCartByUsername(@PathVariable String username){
        return cartService.getCartByUsername(username);
    }

    @GetMapping(value = "/{cartId}")
    public Mono<ShoppingCart> getCartByCartId(@PathVariable String cartId){
        return cartService.getCartByCartId(cartId);
    }
}
