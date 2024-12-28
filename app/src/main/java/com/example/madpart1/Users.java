package com.example.madpart1;

import com.google.firebase.firestore.PropertyName;

public class Users {
    @PropertyName("Full name")
    private String name;
    private String phoneNo;
    private Boolean loginStatus;

    // Default constructor (required for Firestore)
    public Users() {}

    // Constructor with parameters
    public Users(String name, String phoneNo, Boolean loginStatus) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.loginStatus = loginStatus;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public Boolean getLoginStatus() { return loginStatus; }
    public void setLoginStatus(Boolean loginStatus) { this.loginStatus = loginStatus; }
}
