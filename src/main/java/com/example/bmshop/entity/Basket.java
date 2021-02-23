package com.example.bmshop.entity;

import javax.persistence.*;

@Entity
public class Basket {
    @SequenceGenerator(name = "basket_sequence",
            sequenceName = "basket_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "basket_sequence")
    @Id
    private Long id;

    @Column(name = "customerId")
    private Long customerId;

    public Basket(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
