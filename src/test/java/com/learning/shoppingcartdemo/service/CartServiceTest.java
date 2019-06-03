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
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    private CartService cartService;

    @Before
    public void setUp() throws Exception {
        cartService = new CartService(cartRepository);
    }

    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";

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

    private static final ShoppingCart ORDERED_CART = ShoppingCart.builder()
        .id("1")
        .username("user")
        .orderDate(new Date())
        .cartStatus(ShoppingCart.ORDERED)
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
}
