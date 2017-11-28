package com.edwinacubillos.agendaapp;

/**
 * Created by edwin on 27/11/17.
 */

public class Contactos {

    private String name,email,phone;
    private float lat, longit;

    public Contactos() {
    }

    public Contactos(String name, String email, String phone, float lat, float longit) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.longit = longit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLongit() {
        return longit;
    }

    public void setLongit(float longit) {
        this.longit = longit;
    }
}
