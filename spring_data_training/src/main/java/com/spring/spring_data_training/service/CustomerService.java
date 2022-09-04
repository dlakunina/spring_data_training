package com.spring.spring_data_training.service;

import com.spring.spring_data_training.entity.Customer;
import com.spring.spring_data_training.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Optional<Customer> updateCustomer(Customer newCustomer){
        Optional<Customer> existingCustomerOpt = customerRepository.findById(newCustomer.getId());
        if (existingCustomerOpt.isPresent()){
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setFirstName(newCustomer.getFirstName());
            existingCustomer.setLastName(newCustomer.getLastName());
            customerRepository.save(existingCustomer);
            return Optional.of(existingCustomer);
        } else {
            return Optional.empty();
        }
    }
}
