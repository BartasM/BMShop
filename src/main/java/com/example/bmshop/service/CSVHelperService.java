package com.example.bmshop.service;

import com.example.bmshop.CSVHelper.CsvHelper;
import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CSVHelperService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public ByteArrayInputStream load(){
        List<Customer> customers = customerRepository.findAll();

        ByteArrayInputStream in = CsvHelper.customersToCsv(customers);
        return in;
    }

    @Transactional
    public void save(MultipartFile file) {
        try {
            List<Customer> customers = CsvHelper.csvToCustomers(file.getInputStream());
            customerRepository.saveAll(customers);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
}
