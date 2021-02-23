package com.example.bmshop.entity;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "product_sequence")
    private Long id;
    private String name;
    private float weight;
    private float price;
    private float quantity;

    public Product() {};

    public Product(Long id, String name, float weight, float price, float quantity) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, float weight, float price, float quantity) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
