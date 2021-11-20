package com.example.phonenumbers.util;

import com.example.phonenumbers.phone.PhoneRecord;
import com.example.phonenumbers.model.Country;
import com.example.phonenumbers.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A builder class used to build PhoneRecord objects using Customer and Country objects.
 */
@Component
public final class PhoneRecordBuilder {

    public List<PhoneRecord> buildPhoneRecords(List<Customer> customers, List<Country> countries) {
        Map<String, Country> countryMap = countries.stream().collect
            (Collectors.toMap(Country::getCode, Function.identity()));
        return customers.stream()
            .map(customer -> buildPhoneRecord(countryMap, customer))
            .collect(Collectors.toList());
    }

    private PhoneRecord buildPhoneRecord(Map<String, Country> countryMap, Customer customer) {
        String countryCode = customer.getPhone().substring(0, customer.getPhone().indexOf(")") + 1);
        if (countryMap.get(countryCode) == null
            || !customer.getPhone().matches(countryMap.get(countryCode).getRegex())) {
            return new PhoneRecord(customer.getName(), customer.getPhone(),
                                   countryCode, null, false);
        }
        return new PhoneRecord(customer.getName(), customer.getPhone(), countryCode,
                               countryMap.get(countryCode).getName(), true);
    }
}
