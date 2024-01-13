package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAllByOrderByNameAsc(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Customer c " +
            "LEFT JOIN FETCH c.addresses a " +
            "WHERE (:name is null OR c.name LIKE %:name%) " +
            "AND (:email is null OR c.email LIKE %:email%) " +
            "AND (:gender is null OR c.gender LIKE %:gender%) " +
            "AND (:city is null OR a.city LIKE %:city%) " +
            "AND (:state is null OR a.state LIKE %:state%)")
    List<Customer> findAllByFilters(@Param("name") String name,
                                    @Param("email") String email,
                                    @Param("gender") String gender,
                                    @Param("city") String city,
                                    @Param("state") String state,
                                    Pageable pageable);

}
