package com.example.bmshop.service;

import com.example.bmshop.dto.BasketPositionDTO;
import com.example.bmshop.dto.BasketPositionModDTO;
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
    public BasketPosition addBasketPosition(BasketPositionDTO basketPositionDTO) {

        BasketPosition basketPosition = new BasketPosition();

        Optional<Basket> basketOptional = basketRepository.findById(basketPositionDTO.getBasketId());
        Optional<Product> productOptional = productRepository.findById(basketPositionDTO.getProductId());

        if (basketOptional.get().getBasketPositionSet() != null &&
                basketOptional.get().getBasketPositionSet().stream().anyMatch(
                        p -> p.getProductId().getId().equals(basketPositionDTO.getProductId()))) {

            BasketPosition position = basketOptional.get().getBasketPositionSet().stream()
                    .filter(p -> p.getProductId().getId().equals(basketPositionDTO.getProductId()))
                    .findFirst().orElse(null);

            updateBasketPositionQuantity(position.getBasketPositionId(), basketPositionDTO.getQuantity());

        } else {
            basketPosition.setBasket(basketOptional.get());
            basketPosition.setProductId(productOptional.get());
            basketPosition.setQuantity(basketPositionDTO.getQuantity());
            basketPositionRepository.saveAndFlush(basketPosition);
        }
        return basketPosition;
    }

    public void updateBasketPositionQuantity(Long positionId, int quantity) {
        BasketPosition basketPosition = basketPositionRepository.findById(positionId).orElseThrow(
                () -> new IllegalStateException("basketPositionId: " + positionId + " doesn't exist."));
        int newQuantity = basketPosition.getQuantity() + quantity;

        basketPosition.setQuantity(newQuantity);
        basketPositionRepository.saveAndFlush(basketPosition);
    }

    @Transactional
    public BasketPosition updateBasketPosition(Long basketPositionId, BasketPositionModDTO basketPositionModDTO) {
        BasketPosition basketPosition = basketPositionRepository.findById(basketPositionId)
                .orElseThrow(() -> new IllegalStateException("basketPositionId: " + basketPositionId + " doesn't exist."));
        basketPosition.setQuantity(basketPositionModDTO.getQuantity());
        basketPositionRepository.save(basketPosition);
        return basketPosition;
    }

}
