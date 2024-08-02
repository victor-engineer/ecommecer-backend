package com.ecommecer.example.controller;

import com.ecommecer.example.entity.Customer;
import com.ecommecer.example.entity.Product;
import com.ecommecer.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{customer}")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    @PostMapping
    public Customer createCustomer(Customer customer) {
        return customerService.saveCUstomer(customer);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }
}
