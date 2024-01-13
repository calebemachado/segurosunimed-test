package com.example.api.service.validation;

import com.example.api.exception.ValidationException;
import com.example.api.web.rest.model.CustomerRequest;

public class CustomerValidator {
    public static void validateCustomerRequest(CustomerRequest customerRequest) {
        if (customerRequest == null || customerRequest.getName() == null || customerRequest.getName().isEmpty()
                || customerRequest.getEmail() == null || !EmailValidator.isValidEmail(customerRequest.getEmail())
                || customerRequest.getGender() == null || !isValidGender(customerRequest.getGender())) {
            throw new ValidationException("Invalid customer data");
        }
    }

    private static boolean isValidGender(String gender) {
        return gender != null && (gender.equals("M") || gender.equals("F"));
    }
}
