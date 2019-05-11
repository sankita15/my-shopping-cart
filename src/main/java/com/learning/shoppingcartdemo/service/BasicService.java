package com.learning.shoppingcartdemo.service;

import com.learning.shoppingcartdemo.model.Product;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
public class BasicService {

    private HashMap<String, Product> listOfProduct;

    @SneakyThrows
    private static HashMap<String, Product> setUpMethod() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        HashMap<String, Product> listOfProduct = new HashMap<>();

        Product product1 = Product.builder().id("1")
            .productName("Leaf Rake")
            .productCode("GDN-0011")
            .price(1995)
            .releaseDate(date.parse("2017-03-19T15:15:55.570Z"))
            .description("Leaf rake with 48-inch wooden handle.")
            .starRating(3.2f)
            .imageUrl("http://openclipart.org/image/300px/svg_to_png/26215/Anonymous_Leaf_Rake.png")
            .stock(400).build();

        Product product2 = Product.builder().id("2")
            .productName("Garden Cart")
            .productCode("GDN-0023")
            .price(3295)
            .releaseDate(date.parse("2017-03-18T08:15:55.570Z"))
            .description("15 gallon capacity rolling garden cart")
            .starRating(3.2f)
            .imageUrl("http://openclipart.org/image/300px/svg_to_png/58471/garden_cart.png")
            .stock(20).build();

        Product product3 = Product.builder().id("3")
            .productName("Hammer")
            .productCode("TBX-0048")
            .price(890)
            .releaseDate(date.parse("2017-05-21T12:15:55.570Z"))
            .description("Curved claw steel hammer")
            .starRating(4.8f)
            .imageUrl("http://openclipart.org/image/300px/svg_to_png/73/rejon_Hammer.png")
            .stock(400).build();

        Product product4 = Product.builder().id("4")
            .productName("Saw")
            .productCode("TBX-0022")
            .price(1195)
            .releaseDate(date.parse("2017-05-15T12:15:55.570Z"))
            .description("15-inch steel blade hand saw")
            .starRating(3.7f)
            .imageUrl("http://openclipart.org/image/300px/svg_to_png/27070/egore911_saw.png")
            .stock(1200).build();

        Product product5 = Product.builder().id("5")
            .productName("Video Game Controller")
            .productCode("GMG-0042")
            .price(3595)
            .releaseDate(date.parse("2017-10-15T14:15:55.570Z"))
            .description("15-inch steel blade hand saw")
            .starRating(4.6f)
            .imageUrl("http://openclipart.org/image/300px/svg_to_png/120337/xbox-controller_01.png")
            .stock(300).build();

        listOfProduct.put(product1.getId(), product1);
        listOfProduct.put(product2.getId(), product2);
        listOfProduct.put(product3.getId(), product3);
        listOfProduct.put(product4.getId(), product4);
        listOfProduct.put(product5.getId(), product5);

        return listOfProduct;
    }

    public BasicService() {
        this.listOfProduct = setUpMethod();
    }

    public Flux<Product> getProducts() {
        return Flux.fromIterable(listOfProduct.values());
    }

    public Mono<Product> getProductById(String id) {
        return getProducts().filter(product -> product.getId().equals(id)).next();
    }

    public Mono<Product> updateProductDetails(Product product, String id) {
        listOfProduct.put(id, product);
        return getProductById(id);
    }

    public Mono<Product> addProductDetails(Product product){
        listOfProduct.put(product.getId(), product);

        return getProductById(product.getId());
    }

    public Mono<Product> deleteProductDetails(String id){
         Product removedProduct = listOfProduct.remove(id);
         return Mono.just(removedProduct);
    }
}
