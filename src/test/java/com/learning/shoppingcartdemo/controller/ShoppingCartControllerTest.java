package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.config.JwtUtilConfig;
import com.learning.shoppingcartdemo.config.TestConfig;
import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.service.CartService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = {TestConfig.class, JwtUtilConfig.class})
public class ShoppingCartControllerTest {

    @MockBean
    CartService cartService;

    @Autowired
    private WebTestClient webTestClient;

    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";
    private static final String MOCK_BEARER_AUTH_TOKEN = "Bearer 1234567f.gh.jklgvbhcj8s";

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

    @SneakyThrows
    private static Date getParsedDate(String dateString) {
        return date.parse(dateString);
    }

    private static final ShoppingCart CART1 = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(new Date())
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts("1", product1))
        .productQuantities(getProductQuantity("1", 5))
        .cartPrice(400)
        .build();

    private static final ShoppingCart CART2 = ShoppingCart.builder()
        .id("2")
        .username("alice")
        .orderDate(new Date())
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts("2", product1))
        .productQuantities(getProductQuantity("2", 2))
        .cartPrice(400)
        .build();

    private static final ShoppingCart ORDERED_CART = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(new Date())
        .cartStatus(ShoppingCart.ORDERED)
        .products(getProducts("1", product1))
        .productQuantities(getProductQuantity("1", 5))
        .cartPrice(400)
        .build();

    private static final ShoppingCart CART_WITH_ADDED_PRODUCT = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(new Date())
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts("1", product1))
        .productQuantities(getProductQuantity("1", 5))
        .cartPrice(400)
        .build();

    private static HashMap<String, Integer> getProductQuantity(String id, Integer quantity) {
        HashMap productQuantities = new HashMap<>();
        productQuantities.put(id, quantity);
        return productQuantities;
    }

    private static HashMap<String, Product> getProducts(String id , Product product1) {
        HashMap<String, Product> product = new HashMap<>();
        product.put(id, product1);
        return product;
    }

    @Test
    public void shouldReturnAllTheCarts() {

        when(cartService.getAllCarts()).thenReturn(Flux.just(CART1, CART2));

        webTestClient.get().uri("/api/carts")
            .header("Authorization", MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(ShoppingCart.class)
            .hasSize(2)
            .contains(CART1, CART2);

        verify(cartService, times(1)).getAllCarts();
    }

    @Test
    public void shouldAddCart() {
        when(cartService.addCart(CART1)).thenReturn(Mono.just(CART1));

        webTestClient.post().uri("/api/carts/add")
            .body(Mono.just(CART1), ShoppingCart.class)
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ShoppingCart.class)
            .isEqualTo(CART1);

        verify(cartService, times(1)).addCart(CART1);
    }

    @Test
    public void shouldReturnCartByCartID() {
        when(cartService.getCartByCartId("1")).thenReturn(Mono.just(CART1));

        webTestClient.get().uri("/api/carts/1")
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ShoppingCart.class)
            .isEqualTo(CART1);

        verify(cartService, times(1)).getCartByCartId("1");
    }

    @Test
    public void shouldReturnCartByUsername() {
        when(cartService.getCartByUsername("alice")).thenReturn(Flux.just(CART2));

        webTestClient.get().uri("/api/carts/user/alice")
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(ShoppingCart.class)
            .hasSize(1)
            .contains(CART2);

        verify(cartService, times(1)).getCartByUsername("alice");
    }

    @Test
    public void shouldPlaceOrder() {
        when(cartService.placeOrder("1")).thenReturn(Mono.just(ORDERED_CART));

        webTestClient.put().uri("/api/carts/1/order")
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ShoppingCart.class)
            .isEqualTo(ORDERED_CART);

        verify(cartService, times(1)).placeOrder("1");
    }

    @Test
    public void shouldAddProductinCart() {
        when(cartService.addProduct("1", product1)).thenReturn(Mono.just(CART_WITH_ADDED_PRODUCT));

        webTestClient.put().uri("/api/carts/1/product/1")
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .body(Mono.just(product1), Product.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ShoppingCart.class)
            .isEqualTo(CART_WITH_ADDED_PRODUCT);

        verify(cartService, times(1)).addProduct("1", product1);
    }

    @Test
    public void shouldDeleteCart() {
        when(cartService.deleteCart("1")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/carts/1")
            .header("AUTHORIZATION", MOCK_BEARER_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(ShoppingCart.class)
            .hasSize(0)
            .doesNotContain(CART1);

        verify(cartService, times(1)).deleteCart("1");
    }
}
