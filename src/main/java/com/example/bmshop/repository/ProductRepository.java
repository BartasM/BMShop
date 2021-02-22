package com.example.bmshop.repository;

import com.example.bmshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query("SELECT p FROM product p WHERE p.name_product = 1?")
    Optional<Product> productFindByName_product(String name_product);
}
