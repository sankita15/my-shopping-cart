package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class BasicServiceTest {

    private BasicService basicService;
    private Product product1, product2, product3, product4, product5;
    private static final String IMAGE_URL1 = "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png";
    private static final String IMAGE_URL2 = "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png";
    private static final String IMAGE_URL3 = "http://openclipart.org/image/300px/svg_to_png/73/rejon_Hammer.png";
    private static final String IMAGE_URL4 = "http://openclipart.org/image/300px/svg_to_png/27070/egore911_saw.png";
    private static final String IMAGE_URL5 = "http://openclipart.org/image/300px/svg_to_png/120337/xbox-controller_01.png";

    private SimpleDateFormat date;

    @Before
    public void setUp() throws Exception {
        basicService = new BasicService();
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

        StepVerifier.create(basicService.getProducts())
            .expectNext(product1, product2, product3, product4, product5)
            .verifyComplete();
    }

    @Test
    public void shouldReturnProductById() {
        StepVerifier.create(basicService.getProductById("1"))
            .expectNext(product1)
            .verifyComplete();
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

        StepVerifier.create(basicService.updateProductDetails(updatedProduct, "1"))
            .expectNext(updatedProduct)
            .verifyComplete();

    }
}
