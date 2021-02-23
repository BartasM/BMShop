package com.example.bmshop.service;

import com.example.bmshop.entity.Basket;
import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.BasketRepository;
import com.example.bmshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
    }

    public List<Basket> getBaskets() {
        return basketRepository.findAll();
    }

    public void addBasket(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
        Basket basket = new Basket(customerId);
        }else {
            throw new IllegalStateException("Customer with id: " + customerId + " doesn't exist.");
        }
    }

    public void deleteBasket(Long basketId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (!basketOptional.isPresent()) {
            throw new IllegalStateException("basket id: " + basketId + " doesn't exist.");
        } else {
            basketRepository.deleteById(basketId);
        }
    }
}
