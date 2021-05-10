package com.example.vaccinespotter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Session {
    private static final int AGE_18 = 18;
    private static final int AGE_45 = 45;

    private static final String COVISHIELD = "COVISHIELD";
    private static final String COVAXIN ="COVAXIN";

    private int available_capacity;
    private String date;
    private int min_age_limit;
    private String vaccine;

    public int getAvailable_capacity() {
        return available_capacity;
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public String getDateString() {
        return date;
    }

    public int getMin_age_limit() {
        return min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public boolean isVaccineAvailable() {
        return available_capacity != 0;
    }

    public boolean minAgeLimitIs18() {
        return min_age_limit == AGE_18;
    }

    public boolean minAgeLimitIs45() {
        return min_age_limit == AGE_45;
    }

    public boolean isCovishield() {
        return vaccine.equals(COVISHIELD);
    }

    public boolean isCovaxin() {
        return vaccine.equals(COVAXIN);
    }
}
