package com.productservice.repository;


import com.productservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface ProductRepository extends MongoRepository<Product, String> {
    public Optional<Product> findByName(String name);
    public void deleteByName(String name);
}
