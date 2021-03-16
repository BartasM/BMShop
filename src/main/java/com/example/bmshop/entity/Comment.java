package com.example.bmshop.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public Comment(Product productId, String commentText) {
        this.productId = productId;
        this.commentText = commentText;
    }

    public Comment(Customer customerId, String commentText) {
        this.customerId = customerId;
        this.commentText = commentText;
    }

    public Comment(Basket basketId, String commentText) {
        this.basketId = basketId;
        this.commentText = commentText;
    }

    public Comment(BasketPosition basketPositionId, String commentText) {
        this.basketPositionId = basketPositionId;
        this.commentText = commentText;
    }

    public Comment(Long commentId, Customer customerId, Basket basketId, BasketPosition basketPositionId, String commentText) {
        this.commentId = commentId;
        this.customerId = customerId;
        this.basketId = basketId;
        this.basketPositionId = basketPositionId;
        this.commentText = commentText;
    }

}
