package com.example.pixiefruit.Model;

public class User {
    public String email;
    public String name;
    public String phone;
    public String areaOwned;
    public String address;
    public String totalimages;
    public String testruns;


    public User() {
    }

    public User(String email, String name, String phone, String areaOwned, String address, String totalimages, String testruns) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.areaOwned = areaOwned;
        this.address=address;
        this.totalimages = totalimages;
        this.testruns = testruns;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAreaOwned(String areaOwned) {
        this.areaOwned = areaOwned;
    }

    public void setTotalimages(String totalimages) {
        this.totalimages = totalimages;
    }

    public void setTestruns(String testruns) {
        this.testruns = testruns;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAreaOwned() {
        return areaOwned;
    }

    public String getTotalimages() {
        return totalimages;
    }

    public String getTestruns() {
        return testruns;
    }
}
