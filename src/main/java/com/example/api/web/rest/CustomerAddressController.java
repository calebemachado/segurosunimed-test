package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.service.AddressService;
import com.example.api.web.rest.model.AddressRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class CustomerAddressController {
    private final AddressService addressService;

    public CustomerAddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Customer> addAddressToCustomer(
            @PathVariable Long customerId,
            @RequestBody AddressRequest addressRequest
    ) {
        Customer customer = addressService.addAddressToCustomer(customerId, addressRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @DeleteMapping("{addressId}/customer/{customerId}")
    public ResponseEntity<Void> removeAddressFromCustomer(
            @PathVariable Long customerId,
            @PathVariable Long addressId
    ) {
        addressService.removeAddressFromCustomer(customerId, addressId);
        return ResponseEntity.noContent().build();
    }
}
