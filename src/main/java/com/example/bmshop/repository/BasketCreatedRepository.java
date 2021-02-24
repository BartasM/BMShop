package com.example.bmshop.repository;

import com.example.bmshop.entity.BasketPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketCreatedRepository extends JpaRepository<BasketPosition, Long> {
}
