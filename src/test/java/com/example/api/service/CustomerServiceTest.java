package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.exception.NotFoundException;
import com.example.api.exception.ValidationException;
import com.example.api.repository.CustomerRepository;
import com.example.api.web.rest.model.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomer_Success() {
        CustomerRequest customerRequest = new CustomerRequest("John Doe", "john@example.com", "M");
        when(customerRepository.save(any())).thenReturn(new Customer("John Doe", "john@example.com", "M"));

        Customer createdCustomer = customerService.createCustomer(customerRequest);

        assertEquals("JOHN DOE", createdCustomer.getName());
        assertEquals("john@example.com", createdCustomer.getEmail());
        assertEquals("M", createdCustomer.getGender());
    }

    @Test
    void testCreateCustomer_NameValidationException() {
        CustomerRequest customerRequest = new CustomerRequest(null, "invalid_email", "InvalidGender");

        assertThrows(ValidationException.class, () -> customerService.createCustomer(customerRequest));
    }

    @Test
    void testCreateCustomer_EmailValidationException() {
        CustomerRequest customerRequest = new CustomerRequest("john wick", "invalid_email", "InvalidGender");

        assertThrows(ValidationException.class, () -> customerService.createCustomer(customerRequest));
    }

    @Test
    void testCreateCustomer_GenderValidationException() {
        CustomerRequest customerRequest = new CustomerRequest("john wick", "john@gmail.com", "InvalidGender");

        assertThrows(ValidationException.class, () -> customerService.createCustomer(customerRequest));
    }

    @Test
    public void testUpdateCustomer_Success() {
        CustomerRequest customerRequest = new CustomerRequest("Updated Name", "updated@example.com", "F");
        Customer existingCustomer = new Customer("Original Name", "original@example.com", "M");
        when(customerRepository.findById(any())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any())).thenReturn(existingCustomer);

        Customer updatedCustomer = customerService.updateCustomer(1L, customerRequest);

        assertEquals("Updated Name", updatedCustomer.getName());
        assertEquals("updated@example.com", updatedCustomer.getEmail());
        assertEquals("F", updatedCustomer.getGender());
    }

    @Test
    public void testUpdateCustomer_NotFound() {
        CustomerRequest customerRequest = new CustomerRequest("Updated Name", "updated@example.com", "F");
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(1L, customerRequest));
    }

    @Test
    public void testUpdateCustomer_ValidationException() {
        CustomerRequest customerRequest = new CustomerRequest("Some Name", "invalid_email", "F");
        Customer existingCustomer = new Customer("Original Name", "original@example.com", "M");
        when(customerRepository.findById(any())).thenReturn(Optional.of(existingCustomer));

        assertThrows(ValidationException.class, () -> customerService.updateCustomer(1L, customerRequest));
    }
}