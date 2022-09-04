package com.spring.spring_data_training.controller;

import com.spring.spring_data_training.entity.Customer;
import com.spring.spring_data_training.repository.CustomerRepository;
import com.spring.spring_data_training.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFindAllCustomers() throws Exception {
        Customer customer = new Customer("John", "Doe");
        List<Customer> customers = Arrays.asList(customer);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        mockMvc.perform(get("/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("John")));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testFindCustomerById() throws Exception {
        Customer customer = new Customer("John", "Doe");

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        mockMvc.perform(get("/api/customer/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", Matchers.is("John")));
        verify(customerRepository, times(1)).findById(1L);
    }
}
