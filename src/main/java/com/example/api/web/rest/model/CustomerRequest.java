package com.example.api.web.rest.model;

public class CustomerRequest {
    private String name;
    private String email;
    private String gender;

    public CustomerRequest(String name, String email, String gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
