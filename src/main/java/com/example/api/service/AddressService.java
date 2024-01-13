package com.example.api.service;

import com.example.api.client.ViaCepClient;
import com.example.api.client.response.AddressResponse;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.exception.NotFoundException;
import com.example.api.exception.ValidationException;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.web.rest.model.AddressRequest;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public static final String ZIP_CODE_MESSAGE = "Zip Code %s already registered for customer %s";

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

    public Customer addAddressToCustomer(Long customerId, AddressRequest addressRequest) {
        Customer customer = getCustomerById(customerId);

        String formattedZipCode = formatZipCode(addressRequest.getZipcode());
        validateAddressUniqueness(formattedZipCode, customerId);

        AddressResponse addressByZipcode = getAddressFromExternalService(addressRequest.getZipcode());
        Address address = createAddressFromResponse(addressByZipcode, customer);

        return saveAddressToCustomer(customer, address);
    }

    public void removeAddressFromCustomer(
            Long customerId,
            Long addressId
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId.toString()));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address", addressId.toString()));

        customer.removeAddress(address);
        customerRepository.save(customer);
    }

    private Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId.toString()));
    }

    private String formatZipCode(String zipCode) {
        return String.format("%s-%s", zipCode.substring(0, 5), zipCode.substring(5));
    }

    private void validateAddressUniqueness(String formattedZipCode, Long customerId) {
        addressRepository.findByZipCodeAndCustomerId(formattedZipCode, customerId)
                .ifPresent(existingAddress -> {
                    throw new ValidationException(String.format(ZIP_CODE_MESSAGE, formattedZipCode, customerId));
                });
    }

    private AddressResponse getAddressFromExternalService(String zipCode) {
        return viaCepClient.getJsonAddressByZipcode(zipCode)
                .orElseThrow(() -> new NotFoundException("Zip Code", zipCode));
    }

    private Address createAddressFromResponse(AddressResponse addressResponse, Customer customer) {
        return new Address(
                addressResponse.getLogradouro(),
                addressResponse.getLocalidade(),
                addressResponse.getUf(),
                addressResponse.getCep(),
                customer
        );
    }

    private Customer saveAddressToCustomer(Customer customer, Address address) {
        customer.addAddress(address);
        return customerRepository.save(customer);
    }
}
