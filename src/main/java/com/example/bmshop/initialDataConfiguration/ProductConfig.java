package com.example.bmshop.initialDataConfiguration;

import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Configuration
@ActiveProfiles({"dev"})
public class ProductConfig {
    @Bean
    CommandLineRunner commandLineRunner2(ProductRepository productRepository) {
        return args -> {

            Product product2 = new Product(2L, "Milk", 0.2f, 13.30f, 15.0f);
            Product product3 = new Product(3L, "Apple", 0.5f, 13.30f, 5.0f);
            Product product4 = new Product(4L, "Banana", 0.5f, 13.30f, 5.0f);

            productRepository.saveAll(List.of(product2,product3,product4));
        };

    }
}
