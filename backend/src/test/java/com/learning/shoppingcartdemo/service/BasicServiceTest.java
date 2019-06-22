package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import com.learning.shoppingcartdemo.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
public class BasicServiceTest {

    private BasicService basicService;

    @Mock
    private ProductRepository productRepository;

    private Product product1, product2, product3, product4, product5;
    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";
    private static final String IMAGE_URL2 = "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png";
    private static final String IMAGE_URL3 = "http://openclipart.org/image/300px/svg_to_png/73/rejon_Hammer.png";
    private static final String IMAGE_URL4 = "http://openclipart.org/image/300px/svg_to_png/27070/egore911_saw.png";
    private static final String IMAGE_URL5 = "http://openclipart.org/image/300px/svg_to_png/120337/xbox-controller_01.png";

    private SimpleDateFormat date;

    @Before
    public void setUp() throws Exception {
        basicService = new BasicService(productRepository);
        date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

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

        product3 = Product.builder().id("3")
            .productName("Hammer")
            .productCode("TBX-0048")
            .price(890)
            .releaseDate(date.parse("2017-05-21T12:15:55.570Z"))
            .description("Curved claw steel hammer")
            .starRating(4.8f)
            .imageUrl(IMAGE_URL3)
            .stock(400).build();

        product4 = Product.builder().id("4")
            .productName("Saw")
            .productCode("TBX-0022")
            .price(1195)
            .releaseDate(date.parse("2017-05-15T12:15:55.570Z"))
            .description("15-inch steel blade hand saw")
            .starRating(3.7f)
            .imageUrl(IMAGE_URL4)
            .stock(1200).build();

        product5 = Product.builder().id("5")
            .productName("Video Game Controller")
            .productCode("GMG-0042")
            .price(3595)
            .releaseDate(date.parse("2017-10-15T14:15:55.570Z"))
            .description("15-inch steel blade hand saw")
            .starRating(4.6f)
            .imageUrl(IMAGE_URL5)
            .stock(300).build();
    }

    @Test
    public void shouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(Flux.just(product1,product2, product3, product4, product5));

        StepVerifier.create(basicService.getProducts())
            .expectNext(product1, product2, product3, product4, product5)
            .verifyComplete();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnProductById() {
        when(productRepository.findById("1")).thenReturn(Mono.just(product1));

        StepVerifier.create(basicService.getProductById("1"))
            .expectNext(product1)
            .verifyComplete();

        verify(productRepository, times(1)).findById("1");
    }

    @Test
    public void shouldUpdateTheProduct() throws ParseException {

        Product updatedProduct = Product.builder().id("1")
            .productName("New Leaf Rake")
            .productCode("GDN-0011")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL1)
            .stock(400).build();

        when(productRepository.save(any())).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(basicService.updateProductDetails(updatedProduct, "1"))
            .expectNext(updatedProduct)
            .verifyComplete();

        verify(productRepository, times(1)).save(updatedProduct);

    }

    @Test
    public void shouldAddProductDetails() throws ParseException {
        Product product6 = Product.builder().id("6")
            .productName("New Garden Rake")
            .productCode("GDN-00189")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl(IMAGE_URL1)
            .stock(400).build();

        when(productRepository.insert(product6)).thenReturn(Mono.just(product6));

        StepVerifier.create(basicService.addProductDetails(product6))
            .expectNext(product6)
            .verifyComplete();

        verify(productRepository, times(1)).insert(product6);
    }

    @Test
    public void shouldDeleteProductDetails(){
        when(productRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(basicService.deleteProductDetails("1")).verifyComplete();

        verify(productRepository, times(1)).deleteById("1");
    }
}
