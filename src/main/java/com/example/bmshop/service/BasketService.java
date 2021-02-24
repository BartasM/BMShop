package com.example.bmshop.service;

import com.example.bmshop.dto.BasketCreatedModDTO;
import com.example.bmshop.dto.BasketPositionDTO;
import com.example.bmshop.entity.Basket;
import com.example.bmshop.entity.BasketPosition;
import com.example.bmshop.entity.Customer;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.BasketPositionRepository;
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
    private final BasketPositionRepository basketPositionRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository, ProductRepository productRepository, BasketPositionRepository basketPositionRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.basketPositionRepository = basketPositionRepository;
    }

    public List<Basket> getBaskets() {
        return basketRepository.findAll();
    }

    public Basket getBasketById(Long basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new IllegalStateException("basketId: " + basketId + " doesnt exist."));
    }

    @Transactional
    public void addBasket(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Basket basket = new Basket(customerOptional.get());
            basketRepository.save(basket);
            basketRepository.flush();
        } else {
            throw new IllegalStateException("Customer with id: " + customerId + " doesn't exist.");
        }
    }

    @Transactional
    public void deleteBasket(Long basketId) {
        basketRepository.deleteById(basketId);
    }

    @Transactional
    public void addBasketPosition(BasketPositionDTO basketPositionDTO) {

        Optional<Basket> basketOptional = basketRepository.findById(basketPositionDTO.getBasketId());
        Optional<Product> productOptional = productRepository.findById(basketPositionDTO.getProductId());

        if (basketOptional.isPresent() && productOptional.isPresent()) {
            if (basketPositionRepository.findById(basketPositionDTO.getBasketId()).isPresent()) {
                updateBasketPositionQuantity(basketPositionDTO.getBasketId(), basketPositionDTO.getProductId(), basketPositionDTO.getQuantity());
            }else{
                BasketPosition basketPosition = new BasketPosition();
                basketPosition.setBasket(basketOptional.get());
                basketPosition.setProductId(productOptional.get());
                basketPosition.setQuantity(basketPositionDTO.getQuantity());
                basketPositionRepository.saveAndFlush(basketPosition);
            }
        }
    }

    public void updateBasketPositionQuantity(Long basketPositionId, Long productId, int quantity) {
        BasketPosition basketPosition = basketPositionRepository.findById(basketPositionId).orElseThrow(
                () -> new IllegalStateException("basketPositionId: " + basketPositionId + " doesn't exist."));
        int newQuantity = basketPosition.getQuantity() + quantity;
        basketPosition.setQuantity(newQuantity);
        basketPositionRepository.saveAndFlush(basketPosition);
    }

    @Transactional
    public void updateBasketPosition(Long basketPositionId, BasketCreatedModDTO basketCreatedModDTO) {
        BasketPosition basketPosition = basketPositionRepository.findById(basketPositionId)
                .orElseThrow(() -> new IllegalStateException("basketPositionId: " + basketPositionId + " doesn't exist."));
        basketPosition.setQuantity(basketCreatedModDTO.getQuantity());
        basketPositionRepository.save(basketPosition);
    }

}
