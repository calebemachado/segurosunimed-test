package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAllByOrderByNameAsc(Pageable pageable);

    @Query("SELECT c FROM Customer c " +
            "WHERE (:name is null OR c.name LIKE %:name%) " +
            "AND (:email is null OR c.email LIKE %:email%) " +
            "AND (:gender is null OR c.gender LIKE %:gender%)")
    List<Customer> findAllByFilters(@Param("name") String name,
                                    @Param("email") String email,
                                    @Param("gender") String gender,
                                    Pageable pageable);

}
