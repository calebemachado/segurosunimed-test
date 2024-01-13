package com.example.api.service.validation;

import com.example.api.exception.ValidationException;
import com.example.api.web.rest.model.CustomerRequest;

public class CustomerValidator {
    public static void validateCustomerRequest(CustomerRequest customerRequest) {
        if (customerRequest == null || customerRequest.getName() == null || customerRequest.getName().isEmpty()
                || customerRequest.getEmail() == null || EmailValidator.isInvalidEmail(customerRequest.getEmail())
                || customerRequest.getGender() == null || isInvalidValidGender(customerRequest.getGender())) {
            throw new ValidationException("Invalid customer data");
        }
    }

    public static void validateCustomerUpdateRequest(CustomerRequest customerRequest) {
        if (customerRequest == null
                || (customerRequest.getEmail() != null && EmailValidator.isInvalidEmail(customerRequest.getEmail()))
                || (customerRequest.getGender() != null && isInvalidValidGender(customerRequest.getGender()))
        ) {
            throw new ValidationException("Invalid customer data");
        }
    }

    private static boolean isInvalidValidGender(String gender) {
        return gender == null || (!gender.equals("M") && !gender.equals("F"));
    }
}
