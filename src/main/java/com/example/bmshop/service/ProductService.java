package com.example.bmshop.service;

import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product productFindById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("ProductId : " + productId + " doesn't exist."));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long productId, String name_product, float quantity) {

        Product product = productFindById(productId);

        if (name_product == null || name_product.isEmpty() || name_product.isBlank()) {
            throw new IllegalArgumentException("name_product is null or blank");
        } else {
            if (!Objects.equals(product.getName(), name_product)) {
                product.setName(name_product);
            }
        }
        if(quantity < 0){
            throw new IllegalArgumentException("quantity is null or has to be greater than 0.");
        } else {
            if (!Objects.equals(product.getQuantity(), quantity)) {
                product.setQuantity(quantity);
            }
        }
        return productRepository.saveAndFlush(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
