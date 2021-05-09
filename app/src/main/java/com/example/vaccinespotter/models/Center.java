package com.example.vaccinespotter.models;

public class Center {

    private String address;

    private String block_name;

    private int center_id;

    private String district_name;

    private String fee_type;

    private String name;

    private int pincode;

    private Session[] sessions;

    public String getAddress() {
        return address;
    }

    public String getBlock_name() {
        return block_name;
    }

    public int getCenter_id() {
        return center_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getFee_type() {
        return fee_type;
    }

    public String getName() {
        return name;
    }

    public int getPincode() {
        return pincode;
    }

    public Session[] getSessions() {
        return sessions;
    }
}
