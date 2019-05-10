package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.service.BasicService;
import org.junit.Before;
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

    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";
    private static final String IMAGE_URL2 = "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png";

    @MockBean
    private BasicService basicService;

    @Autowired
    private WebTestClient webTestClient;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private Product product1, product2;

    @Before
    public void setUp() throws Exception {
        product1 = Product.builder().id("1")
            .productName("Leaf Rake")
            .productCode("GDN-0011")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL1)
            .stock(400).build();

        product2 = Product.builder().id("2")
            .productName("Garden Cart")
            .productCode("GDN-0023")
            .price(3295)
            .releaseDate(date.parse("2017-03-18T08:15:55.570Z"))
            .description("15 gallon capacity rolling garden cart")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL2)
            .stock(20).build();
    }

    @Test
    public void shouldReturnListOfProduct() {

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
    public void shouldReturnProductById() {

        when(basicService.getProductById("1")).thenReturn(Mono.just(product1));

        webTestClient.get().uri("/product/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product1);

        verify(basicService, times(1)).getProductById("1");
    }

    @Test
    public void shoudldUpdateProductDetails() throws ParseException {

        Product updatedProduct = Product.builder().id("1")
            .productName("New Leaf Rake")
            .productCode("GDN-0011")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL1)
            .stock(400).build();

        when(basicService.updateProductDetails(updatedProduct, "1")).thenReturn(Mono.just(updatedProduct));

        webTestClient.put().uri("/product/1")
            .body(Mono.just(updatedProduct), Product.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(updatedProduct);

        verify(basicService, times(1)).updateProductDetails(updatedProduct, "1");
    }

    @Test
    public void shouldAddProductDetails() throws ParseException {

        Product product3 = Product.builder().id("3")
            .productName("New Garden Rake")
            .productCode("GDN-00189")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL1)
            .stock(400).build();

        when(basicService.addProductDetails(product3)).thenReturn(Mono.just(product3));

        webTestClient.post().uri("/product")
            .body(Mono.just(product3), Product.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product3);

        verify(basicService, times(1)).addProductDetails(product3);
    }
}
