package com.example.bmshop.initialDataConfiguration;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@Configuration
@ActiveProfiles({"dev"})
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            Customer bartek = new Customer(1L,
                    "Bartek",
                    "bartek@gmail.com",
                    LocalDate.of(2000, 5, 1),
                    true);

            Customer kasia = new Customer(
                    2L,
                    "Kasia",
                    "kasia@gmail.com",
                    LocalDate.of(2004, 6, 15),
                    true);

            Customer bolek = new Customer(
                    3L,
                    "Bolek",
                    "bolek@gmail.com",
                    LocalDate.of(2001, 3, 12),
                    true);

            Customer grzegorz = new Customer(
                    4L,
                    "grzegorz",
                    "grzegorz@gmail.com",
                    LocalDate.of(2001, 3, 12),
                    true);
            customerRepository.saveAll(List.of(bartek, kasia, bolek, grzegorz));
        };
    }
}
