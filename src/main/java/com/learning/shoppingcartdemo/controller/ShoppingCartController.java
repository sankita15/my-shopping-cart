package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Flux<ShoppingCart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping(value = "/add")
    public Mono<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart) {
        return cartService.addCart(shoppingCart);
    }

    @GetMapping(value = "/user/{username}")
    public Flux<ShoppingCart> getCartByUsername(@PathVariable String username) {
        return cartService.getCartByUsername(username);
    }

    @GetMapping(value = "/{cartId}")
    public Mono<ShoppingCart> getCartByCartId(@PathVariable String cartId) {
        return cartService.getCartByCartId(cartId);
    }

    @PutMapping(value = "{cartId}/order")
    public Mono<ShoppingCart> placeOrder(@PathVariable String cartId) {
        return cartService.placeOrder(cartId);
    }

    @PutMapping(value = "/{cartId}/product/{productId}")
    public Mono<ShoppingCart> addProduct(@PathVariable String cartId,
                                         @PathVariable String productId,
                                         @RequestBody Product product) {
        return cartService.addProduct(cartId, product);
    }

    @DeleteMapping(value = "/{cartId}")
    public Mono<Void> deleteCart(@PathVariable String cartId) {
        return cartService.deleteCart(cartId);
    }

    @DeleteMapping(value = "/{cartId}/product/{productId}")
    public Mono<ShoppingCart> removeProduct(@PathVariable String cartId,
                                            @PathVariable String productId) {
        return cartService.removeProduct(cartId, productId);
    }
}
