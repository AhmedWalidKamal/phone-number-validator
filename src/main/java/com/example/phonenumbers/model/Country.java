package com.example.phonenumbers.model;

import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table
public final class Country {
    @Id
    private long id;
    private String name;
    private String code;
    private String regex;

    public Country() {}

    public Country(long id, String name, String code, String regex) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.regex = regex;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getRegex() {
        return regex;
    }
}
