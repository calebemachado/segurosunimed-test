package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll(String name, String email, String gender, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, limit != null ? limit : 5);

        String filterName = toUpperCaseIfNotNull(name);
        String filterEmail = toLowerCaseIfNotNull(email);
        String filterGender = toUpperCaseIfNotNull(gender);

        return repository.findAllByFilters(filterName, filterEmail, filterGender, pageable);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    private String toUpperCaseIfNotNull(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    private String toLowerCaseIfNotNull(String value) {
        return value != null ? value.toLowerCase() : null;
    }
}
