package com.ecommecer.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    // testa se um usuario tem acesso a esse edpoint
    public void givenUser_whenAccessProductEndpoint_thenStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/someProduct"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void givenUser_whenAccessCustomerEndpoint_thenStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/someCustomer"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    // testa um usuario não autenticado o endpoint
    public void givenNoUser_whenAccessProductEndpoint_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/someProduct"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    // testa um usuário com papel admin ao acesso ao endpoint
    public void givenAdmin_whenAccessProductEndpoint_thenStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/someProduct")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
