package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

public class BasicServiceTest {

    private BasicService basicService;

    @Before
    public void setUp() throws Exception {
        basicService = new BasicService();
    }

    @Test
    public void shouldReturnListOfProducts(){
        StepVerifier.create(basicService.getProducts())
            .expectNext(new Product("A", 2500, "Used for cutting"),
                new Product("B", 5600, "Used for baking"),
                new Product("C", 250, "Used for cycling"),
                new Product("D", 8900, "Used for running"),
                new Product("E", 20, "Used for cooling"))
            .verifyComplete();
    }
}
