package com.example.api.web.rest;

import com.example.api.ApiApplication;
import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAll() throws Exception {
        List<Customer> customers = Arrays
                .asList(new Customer(1L, "John", "john@gmail.com", "male"),
                        new Customer(2L, "Jane", "jane@gmail.com", "female"));
        when(service.findAll(any(), any(), any(), any(), any(), any(), any())).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    void testFindById() throws Exception {
        Long customerId = 1L;
        Customer customer = new Customer(1L, "John", "john@gmail.com", "male");
        when(service.findById(customerId)).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customer)));
    }
}
