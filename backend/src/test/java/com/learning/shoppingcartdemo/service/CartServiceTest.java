package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.model.ShoppingCart;
import com.learning.shoppingcartdemo.repository.CartRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    private CartService cartService;

    @Before
    public void setUp() {
        cartService = new CartService(cartRepository);
    }

    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";

    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final Date orderDate = new Date();

    private static final Product PRODUCT_1 = Product.builder().id("1")
        .productName("Leaf Rake")
        .productCode("GDN-0011")
        .price(100)
        .releaseDate(getParsedDate("2017-03-19T15:15:55.570Z"))
        .description("Leaf rake with 48-inch wooden handle.")
        .starRating(3.2f)
        .imageUrl(IMAGE_URL1)
        .stock(400).build();

    private static final Product PRODUCT_2 = Product.builder().id("2")
        .productName("New Leaf Rake")
        .productCode("GDN-0431")
        .price(200)
        .releaseDate(getParsedDate("2017-03-19T15:15:55.570Z"))
        .description("Leaf rake with 48-inch wooden handle.")
        .starRating(4.2f)
        .imageUrl(IMAGE_URL1)
        .stock(100).build();

    @SneakyThrows
    private static Date getParsedDate(String dateString) {
        return date.parse(dateString);
    }

    private static final ShoppingCart CART1 = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(orderDate)
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts(PRODUCT_1))
        .productQuantities(getProductQuantities(new SimpleEntry<>("1", 5)))
        .cartPrice(500)
        .build();

    private static final ShoppingCart CART_WITH_PRODUCTS = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(orderDate)
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts(PRODUCT_1, PRODUCT_2))
        .productQuantities(getProductQuantities(new SimpleEntry<>("1", 5), new SimpleEntry<>("2", 1)))
        .cartPrice(700)
        .build();

    private static final ShoppingCart ORDERED_CART = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(orderDate)
        .cartStatus(ShoppingCart.ORDERED)
        .products(getProducts(PRODUCT_1))
        .productQuantities(getProductQuantities(new SimpleEntry<>("1", 5)))
        .cartPrice(500)
        .build();

    private static HashMap<String, Product> getProducts(Product... products) {
        return Arrays.stream(products)
            .collect(Collectors.toMap(Product::getId, product -> product, (a, b) -> b, HashMap::new));
    }

    private static HashMap<String, Integer> getProductQuantities(SimpleEntry<String, Integer>... entries) {
        return Arrays.stream(entries).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue, (a, b) -> b, HashMap::new));
    }

    private static final ShoppingCart CART_WITH_ADDED_PRODUCT = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(orderDate)
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts(PRODUCT_1, PRODUCT_2))
        .productQuantities(getProductQuantities(new SimpleEntry<>("1", 5), new SimpleEntry<>("2", 1)))
        .cartPrice(700)
        .build();

    private static final ShoppingCart CART_WITH_REMOVED_PRODUCT = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(orderDate)
        .cartStatus(ShoppingCart.PENDING)
        .products(getProducts(PRODUCT_1))
        .productQuantities(getProductQuantities(new SimpleEntry<>("1", 5)))
        .cartPrice(500)
        .build();

    @Test
    public void shouldReturnListOfAllTheCart() {
        when(cartRepository.findAll()).thenReturn(Flux.just(CART1));

        StepVerifier.create(cartService.getAllCarts())
            .expectNext(CART1)
            .verifyComplete();

        verify(cartRepository, times(1)).findAll();
    }

    @Test
    public void shouldAddCart() {
        when(cartRepository.insert(CART1)).thenReturn(Mono.just(CART1));

        StepVerifier.create(cartService.addCart(CART1))
            .expectNext(CART1)
            .verifyComplete();

        verify(cartRepository, times(1)).insert(CART1);
    }

    @Test
    public void shouldReturnCartByCartId() {
        when(cartRepository.findById("1")).thenReturn(Mono.just(CART1));

        StepVerifier.create(cartService.getCartByCartId("1"))
            .expectNext(CART1)
            .verifyComplete();

        verify(cartRepository, times(1)).findById("1");
    }

    @Test
    public void shouldReturnCartByUsername() {
        when(cartRepository.findByUsername("user")).thenReturn(Flux.just(CART1));

        StepVerifier.create(cartService.getCartByUsername("user"))
            .expectNext(CART1)
            .verifyComplete();

        verify(cartRepository, times(1)).findByUsername("user");
    }

    @Test
    public void shouldPlaceOrderForCart() {
        when(cartRepository.findById("1")).thenReturn(Mono.just(CART1));
        when(cartRepository.save(ORDERED_CART)).thenReturn(Mono.just(ORDERED_CART));

        StepVerifier.create(cartService.placeOrder("1"))
            .expectNext(ORDERED_CART)
            .verifyComplete();

        verify(cartRepository, times(1)).save(ORDERED_CART);
    }

    @Test
    public void shouldAddProductInCart() {
        when(cartRepository.findById("1")).thenReturn(Mono.just(CART1));
        when(cartRepository.save(any())).thenReturn(Mono.just(CART_WITH_ADDED_PRODUCT));

        StepVerifier.create(cartService.addProduct("1", PRODUCT_2))
            .expectNext(CART_WITH_ADDED_PRODUCT)
            .verifyComplete();

        verify(cartRepository, times(1)).save(CART_WITH_ADDED_PRODUCT);
    }

    @Test
    public void shouldDeleteCart() {
        when(cartRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(cartService.deleteCart("1"))
            .expectComplete();

        verify(cartRepository, times(1)).deleteById("1");
    }

    @Test
    public void shouldDeleteProductFromCart() {
        when(cartRepository.findById("1")).thenReturn(Mono.just(CART_WITH_PRODUCTS));
        when(cartRepository.save(any())).thenReturn(Mono.just(CART_WITH_REMOVED_PRODUCT));

        StepVerifier.create(cartService.removeProduct("1", "2"))
            .expectNext(CART_WITH_REMOVED_PRODUCT)
            .verifyComplete();

        verify(cartRepository, times(1)).save(CART_WITH_REMOVED_PRODUCT);
    }
}
