package com.example.bmshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "basked_position")
public class BasketPosition {

    @Id
    @Column(name = "basketPositionId")
    @SequenceGenerator(
            name = "BasketPosition_generator",
            sequenceName = "BasketPosition_generator",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BasketPosition_generator")
    private Long basketCreatedId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "basketId", nullable = false)
    private Basket basket;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "productId", nullable = false)
    private Product productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public Long getBasketCreatedId() {
        return basketCreatedId;
    }

    public void setBasketCreatedId(Long basketCreatedId) {
        this.basketCreatedId = basketCreatedId;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
