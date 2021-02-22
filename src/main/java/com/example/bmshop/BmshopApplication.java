package com.example.bmshop;

import com.example.bmshop.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BmshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmshopApplication.class, args);

	}

}
