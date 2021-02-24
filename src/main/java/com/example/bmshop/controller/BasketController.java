package com.example.bmshop.controller;

import com.example.bmshop.dto.BasketCreatedDTO;
import com.example.bmshop.dto.BasketCreatedModDTO;
import com.example.bmshop.entity.Basket;
import com.example.bmshop.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/basket")
public class BasketController {

    private BasketService basketService;

    public BasketController(com.example.bmshop.service.BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<Basket> getBaskets(){
        return basketService.getBaskets();
    }

    @GetMapping(path = "{basketId}")
    public Basket getBasket(@PathVariable("basketId") Long basketId){
       return basketService.getBasketById(basketId);
    }

    @PostMapping(path = "{customerId}")
    public void addBasket(@PathVariable("customerId") Long customerId){
        basketService.addBasket(customerId);
    }

    @DeleteMapping(path = "{basketId}")
    public void deleteBasket(@PathVariable("basketId") Long basketId){
        basketService.deleteBasket(basketId);
    }

    @PostMapping
    public void addBasketCreated(@RequestBody BasketCreatedDTO basketCreatedDTO){
        basketService.addBasketCreated(basketCreatedDTO);
    }

    @PutMapping(path = "{basketCreatedId}")
    public void updateBasketCreated(@PathVariable("basketCreatedId") Long basketCreatedId,
                                    @RequestBody BasketCreatedModDTO basketCreatedModDTO){
        basketService.updateBasketCreated(basketCreatedId, basketCreatedModDTO);
    }



}
