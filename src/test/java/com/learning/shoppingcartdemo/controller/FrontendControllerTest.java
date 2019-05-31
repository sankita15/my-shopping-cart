package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.config.JwtUtilConfig;
import com.learning.shoppingcartdemo.config.TestConfig;
import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.service.BasicService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = {TestConfig.class, JwtUtilConfig.class})
public class FrontendControllerTest {

    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";
    private static final String IMAGE_URL2 = "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png";
    private static final String MOCK_BEARER_AUTH_TOKEN = "Bearer 1234567f.gh.jklgvbhcj8s";

    @MockBean
    private BasicService basicService;

    @Autowired
    private WebTestClient webTestClient;

    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final Product product1 = Product.builder().id("1")
        .productName("Leaf Rake")
        .productCode("GDN-0011")
        .price(1995)
        .releaseDate(getParsedDate("2017-03-19T15:15:55.570Z"))
        .description("Leaf rake with 48-inch wooden handle.")
        .starRating(3.2f)
        .imageUrl(IMAGE_URL1)
        .stock(400).build();

    private static final Product product2 = Product.builder().id("2")
        .productName("Garden Cart")
        .productCode("GDN-0023")
        .price(3295)
        .releaseDate(getParsedDate("2017-03-18T08:15:55.570Z"))
        .description("15 gallon capacity rolling garden cart")
        .starRating(3.2f)
        .imageUrl(IMAGE_URL2)
        .stock(20).build();

    @SneakyThrows
    private static Date getParsedDate(String dateString) {
        return date.parse(dateString);
    }

    @Test
    public void shouldReturnListOfProduct() {
        when(basicService.getProducts()).thenReturn(Flux.just(product1, product2));

        webTestClient.get().uri("/api/products")
            .header(HttpHeaders.AUTHORIZATION, MOCK_BEARER_AUTH_TOKEN)
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

        webTestClient.get().uri("/api/products/1")
            .header(HttpHeaders.AUTHORIZATION, MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product1);

        verify(basicService, times(1)).getProductById("1");
    }

    @Test
    public void shouldUpdateProductDetails() {
        Product updatedProduct = product1.toBuilder().build();

        when(basicService.updateProductDetails(updatedProduct, "1")).thenReturn(Mono.just(updatedProduct));

        webTestClient.put().uri("/api/products/1")
            .header(HttpHeaders.AUTHORIZATION, MOCK_BEARER_AUTH_TOKEN)
            .body(Mono.just(updatedProduct), Product.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(updatedProduct);

        verify(basicService, times(1)).updateProductDetails(updatedProduct, "1");
    }

    @Test
    public void shouldAddProductDetails() {
        Product product3 = product1.toBuilder().id("3").productName("New Garden Rake").build();

        when(basicService.addProductDetails(product3)).thenReturn(Mono.just(product3));

        webTestClient.post().uri("/api/product")
            .header(HttpHeaders.AUTHORIZATION, MOCK_BEARER_AUTH_TOKEN)
            .body(Mono.just(product3), Product.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .isEqualTo(product3);

        verify(basicService, times(1)).addProductDetails(product3);
    }

    @Test
    public void shouldDeleteProductDetails() {
        when(basicService.deleteProductDetails("1")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/products/1")
            .header(HttpHeaders.AUTHORIZATION, MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class);

        verify(basicService, times(1)).deleteProductDetails("1");
    }
}
