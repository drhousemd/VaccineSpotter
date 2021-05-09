package com.example.vaccinespotter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {

    private int available_capacity;

    private String date;

    private int min_age_limit;

    private String vaccine;

    public int getAvailable_capacity() {
        return available_capacity;
    }

    public Date getDate() throws ParseException {
        return  new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public int getMin_age_limit() {
        return min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }
}
