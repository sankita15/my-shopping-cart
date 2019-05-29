package com.learning.shoppingcartdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "ShoppingCart")
public class ShoppingCart {

    public static final String ORDERED = "ordered";
    public static final String PENDING = "pending";

    @Id
    public String id;

    public String username;
    public HashMap<String, Product> products;
    public HashMap<String, Integer> productQuantities;
    @Builder.Default
    public int cartPrice = 0;
    @Builder.Default
    public Date orderDate = new Date();
    @Builder.Default
    public String cartStatus = ShoppingCart.PENDING;

}

