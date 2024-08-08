package com.ecommecer.example;

import com.ecommecer.example.controller.ProductController;
import com.ecommecer.example.entity.Product;
import com.ecommecer.example.service.ProductService;
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

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProduct() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "product 1", 10.0),
                new Product(2L, "product 2", 20.0)
        );

        when(productService.getAllProduct()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("product 1"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("product 2"))
                .andExpect(jsonPath("$[1].price").value(20.0));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product(1L, "product 1", 10.0);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product(1L, "product 1", 10.0);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"product 1\", \"price\": 10.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        Long id = 1L;

        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(id);
    }
}
