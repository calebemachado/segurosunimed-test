package com.example.api.service;

import com.example.api.client.ViaCepClient;
import com.example.api.client.response.AddressResponse;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.exception.NotFoundException;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.web.rest.model.AddressRequest;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ViaCepClient viaCepClient;

    public AddressService(
            CustomerRepository customerRepository,
            AddressRepository addressRepository,
            ViaCepClient viaCepClient
    ) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.viaCepClient = viaCepClient;
    }

    public void addAddressToCustomer(Long customerId, AddressRequest addressRequest) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId.toString()));

        AddressResponse addressByZipcode = viaCepClient.getJsonAddressByZipcode(addressRequest.getZipcode())
                .orElseThrow(() -> new NotFoundException("Zip Code", addressRequest.getZipcode()));

        Address address = new Address(
                addressByZipcode.getLogradouro(),
                addressByZipcode.getLocalidade(),
                addressByZipcode.getUf(),
                addressByZipcode.getCep(),
                customer
        );

        customer.addAddress(address);
        customerRepository.save(customer);
    }

    public void removeAddressFromCustomer(Long customerId, Long addressId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId.toString()));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address", addressId.toString()));

        customer.removeAddress(address);
        customerRepository.save(customer);
    }
}
