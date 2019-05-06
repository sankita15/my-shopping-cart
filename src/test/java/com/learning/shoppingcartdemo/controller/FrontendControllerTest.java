package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.service.BasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(FrontendController.class)
public class FrontendControllerTest {

    @MockBean
    private BasicService basicService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnListOfProduct(){

        Product product1 = new Product("XYZ", 2500, "Used for smoking");
        Product product2 = new Product("ABC", 5600, "used for hitting");

        when(basicService.getProducts()).thenReturn(Flux.just(product1, product2));

        webTestClient.get().uri("/products")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .hasSize(2)
            .contains(product1, product2);

        verify(basicService, times(1)).getProducts();

    }
}
