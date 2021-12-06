package com.example.phonenumbers.model;

import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table
public final class Customer {

    @Id
    private long id;
    private String name;
    private String phone;

    public Customer() {}

    public Customer(long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
