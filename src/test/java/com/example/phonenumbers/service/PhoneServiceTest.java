package com.example.phonenumbers.service;


import com.example.phonenumbers.repo.CountryRepo;
import com.example.phonenumbers.repo.CustomerRepo;
import com.example.phonenumbers.util.PhoneRecordBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PhoneServiceTest {

    @InjectMocks
    private PhoneServiceImpl customerService;
    @Mock
    private CountryRepo countryRepo;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private PhoneRecordBuilder phoneRecordBuilder;

    @BeforeEach
    public void setUp() {

    }
}
