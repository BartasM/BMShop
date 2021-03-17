package com.example.bmshop.controller;

import com.example.bmshop.entity.Product;
import com.example.bmshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping(path = "api/product")
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = {"application/hal+json"})
    public CollectionModel<Product> getProducts(){
       List<Product> productList = productService.getProducts();
       productList.stream().forEach(product -> product.add(linkTo(ProductController.class).slash(product.getId()).withSelfRel()));
       Link link = linkTo(ProductController.class).withSelfRel();
       CollectionModel<Product> result = CollectionModel.of(productList, link);
       return result;
    }

    @GetMapping(path = "{productId}")
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable Long productId){
        Link link = linkTo(ProductController.class).slash(productId.toString()).withSelfRel();
        EntityModel entityModel = EntityModel.of(productService.productFindById(productId), link);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PostMapping
    public void addProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long productId,
                              @RequestParam(required = false) String nameProduct,
                              @RequestParam(required = false) float quantity){

         productService.updateProduct(productId, nameProduct, quantity);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }
}
