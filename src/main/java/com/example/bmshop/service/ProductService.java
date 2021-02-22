package com.example.bmshop.service;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

}
