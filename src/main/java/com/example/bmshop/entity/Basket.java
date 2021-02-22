package com.example.bmshop.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

public class Basket {
    @SequenceGenerator(name = "basket_sequence",
            sequenceName = "basket_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "basket_sequence")
    private Long id;


}
