package com.example.bmshop.service;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, String name_product, double quantity){
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product with id " + productId + " does not exist."));

        if(!Objects.equals(product.getName_product(), name_product)){
            product.setName_product(name_product);
        }
        if(!Objects.equals(product.getQuantity(), quantity)){
            product.setQuantity(quantity);
        }
    }

    @Transactional
    public void deleteProduct(Long productId){
        Optional<Product> productOptional = productRepository.findById(productId);
               if(productOptional.isPresent()){
                   productRepository.deleteById(productId);
               }
    }
}
