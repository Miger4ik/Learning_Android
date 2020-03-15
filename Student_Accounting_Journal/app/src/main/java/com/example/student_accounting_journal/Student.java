package com.example.student_accounting_journal;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private String surName;
    private String fatherName;
    private float rate;
    private Map<String, Float> rateBook;

    public Student() {}

    public Student(String name, String surName, String fatherName, float rate, Map<String, Float> rateBook) {
        this.name = name;
        this.surName = surName;
        this.fatherName = fatherName;
        this.rate = rate;
        this.rateBook = rateBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Map<String, Float> getRateBook() {
        return rateBook;
    }

    public void setRateBook(Map<String, Float> rateBook) {
        this.rateBook = rateBook;
    }
}
