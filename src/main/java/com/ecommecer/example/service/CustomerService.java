package com.ecommecer.example.service;

import com.ecommecer.example.entity.Customer;
import com.ecommecer.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // Lidando com exceções.
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found by id:" + id));
    }

    public Customer saveCUstomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // vou retornar um valor indicado pela resultado da operação
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
