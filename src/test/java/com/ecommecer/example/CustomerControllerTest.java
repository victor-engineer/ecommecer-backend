package com.ecommecer.example;

import com.ecommecer.example.controller.CustomerController;
import com.ecommecer.example.entity.Customer;
import com.ecommecer.example.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomer() throws Exception {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "João", "customer@example.com", 992006439),
                new Customer(2L, "Jefferson", "customer@example.com", 992006438)
        );

        when(customerService.getAllCustomer()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("João"))
                .andExpect(jsonPath("$[0].email").value("customer@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jefferson"))
                .andExpect(jsonPath("$[1].email").value("customer@example.com"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1L, "João", "customer@example.com", 992006439);

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João"))
                .andExpect(jsonPath("$.email").value("customer@example.com"));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer(1L, "João", "customer@example.com", 992006439);

        when(customerService.saveCUstomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"João\", \"email\": \"customer@example.com\", \"phone\": 992006439}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João"))
                .andExpect(jsonPath("$.email").value("customer@example.com"))
                .andExpect(jsonPath("$.phone").value(992006439));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        Long id = 1L;

        doNothing().when(customerService).deleteCustomer(id);

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(id);
    }
}
