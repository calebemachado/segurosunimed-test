package com.example.api.service;

import com.example.api.client.ViaCepClient;
import com.example.api.client.response.AddressResponse;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.exception.NotFoundException;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.web.rest.model.AddressRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressServiceTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final Long ADDRESS_ID = 2L;
    @InjectMocks
    private AddressService addressService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ViaCepClient viaCepClient;

    private static AddressResponse addressResponseStub() {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setCep("75110-000");
        addressResponse.setLogradouro("Rua Joao de barro Crispim");
        addressResponse.setComplemento("");
        addressResponse.setBairro("Jundiaí");
        addressResponse.setLocalidade("São Paulo");
        addressResponse.setUf("GO");
        addressResponse.setIbge("6201109");
        addressResponse.setGia("");
        addressResponse.setDdd("11");
        addressResponse.setSiafi("9121");
        return addressResponse;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAddressToCustomer() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setZipcode("12345678");

        Customer existingCustomer = new Customer();
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(existingCustomer));

        when(addressRepository.findByZipCodeAndCustomerId(any(), anyLong())).thenReturn(Optional.empty());

        AddressResponse addressResponse = addressResponseStub();

        when(viaCepClient.getJsonAddressByZipcode(addressRequest.getZipcode())).thenReturn(Optional.of(addressResponse));
        when(customerRepository.save(any())).thenReturn(customerWithAddress(existingCustomer, addressResponse));

        Customer resultCustomer = addressService.addAddressToCustomer(CUSTOMER_ID, addressRequest);

        assertNotNull(resultCustomer);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testRemoveAddressFromCustomer() {
        Customer existingCustomer = new Customer();
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(existingCustomer));

        Address existingAddress = new Address();
        when(addressRepository.findById(ADDRESS_ID)).thenReturn(Optional.of(existingAddress));

        assertDoesNotThrow(() -> addressService.removeAddressFromCustomer(CUSTOMER_ID, ADDRESS_ID));

        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testRemoveAddressFromCustomer_CustomerNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> addressService.removeAddressFromCustomer(CUSTOMER_ID, ADDRESS_ID));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testRemoveAddressFromCustomer_AddressNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(new Customer()));
        when(addressRepository.findById(ADDRESS_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> addressService.removeAddressFromCustomer(CUSTOMER_ID, ADDRESS_ID));
        verify(customerRepository, never()).save(any());
    }

    private Customer customerWithAddress(Customer existingCustomer, AddressResponse addressResponse) {
        existingCustomer.addAddress(new Address(
                addressResponse.getLogradouro(),
                addressResponse.getLocalidade(),
                addressResponse.getUf(),
                addressResponse.getCep(),
                existingCustomer
        ));

        return existingCustomer;
    }
}
