package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import com.example.api.web.rest.model.CustomerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer limit
    ) {
        return service.findAll(name, email, gender, page, limit);
    }

    @GetMapping("/{id}")
    public Customer findById(
            @PathVariable Long id
    ) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestBody CustomerRequest customerRequest
    ) {
        Customer createdCustomer = service.createCustomer(customerRequest);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerRequest customerRequest
    ) {
        Customer updatedCustomer = service.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }
}
