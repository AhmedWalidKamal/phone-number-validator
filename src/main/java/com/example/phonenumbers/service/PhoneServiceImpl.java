package com.example.phonenumbers.service;

import com.example.phonenumbers.model.Country;
import com.example.phonenumbers.model.Customer;
import com.example.phonenumbers.phone.PhoneRecord;
import com.example.phonenumbers.repo.CountryRepo;
import com.example.phonenumbers.repo.CustomerRepo;
import com.example.phonenumbers.util.PhoneRecordBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class PhoneServiceImpl implements PhoneService {

    private final CustomerRepo customerRepo;
    private final CountryRepo countryRepo;

    private final PhoneRecordBuilder phoneRecordBuilder;

    @Autowired
    public PhoneServiceImpl(CustomerRepo customerRepo, CountryRepo countryRepo,
                            PhoneRecordBuilder phoneRecordBuilder) {
        this.customerRepo = customerRepo;
        this.countryRepo = countryRepo;
        this.phoneRecordBuilder = phoneRecordBuilder;
    }

    /**
     * Returns a page of PhoneRecord objects, given the pagination information, and some filtering criteria.
     */
    public Page<PhoneRecord> getPhoneRecords(int pageIdx, int pageSize,
                                             String countryCodeIfAny, Boolean stateIfAny) {
        List<Country> supportedCountries = getCountries();
        List<PhoneRecord> phoneRecordList;

        if (countryCodeIfAny == null && stateIfAny == null) {
            // no filtering
            Page<Customer> customerPage = customerRepo.findAll(PageRequest.of(pageIdx, pageSize));
            phoneRecordList = phoneRecordBuilder.buildPhoneRecords
                (customerPage.getContent(), supportedCountries);
        }
        else if (stateIfAny != null && countryCodeIfAny == null) {
            // filter by state with all country codes
            List<Customer> customersList = customerRepo.findAll();
            phoneRecordList = phoneRecordBuilder.buildPhoneRecords
                (customersList, supportedCountries).stream()
                .filter(phoneRecord -> stateIfAny.equals(phoneRecord.isValid()))
                .collect(Collectors.toList());
        } else if (stateIfAny == null) {
            // filter by country code with all states
            Page<Customer> customerPage = customerRepo.findByPhoneStartsWith
                (countryCodeIfAny, PageRequest.of(pageIdx, pageSize));
            phoneRecordList = phoneRecordBuilder.buildPhoneRecords
                (customerPage.getContent(), supportedCountries);
        } else {
            // filter by both
            List<Customer> customerList = customerRepo.findByPhoneStartsWith(countryCodeIfAny);
            phoneRecordList = phoneRecordBuilder.buildPhoneRecords(customerList, supportedCountries)
                    .stream().filter(phoneRecord -> stateIfAny.equals(phoneRecord.isValid()))
                    .collect(Collectors.toList());
        }
        return getPageFromList(pageIdx, pageSize, phoneRecordList);
    }

    /**
     * Returns a list of the supported countries.
     */
    public List<Country> getCountries() {
        return countryRepo.findAll();
    }

    private Page<PhoneRecord> getPageFromList(int pageIdx, int pageSize, List<PhoneRecord> phoneRecordList) {
        int beginIdx = pageIdx * pageSize;
        List<PhoneRecord> phoneRecordsPageList = beginIdx >= phoneRecordList.size()
            ? Collections.emptyList()
            : phoneRecordList.subList(beginIdx, Math.min(phoneRecordList.size(), beginIdx + pageSize));
        return new PageImpl<>(phoneRecordsPageList, PageRequest.of(pageIdx, pageSize), phoneRecordList.size());
    }
}
