package com.example.pixiefruit.Model;

public class User {
    public String email;
    public String name;
    public String phone;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
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

    public User(String email, String name, String phone) {

        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
