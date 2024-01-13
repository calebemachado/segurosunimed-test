package com.example.api.repository;

import com.example.api.domain.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByZipCodeAndCustomerId(String zipcode, Long customerId);
}
