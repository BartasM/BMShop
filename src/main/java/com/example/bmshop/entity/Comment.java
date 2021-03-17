package com.example.bmshop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @SequenceGenerator(
            name = "comment_seq",
            sequenceName = "comment_seq",
            initialValue = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_seq")
    @Id
    private Long commentId;

    @JoinColumn(name = "customerId")
    @ManyToOne
    private Customer customer;

    @JoinColumn(name = "basketId")
    @ManyToOne
    private Basket basket;

    @JoinColumn(name = "basketPositionId")
    @ManyToOne
    private BasketPosition basketPosition;

    @JoinColumn(name = "productId")
    @ManyToOne
    private Product product;

    @Column(name = "commentText")
    private String commentText;

    public Comment() {
    }

    public Comment(Long commentId, Customer customer, Basket basket, BasketPosition basketPosition, Product product, String commentText) {
        this.commentId = commentId;
        this.customer = customer;
        this.basket = basket;
        this.basketPosition = basketPosition;
        this.product = product;
        this.commentText = commentText;
    }

    public Comment(Long commentId, Customer customer, Basket basket, BasketPosition basketPosition, Product product) {
        this.commentId = commentId;
        this.customer = customer;
        this.basket = basket;
        this.basketPosition = basketPosition;
        this.product = product;
    }

    public Comment(Long commentId, Customer customer, Basket basket, BasketPosition basketPosition) {
        this.commentId = commentId;
        this.customer = customer;
        this.basket = basket;
        this.basketPosition = basketPosition;
    }

    public Comment(Long commentId, Customer customer, Basket basket) {
        this.commentId = commentId;
        this.customer = customer;
        this.basket = basket;
    }

    public Comment(Long commentId, Customer customer) {
        this.commentId = commentId;
        this.customer = customer;
    }

    public Comment(Long commentId) {
        this.commentId = commentId;
    }


}
