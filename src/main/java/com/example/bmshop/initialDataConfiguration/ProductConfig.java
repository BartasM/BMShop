package com.example.bmshop.initialDataConfiguration;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.CustomerRepository;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ProductConfig {
    @Bean
    CommandLineRunner commandLineRunner2(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product(1L, "maslo", 0.5, 13.30, 5.0 );
            Product product2 = new Product(2L, "banany", 0.2, 13.30, 15.0 );
            Product product3 = new Product(3L, "jablka", 0.5, 13.30, 5.0 );
            Product product4 = new Product(4L, "jablka", 0.5, 13.30, 5.0 );

            productRepository.saveAll(List.of(product1, product2, product3, product4));
        };
    }


}
