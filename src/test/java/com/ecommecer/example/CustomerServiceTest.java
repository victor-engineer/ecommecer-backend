package com.ecommecer.example;

import com.ecommecer.example.entity.Customer;
import com.ecommecer.example.repository.CustomerRepository;
import com.ecommecer.example.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testGetaAllCustomer() {
        Customer user0 = new Customer(1L, "João", "customer@example.com", 992006439);
        Customer user1 = new Customer(2L, "Jefferson", "customer@example.com", 992006438);
        List<Customer> customers = Arrays.asList(user0, user1);

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(2, customers.size());
        assertEquals("user0", customers.get(0).getName());
        assertEquals("user1", customers.get(1).getName());

    }

    @Test
    public void testGetCustomerById_customerExist() {
        Customer user0 = new Customer(1L, "João", "customer@example.com", 992006439);
        Customer user1 = new Customer(2L, "Jefferson", "customer1@example.com", 992006438);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(user0));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(user1));

        //METHODCALL
        Customer result0 = customerService.getCustomerById(1L);
        Customer result1 = customerService.getCustomerById(2L);

        //verifying to resulted
        assertEquals(user0, result0);
        assertEquals(user1, result1);
    }

    @Test
    public void testGetCustomerById_customerDoesNotExist() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        Customer result0 = customerService.getCustomerById(1L);
        Customer result1 = customerService.getCustomerById(2L);

        assertNull(result0);
        assertNull(result1);
    }

    @Test
    public void createCustomer() {

        Customer user0 = new Customer(1L, "João", "customer@example.com", 992006439);
        Customer user1 = new Customer(2L, "Jefferson", "customer@example.com", 992006438);

        when(customerRepository.save(user0)).thenReturn(user0);
        when(customerRepository.save(user1)).thenReturn(user1);

        Customer result0 = customerService.saveCUstomer(user0);
        Customer result1 = customerService.saveCUstomer(user1);

        assertEquals(user0, result0);
        assertEquals(user1, result1);
        verify(customerRepository, times(1)).save(user0);
        verify(customerRepository, times(1)).save(user1);
    }

        @Test
        public void testDeleteCustomer_CustomerExists() {
            Long id = 1L;


            // Configura o mock para retornar true quando verificar a existência do cliente
            when(customerRepository.existsById(id)).thenReturn(true);

            // Chama o método deleteCustomer e armazena o resultado
            boolean result = customerService.deleteCustomer(id);

            // Verifica se o resultado do método deleteCustomer é true
            assertTrue(result);

            // Verifica se o método deleteById foi chamado exatamente uma vez com o ID fornecido
            verify(customerRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteCustomer_CustomerDoesNotExist() {
            Long id = 2L;

            // Configura o mock para retornar false quando verificar a existência do cliente
            when(customerRepository.existsById(id)).thenReturn(false);

            // Chama o método deleteCustomer
            boolean result = customerService.deleteCustomer(id);

            // Verifica se o resultado do método deleteCustomer é false
            assertFalse(result);

            // Verifica se o método deleteById não foi chamado
            verify(customerRepository, never()).deleteById(id);
        }
    }
