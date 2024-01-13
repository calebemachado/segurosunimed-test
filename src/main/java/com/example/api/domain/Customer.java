package com.example.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @NotEmpty
    @Email
    private String email;

    @Column(nullable = false)
    @NotEmpty
    private String gender;

    public Customer() {
    }

    public Customer(String name, String email, String gender) {
        this.name = name.toUpperCase();
        this.email = email.toLowerCase();
        this.gender = gender.toUpperCase();
    }

    public Customer(Long id, String name, String email, String gender) {
        this.id = id;
        this.name = name.toUpperCase();
        this.email = email.toLowerCase();
        this.gender = gender.toUpperCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.toLowerCase() : null;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender != null ? gender.toUpperCase() : null;
    }

}
