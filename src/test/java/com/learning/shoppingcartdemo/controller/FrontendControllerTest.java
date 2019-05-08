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
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Test
    public void shouldReturnListOfProduct() throws ParseException {

        Product product1 = new Product("1","Leaf Rake", "GDN-0011", 1995, date.parse("2017-03-19T15:15:55.570Z"),
            "Leaf rake with 48-inch wooden handle.", 3.2f,
            "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png", 400);
        Product product2 = new Product("2","Garden Cart", "GDN-0023", 3295,
            date.parse("2017-03-18T08:15:55.570Z"), "15 gallon capacity rolling garden cart", 4.2f,
            "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png",
            20);

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

    @Test
    public void shouldReturnProductById() throws ParseException {

        Product product1 = new Product("2","Garden Cart", "GDN-0023", 3295,
            date.parse("2017-03-18T08:15:55.570Z"), "15 gallon capacity rolling garden cart", 4.2f,
            "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png",
            20);

        when(basicService.getProductById("1")).thenReturn(Mono.just(product1));

        webTestClient.get().uri("/product/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product1);

        verify(basicService, times(1)).getProductById("1");
    }
}
