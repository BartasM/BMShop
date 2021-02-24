package com.example.bmshop.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Basket {
    @SequenceGenerator(name = "basket_sequence",
            sequenceName = "basket_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "basket_sequence")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BasketPosition> basketPosition;

    public Basket(Long id) {
        this.id = id;
    }
    public Basket(){}

    public Basket(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomerId() {
        return customer;
    }

    public void setCustomerId(Customer customerId) {
        this.customer = customer;
    }

    public Set<BasketPosition> getBasketCreatedSet() {
        return basketPosition;
    }

    public void setBasketCreatedSet(Set<BasketPosition> basketPosition) {
        this.basketPosition = basketPosition;
    }
}
