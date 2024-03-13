package com.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String Id;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
}
