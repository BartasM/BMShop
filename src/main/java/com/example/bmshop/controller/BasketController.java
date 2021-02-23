package com.example.bmshop.controller;

import com.example.bmshop.entity.Basket;
import com.example.bmshop.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/basket")
public class BasketController {

    private BasketService BasketService;

    public BasketController(com.example.bmshop.service.BasketService basketService) {
        BasketService = basketService;
    }

    public List<Basket> getBaskets(){
        return BasketService.getBaskets();
    }

    @PostMapping(path = "{customerId}")
    public void addBasket(@PathVariable("customerId") Long customerId){
        BasketService.addBasket(customerId);
    }


}
