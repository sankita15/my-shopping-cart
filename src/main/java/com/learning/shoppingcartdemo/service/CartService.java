package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Flux<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Mono<ShoppingCart> addCart(ShoppingCart shoppingCart) {
        return cartRepository.insert(shoppingCart);
    }

    public Flux<ShoppingCart> getCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public Mono<ShoppingCart> getCartByCartId(String cartId) {
        return cartRepository.findById(cartId);
    }

    public Mono<ShoppingCart> placeOrder(String cartId) {
        return getCartByCartId(cartId)
            .map(cart -> cart.toBuilder().cartStatus(ShoppingCart.ORDERED).build())
            .flatMap(cartRepository::save);
    }

    public Mono<Void> deleteCart(String cartId) {
        return cartRepository.deleteById(cartId);
    }

    public Mono<ShoppingCart> addProduct(String cartId, Product product) {
        return getCartByCartId(cartId)
            .map(cart -> cart.toBuilder()
                .products(getProducts(cart, product))
                .productQuantities(getProductQuantity(product.getId(), cart.getProductQuantities()))
                .cartPrice(cart.getCartPrice() + product.getPrice())
                .build())
            .flatMap(cartRepository::save);
    }

    private HashMap<String, Product> getProducts(ShoppingCart cart, Product product) {
        HashMap<String, Product> products = new HashMap<>(cart.getProducts());
        products.put(product.getId(), product);
        return products;
    }

    private HashMap<String, Integer> getProductQuantity(String id, HashMap<String, Integer> quantities) {
        HashMap<String, Integer> productQuantities = new HashMap<>(quantities);

        productQuantities.put(id, productQuantities.containsKey(id) ? productQuantities.get(id) + 1 : 1);

        return productQuantities;
    }
}
