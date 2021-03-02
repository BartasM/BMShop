package com.example.bmshop.entity;

import javax.persistence.*;
import java.util.Objects;
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

    public Basket(){}

    public Basket(Long id) {
        this.id = id;
    }

    public Basket(Long id, Customer customer, Set<BasketPosition> basketPosition) {
        this.id = id;
        this.customer = customer;
        this.basketPosition = basketPosition;
    }

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

    public Set<BasketPosition> getBasketPositionSet() {
        return basketPosition;
    }

    public void setBasketCreatedSet(Set<BasketPosition> basketPosition) {
        this.basketPosition = basketPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket = (Basket) o;

        if (!Objects.equals(id, basket.id)) return false;
        if (!Objects.equals(customer, basket.customer)) return false;
        return Objects.equals(basketPosition, basket.basketPosition);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (basketPosition != null ? basketPosition.hashCode() : 0);
        return result;
    }
}
