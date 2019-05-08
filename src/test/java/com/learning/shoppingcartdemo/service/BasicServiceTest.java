package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BasicServiceTest {

    private BasicService basicService;
    private SimpleDateFormat date;

    @Before
    public void setUp() throws Exception {
        basicService = new BasicService();
        date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @Test
    public void shouldReturnListOfProducts() throws ParseException {

        StepVerifier.create(basicService.getProducts())
            .expectNext(new Product("1","Leaf Rake", "GDN-0011", 1995, date.parse("2017-03-19T15:15:55.570Z"),
                    "Leaf rake with 48-inch wooden handle.", 3.2f,
                    "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png", 400),
                new Product("2","Garden Cart", "GDN-0023", 3295,
                    date.parse("2017-03-18T08:15:55.570Z"), "15 gallon capacity rolling garden cart", 4.2f,
                    "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png",
                    20),
                new Product("3","Hammer", "TBX-0048", 890, date.parse("2017-05-21T12:15:55.570Z"),
                    "Curved claw steel hammer",
                    4.8f,
                    "http://openclipart.org/image/300px/svg_to_png/73/rejon_Hammer.png",
                    400),
                new Product("4","Saw", "TBX-0022", 1155, date.parse("2017-05-15T12:15:55.570Z"),
                    "15-inch steel blade hand saw",
                    3.7f,
                    "http://openclipart.org/image/300px/svg_to_png/27070/egore911_saw.png",
                    1200),
                new Product("5","Video Game Controller", "GMG-0042", 3595, date.parse("2017-10-15T14:15:55.570Z"),
                    "15-inch steel blade hand saw",
                    4.6f,
                    "http://openclipart.org/image/300px/svg_to_png/120337/xbox-controller_01.png",
                    300))
            .verifyComplete();
    }

    @Test
    public void shouldReturnProductById() throws ParseException {
        StepVerifier.create(basicService.getProductById("1"))
            .expectNext(new Product("1","Leaf Rake", "GDN-0011", 1995, date.parse("2017-03-19T15:15:55.570Z"),
                "Leaf rake with 48-inch wooden handle.", 3.2f,
                "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png", 400))
            .verifyComplete();
    }
}
