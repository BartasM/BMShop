package com.example.bmshop.service;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerByEmail = customerRepository.customerFindByEmail(customer.getEmail());
        if (customerByEmail.isPresent()) {
            throw new IllegalStateException("email already taken");
        } else {
            customerRepository.save(customer);
        }
    }

    @Transactional
    public void updateCustomer(Long customerId,
                               String name,
                               String email) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new IllegalStateException("Customer with id " + customerId + " deos not exist."));

        if(name != null && name.length()>0 &&
                !Objects.equals(customer.getName(), name)){
            customer.setName(name);
        }

        if(email != null && email.length()>0 &&
                !Objects.equals(customer.getEmail(), email)){
            customer.setEmail(email);
        }
    }

    @Transactional
    public void deleteCustomer(Long customerId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            customerRepository.deleteById(customerId);
        }
    }
}
