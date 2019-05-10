package com.learning.shoppingcartdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private final String productName;
    private String productCode;
    @Builder.Default
    private int price = 0;
    private Date releaseDate;
   // private Date lastModified = new Date();
    private String description;
    @Builder.Default
    private float starRating = 0;
    private String imageUrl;
    @Builder.Default
    private int stock = 1000;
}
