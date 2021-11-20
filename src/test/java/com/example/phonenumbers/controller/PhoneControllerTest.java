package com.example.phonenumbers.controller;

import com.example.phonenumbers.PhoneNumbersApplication;
import com.example.phonenumbers.model.Country;
import com.example.phonenumbers.model.Customer;
import com.example.phonenumbers.repo.CountryRepo;
import com.example.phonenumbers.repo.CustomerRepo;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = PhoneNumbersApplication.class)
@AutoConfigureMockMvc
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CountryRepo countryRepo;

    @BeforeEach
    public void setUp(){
        mockMvc = webAppContextSetup(context).build();

        // Add supported countries
        countryRepo.save(new Country(1L, "Cameroon", "(237)", "\\(237\\)\\ ?[2368]\\d{7,8}$"));
        countryRepo.save(new Country(2L, "Ethiopia", "(251)", "\\(251\\)\\ ?[1-59]\\d{8}$"));
        countryRepo.save(new Country(3L, "Morocco", "(212)", "\\(212\\)\\ ?[5-9]\\d{8}$"));
        countryRepo.save(new Country(4L, "Mozambique", "(258)", "\\(258\\)\\ ?[28]\\d{7,8}$"));
        countryRepo.save(new Country(5L, "Uganda", "(256)", "\\(256\\)\\ ?\\d{9}$"));

        // Save some customer records to test against
        customerRepo.save(new Customer(1L, "LOUIS PARFAIT (Cameroon Valid)", "(237) 673122155"));
        customerRepo.save(new Customer(2L, "ZEKARIAS KEBEDE (Invalid Ethiopia)", "(251) 9119454961"));
        customerRepo.save(new Customer(3L, "Nada Sofie (Valid Morocco)", "(212) 654642448"));
        customerRepo.save(new Customer(4L, "Tanvi Sachdeva (Invalid Mozambique)", "(258) 84330678235"));
        customerRepo.save(new Customer(5L, "Daniel Makori (Valid Uganda)", "(256) 714660221"));
    }

    @Test
    public void testGetSupportedCountries() throws Exception {
        mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    public void testGetPhoneRecordsWithoutFiltering() throws Exception {
        mockMvc.perform(get("/phone?pageIdx=0&pageSize=5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value("5"))
            .andExpect(jsonPath("$.content", hasSize(5)))
            .andExpect(jsonPath("$.content[0].customerName").value("LOUIS PARFAIT (Cameroon Valid)"))
            .andExpect(jsonPath("$.content[0].phone").value("(237) 673122155"))
            .andExpect(jsonPath("$.content[0].countryCode").value("(237)"))
            .andExpect(jsonPath("$.content[0].countryNameIfAny").value("Cameroon"))
            .andExpect(jsonPath("$.content[0].valid").value("true"));
    }

    @Test
    public void testGetPhoneRecordsWithCountryCodeFiltering() throws Exception {
        mockMvc.perform(get("/phone?code=(256)"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value("1"))
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].customerName").value("Daniel Makori (Valid Uganda)"))
            .andExpect(jsonPath("$.content[0].phone").value("(256) 714660221"))
            .andExpect(jsonPath("$.content[0].countryCode").value("(256)"))
            .andExpect(jsonPath("$.content[0].countryNameIfAny").value("Uganda"))
            .andExpect(jsonPath("$.content[0].valid").value("true"));
    }

    @Test
    public void testGetPhoneRecordsWithStateFiltering() throws Exception {
        mockMvc.perform(get("/phone?stateIfAny=false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value("2"))
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].customerName").value("ZEKARIAS KEBEDE (Invalid Ethiopia)"))
            .andExpect(jsonPath("$.content[0].phone").value("(251) 9119454961"))
            .andExpect(jsonPath("$.content[0].countryCode").value("(251)"))
            .andExpect(jsonPath("$.content[0].countryNameIfAny").value(IsNull.nullValue()))
            .andExpect(jsonPath("$.content[0].valid").value("false"));
    }

    @Test
    public void testGetPhoneRecordsWithCountryCodeAndStateFiltering() throws Exception {
        mockMvc.perform(get("/phone?code=(212)&stateIfAny=true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value("1"))
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].customerName").value("Nada Sofie (Valid Morocco)"))
            .andExpect(jsonPath("$.content[0].phone").value("(212) 654642448"))
            .andExpect(jsonPath("$.content[0].countryCode").value("(212)"))
            .andExpect(jsonPath("$.content[0].countryNameIfAny").value("Morocco"))
            .andExpect(jsonPath("$.content[0].valid").value("true"));
    }
}
