package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FrontendController {

    private final BasicService basicService;

    @GetMapping(value = "/products")
    public Flux<Product> getProducts() throws ParseException {
       return basicService.getProducts();
    }

    @GetMapping(value = "/product/{id}")
    public Mono<Product> getProductById(@PathVariable String id) throws ParseException {
        return basicService.getProductById(id);
    }
}
