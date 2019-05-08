package com.learning.shoppingcartdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String id;
    private String productName;
    private String productCode;
    private int price = 0;
    private Date releaseDate;
   // private Date lastModified = new Date();
    private String description;
    private float starRating = 0;
    private String imageUrl;
    private int stock = 1000;
}
