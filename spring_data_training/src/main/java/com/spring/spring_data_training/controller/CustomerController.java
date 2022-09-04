package com.spring.spring_data_training.controller;

import com.spring.spring_data_training.entity.Customer;
import com.spring.spring_data_training.repository.CustomerRepository;
import com.spring.spring_data_training.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> findAllCustomers() {
       return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable(value = "id") long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(customer -> ResponseEntity.ok().body(customer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("add-customer")
    public Customer addCustomer(@Validated @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PostMapping("update-customer")
    public ResponseEntity<Customer> updateCustomer(@Validated @RequestBody Customer customer) {
        Optional<Customer> updatedCustomerOpt = customerService.updateCustomer(customer);
        return updatedCustomerOpt.map(updatedCustomer -> ResponseEntity.ok().body(updatedCustomer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
