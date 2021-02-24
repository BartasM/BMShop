package com.example.bmshop.service;


import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Collection<Customer>> customersArgumentCaptor;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void getCustomer(){
        //Given
        Customer customer = new Customer(1L, "Marek", "marek@gmail.com",
                LocalDate.of(2000,05,9), true);

        given(customerRepository.findById(Mockito.eq(1L))).willReturn(Optional.of(customer));

        //When
      //  Customer result = customerService.customerFindById(1L);
        //Then

      //  assertThat(result).isNotNull();
      //  assertThat(result.)


    }

}
