package com.example.phonenumbers.repo;

import com.example.phonenumbers.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>  {
    /**
     * Returns a page of the customers in the database, based on the given pagination information.
     */
    Page<Customer> findAll(Pageable pageable);

    /**
        Returns a list of all customers in the database.
     */
    List<Customer> findAll();

    Page<Customer> findByPhoneStartsWith(@Param("phone") String phone, Pageable pageable);

    List<Customer> findByPhoneStartsWith(@Param("phone") String phone);
}
