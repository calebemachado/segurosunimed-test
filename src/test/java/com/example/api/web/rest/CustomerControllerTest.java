package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    @Test
    void testFindAll() {
        List<Customer> customers = Arrays
                .asList(new Customer(1L, "John", "john@gmail.com", "male"),
                        new Customer(2L, "Jane", "jane@gmail.com", "female"));
        when(service.findAll(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(customers);

        List<Customer> result = controller.findAll("", "", "", 0, 5);

        assertEquals(customers, result);
        verify(service, times(1)).findAll("", "", "", 0, 5);
    }

    @Test
    void testFindById() {
        Long customerId = 1L;
        Customer customer = new Customer(1L, "John", "john@gmail.com", "male");
        when(service.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = controller.findById(customerId);

        assertEquals(customer, result);
        verify(service, times(1)).findById(customerId);
    }

    @Test
    void testFindByIdNotFound() {
        Long customerId = 1L;
        when(service.findById(customerId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> controller.findById(customerId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Customer not found", exception.getReason());
        verify(service, times(1)).findById(customerId);
    }
}