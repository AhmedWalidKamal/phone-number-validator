package com.example.phonenumbers.repo;

import com.example.phonenumbers.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Long> {

}
