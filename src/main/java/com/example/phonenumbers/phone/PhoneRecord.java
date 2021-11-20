package com.example.phonenumbers.phone;

public final class PhoneRecord {
    private final String customerName;
    private final String phone;
    private final String countryCode;
    private final String countryNameIfAny;
    private final boolean valid;

    public PhoneRecord(String customerName, String phone, String countryCode,
                       String countryNameIfAny, boolean valid) {

        this.customerName = customerName;
        this.phone = phone;
        this.countryCode = countryCode;
        this.countryNameIfAny = countryNameIfAny;
        this.valid = valid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryNameIfAny() {
        return countryNameIfAny;
    }

    public boolean isValid() {
        return valid;
    }
}
