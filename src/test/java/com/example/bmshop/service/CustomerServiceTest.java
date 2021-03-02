package com.example.bmshop.service;


import com.example.bmshop.entity.Customer;
import com.example.bmshop.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Customer> customersArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longCustomersArgumentCaptor;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void getCustomerTest(){
        //Given
       List<Customer> customerList = List.of(new Customer(1L, "Marek", "marek@gmail.com",
                LocalDate.of(2000,05,9), true));

        given(customerRepository.findAll()).willReturn(customerList);

        //When
        List<Customer> customersResultList = customerService.getCustomers();

        //Then
        assertThat(customerList).isEqualTo(customersResultList);
    }

    @Test
    public void addNewCustomerTestException(){

        Customer customerTest = new Customer(2L, "Mark", "mark@gmail.com",
                LocalDate.of(2002,05,9), true);
        given(customerRepository.customerFindByEmail("mark@gmail.com")).willReturn(Optional.of(customerTest));

        try {
            customerService.addNewCustomer(customerTest);
        }catch (IllegalStateException x){
            assertThat(x.getMessage()).isEqualTo("email already taken");
        }


    }
    @Test
    public void addNewCustomerTest(){

        Customer customerTest = new Customer(2L, "Mark", "mark@gmail.com",
                LocalDate.of(2002,05,9), true);
        Customer customerTest2 = new Customer();
        given(customerRepository.customerFindByEmail("mark@gmail.com")).willReturn(Optional.empty());

        customerService.addNewCustomer(customerTest);

        verify(customerRepository, times(1)).save(customersArgumentCaptor.capture());
        Customer customerResult = customersArgumentCaptor.getValue();

        assertThat(customerTest).isEqualTo(customerResult);
    }

    @Test
    public void customerFindByIdTest(){
        //Given
        Customer customerTest = new Customer(3L, "Mark", "mark@gmail.com",
                LocalDate.of(2002,05,9), true);
        given(customerRepository.findById(3L)).willReturn(Optional.of(customerTest));

        //When
        customerService.customerFindById(3L);

        //Then
        verify(customerRepository, times(1)).findById(longCustomersArgumentCaptor.capture());
        Long resultLong = longCustomersArgumentCaptor.getValue();

        assertThat(customerTest.getId()).isEqualTo(resultLong);
    }

    @Test
    public void updateCustomerTest(){
        //Given
        Customer customerTest = new Customer(3L, "Mark", "mark@gmail.com",
                LocalDate.of(2002,05,9), true);
        given(customerRepository.findById(3L)).willReturn(Optional.of(customerTest));
        assertThat(customerTest.getName()).isEqualTo("Mark");

        //When
        Customer customerResult = customerService.updateCustomer(3L, "Mark1", "mark1@gmail.com");

        //Then
        assertThat(customerTest.getId()).isEqualTo(customerResult.getId());
        assertThat(customerTest.getName()).isEqualTo(customerResult.getName());
        assertThat(customerTest.getEmail()).isEqualTo(customerResult.getEmail());
    }

    @Test
    public void deleteCustomerTest(){
        //Given
        Long customerId = 10L;

        //When
        customerService.deleteCustomer(customerId);

        //Then
        verify(customerRepository,times(1)).deleteById(longCustomersArgumentCaptor.capture());
        Long resultId = longCustomersArgumentCaptor.getValue();

        assertThat(customerId).isEqualTo(resultId);
    }
}
