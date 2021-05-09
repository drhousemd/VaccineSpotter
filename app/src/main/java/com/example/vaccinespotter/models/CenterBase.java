package com.example.vaccinespotter.models;

public class CenterBase {
    protected String address;
    protected String block_name;
    protected int center_id;
    protected String district_name;
    protected String fee_type;
    protected String name;
    protected int pincode;

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
}
