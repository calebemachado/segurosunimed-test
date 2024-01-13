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

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll(
            final String name,
            final String email,
            final String gender,
            final Integer page,
            final Integer limit
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, limit != null ? limit : 5);

        String filterName = toUpperCaseIfNotNull(name);
        String filterEmail = toLowerCaseIfNotNull(email);
        String filterGender = toUpperCaseIfNotNull(gender);

        return repository.findAllByFilters(filterName, filterEmail, filterGender, pageable);
    }

    public Optional<Customer> findById(final Long id) {
        return repository.findById(id);
    }

    private String toUpperCaseIfNotNull(final String value) {
        return value != null ? value.toUpperCase() : null;
    }

    private String toLowerCaseIfNotNull(final String value) {
        return value != null ? value.toLowerCase() : null;
    }
}
