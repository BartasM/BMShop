package com.example.bmshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BaskedUsed {

    @Id
    private Long id;
    private Long productId;

    public BaskedUsed() { }

    public BaskedUsed(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
