package com.example.bmshop.controller;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/customer")
public class CustomerController {

    public final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(path = "{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        return customerService.customerFindById(customerId);
    }

    @PostMapping
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addNewCustomer(customer);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String email){
        customerService.updateCustomer(customerId, name, email);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }
}
