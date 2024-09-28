package com.example.welcome;

public class User {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String location;
    private String type;

    public User(String name, String phone, String email, String password, String location, String type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.location = location;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String gender) {
        this.type = type;
    }
}
