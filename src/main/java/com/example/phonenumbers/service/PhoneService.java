package com.example.phonenumbers.service;

import com.example.phonenumbers.phone.PhoneRecord;
import com.example.phonenumbers.model.Country;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PhoneService {
    Page<PhoneRecord> getPhoneRecords(int pageIdx, int pageSize, String countryCodeIfAny, Boolean stateIfAny);
    List<Country> getCountries();
}
