package com.example.bmshop.controller;

import com.example.bmshop.BmshopApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BmshopApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class BasketControllerIT {
}
