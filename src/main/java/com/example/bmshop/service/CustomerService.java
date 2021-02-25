package com.example.bmshop.service;

import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

    public Customer customerFindById(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer id: " + customerId + " doesn't exist."));
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

    @Transactional
    public void importCustomerFromCSV(){

    }

    @Transactional
    public void exportCustomersToCsv(){

    }
}
