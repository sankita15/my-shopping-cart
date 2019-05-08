package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasicService {

    Flux<Product> listOfProduct;

    public void setUpMethod() throws ParseException {
        //Initialize the default products
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

         Product product1 = new Product("1","Leaf Rake", "GDN-0011", 1995, date.parse("2017-03-19T15:15:55.570Z"),
            "Leaf rake with 48-inch wooden handle.", 3.2f,
            "http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png", 400);

         Product product2 = new Product("2","Garden Cart", "GDN-0023", 3295,
            date.parse("2017-03-18T08:15:55.570Z"), "15 gallon capacity rolling garden cart", 4.2f,
            "http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png",
            20);

         Product product3 = new Product("3","Hammer", "TBX-0048", 890, date.parse("2017-05-21T12:15:55.570Z"),
            "Curved claw steel hammer",
            4.8f,
            "http://openclipart.org/image/300px/svg_to_png/73/rejon_Hammer.png",
            400);

         Product product4 = new Product("4","Saw", "TBX-0022", 1155, date.parse("2017-05-15T12:15:55.570Z"),
            "15-inch steel blade hand saw",
            3.7f,
            "http://openclipart.org/image/300px/svg_to_png/27070/egore911_saw.png",
            1200);

         Product product5 = new Product("5","Video Game Controller", "GMG-0042", 3595, date.parse("2017-10-15T14:15:55.570Z"),
            "15-inch steel blade hand saw",
            4.6f,
            "http://openclipart.org/image/300px/svg_to_png/120337/xbox-controller_01.png",
            300);

         listOfProduct = Flux.just(product1, product2, product3, product4, product5);
    }

    public Flux<Product> getProducts() throws ParseException {
        setUpMethod();
        return listOfProduct;
    }

    public Mono<Product> getProductById(String id) throws ParseException {
        setUpMethod();
        Mono<Product> productById = listOfProduct.filter(product -> product.getId().equals(id)).next();
        return productById;
    }
}
