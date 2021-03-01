package com.example.bmshop.controller;

import com.example.bmshop.BmshopApplication;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = BmshopApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanAfterTests() {
        productRepository.deleteAll();
        productRepository.flush();
    }

    @Test
    public void productNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/product/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getOneProductTest() throws Exception {
        Product product = new Product(3L, "Milk", 3.0f, 4.0f, 5.0f);
        Product saveProduct = productRepository.saveAndFlush(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/product/" + saveProduct.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Milk")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(3.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(4.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", CoreMatchers.is(5.0)))
        ;
    }

    @Test
    public void getAllProductTest() throws Exception {
        Product product = new Product(3L, "Milk", 3.0f, 4.0f, 5.0f);
        Product saveProduct = productRepository.saveAndFlush(product);

        product = new Product(3L, "Apple", 1.0f, 2.0f, 3.0f);
        Product saveProduct1 = productRepository.saveAndFlush(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/product")
                //  .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", CoreMatchers.hasItems("Milk", "Apple")))

        ;
    }

}
