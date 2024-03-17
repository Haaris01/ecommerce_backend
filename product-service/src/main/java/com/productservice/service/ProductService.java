package com.productservice.service;

import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRequest productRequest;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductResponse productResponse;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .imageUrl(productRequest.getImageUrl())
                .build();

        productRepository.save(product);
        log.info("Product " + product.getId() + "is created.");
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        log.info("Product List successfully fetched");
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse findProductByName(String name) {

        Optional<Product> product = productRepository.findByName(name);
        Product prod = product.isPresent() ? product.get() : null;
        return ProductResponse.builder()
                .Id(prod.getId())
                .name(prod.getName())
                .price(prod.getPrice())
                .quantity(prod.getQuantity())
                .imageUrl(prod.getImageUrl())
                .build();
    }

    public void updateProduct(String name, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findByName(name);
        Product existingProduct = product.get();
        System.out.println(existingProduct);
        existingProduct.setName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setImageUrl(productRequest.getImageUrl());
        productRepository.save(existingProduct);
    }
}
