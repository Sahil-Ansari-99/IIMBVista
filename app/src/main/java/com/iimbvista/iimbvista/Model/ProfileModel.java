package com.iimbvista.iimbvista.Model;

public class ProfileModel {
    String name;
    String college;
    String city;
    String vcap;
    String vista_id;
    String email;

    public ProfileModel(String name, String college, String city, String vcap, String vista_id, String email) {
        this.name = name;
        this.college = college;
        this.city = city;
        this.vcap = vcap;
        this.vista_id = vista_id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getCollege() {
        return college;
    }

    public String getCity() {
        return city;
    }

    public String getVcap() {
        return vcap;
    }

    public String getVista_id() {
        return vista_id;
    }

    public String getEmail() {
        return email;
    }
}
