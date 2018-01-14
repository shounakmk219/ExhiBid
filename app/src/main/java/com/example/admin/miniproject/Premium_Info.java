package com.example.admin.miniproject;

/**
 * Created by ADMIN on 29-09-2017.
 */

public class Premium_Info {

    String adhaar,address;
    public Premium_Info() {
    }


    public Premium_Info(String address,String adhaar) {
        this.address=address;
        this.adhaar=adhaar;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public String getAddress() {
        return address;
    }

    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
