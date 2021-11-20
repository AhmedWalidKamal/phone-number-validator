package com.example.phonenumbers.controller;

import com.example.phonenumbers.model.Country;
import com.example.phonenumbers.phone.PhoneRecord;
import com.example.phonenumbers.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public final class PhoneController {
    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("phone")
    public ResponseEntity<Page<PhoneRecord>> getPhoneRecords
        (@RequestParam(defaultValue = "0") int pageIdx,
         @RequestParam(defaultValue = "10") int pageSize,
         @RequestParam(name = "code", required = false) String countryCodeIfAny,
         @RequestParam(required = false) Boolean stateIfAny) {

        Page<PhoneRecord> customers = phoneService.getPhoneRecords(pageIdx, pageSize, countryCodeIfAny, stateIfAny);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("country")
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(phoneService.getCountries(), HttpStatus.OK);
    }
}
