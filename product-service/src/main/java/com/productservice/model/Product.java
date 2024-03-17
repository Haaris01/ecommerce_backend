package com.productservice.model;

import lombok.*;
import org.springframework.aot.generate.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class Product {

    @Id
    private String id;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private Ratings ratings;
}

class Ratings {
    float stars;
    int count;
}
