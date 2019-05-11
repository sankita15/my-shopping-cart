package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.service.BasicService;
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
@RequestMapping
@RequiredArgsConstructor
public class FrontendController {

    private final BasicService basicService;

    @GetMapping(value = "/products")
    public Flux<Product> getProducts() {
       return basicService.getProducts();
    }

    @GetMapping(value = "/products/{id}")
    public Mono<Product> getProductById(@PathVariable String id) {
        return basicService.getProductById(id);
    }

    @PutMapping(value = "/products/{id}")
    public Mono<Product> updateProductDetails(@RequestBody Product product, @PathVariable String id) {
        return basicService.updateProductDetails(product, id);
    }

    @PostMapping(value = "/product")
    public Mono<Product> addProduct(@RequestBody Product product){
        return basicService.addProductDetails(product);
    }

    @DeleteMapping(value = "/products/{id}")
    public Mono<Product> deleteProduct(@PathVariable String id){
         return basicService.deleteProductDetails(id);
    }
}
