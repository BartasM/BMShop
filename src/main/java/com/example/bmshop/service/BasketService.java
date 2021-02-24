package com.example.bmshop.service;

import com.example.bmshop.dto.BasketCreatedDTO;
import com.example.bmshop.dto.BasketCreatedModDTO;
import com.example.bmshop.entity.Basket;
import com.example.bmshop.entity.BasketPosition;
import com.example.bmshop.entity.Customer;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.BasketCreatedRepository;
import com.example.bmshop.repository.BasketRepository;
import com.example.bmshop.repository.CustomerRepository;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BasketCreatedRepository basketCreatedRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository, ProductRepository productRepository, BasketCreatedRepository basketCreatedRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.basketCreatedRepository = basketCreatedRepository;
    }

    public List<Basket> getBaskets() {
        return basketRepository.findAll();
    }

    public Basket getBasketById(Long basketId){
        return basketRepository.findById(basketId).orElseThrow(() -> new IllegalStateException("basketId: " + basketId + " doesnt exist."));
    }

    @Transactional
    public void addBasket(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
        Basket basket = new Basket(customerOptional.get());
        basketRepository.save(basket);
        basketRepository.flush();
        }else {
            throw new IllegalStateException("Customer with id: " + customerId + " doesn't exist.");
        }
    }

    @Transactional
    public void deleteBasket(Long basketId) {
            basketRepository.deleteById(basketId);
    }

    @Transactional
    public void addBasketCreated(BasketCreatedDTO basketCreatedDTO){
        Optional<Basket> basketOptional = basketRepository.findById(basketCreatedDTO.getBasketId());
        Optional<Product> productOptional = productRepository.findById(basketCreatedDTO.getProduktId());
        if(basketOptional.isPresent() && productOptional.isPresent()){
            BasketPosition basketPosition = new BasketPosition();
            basketPosition.setBasket(basketOptional.get());
            basketPosition.setProductId(productOptional.get());
            basketPosition.setQuantity(basketCreatedDTO.getQuantity());
            basketCreatedRepository.saveAndFlush(basketPosition);
        }
    }

    @Transactional
    public void updateBasketCreated(Long basketCreatedId, BasketCreatedModDTO basketCreatedModDTO){
        BasketPosition basketPosition = basketCreatedRepository.findById(basketCreatedId)
                .orElseThrow(() -> new IllegalStateException("basketCreatedId: " + basketCreatedId + " doesn't exist."));
        basketPosition.setQuantity(basketCreatedModDTO.getQuantity());
        basketCreatedRepository.save(basketPosition);
    }

}
