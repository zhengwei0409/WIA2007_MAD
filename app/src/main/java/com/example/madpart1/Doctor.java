package com.example.madpart1;

public class Doctor {
    private String name;
    private String department;
    private String loginStatus;

    // Default constructor (required for Firestore)
    public Doctor() {}

    // Constructor with parameters
    public Doctor(String name, String department, String loginStatus) {
        this.name = name;
        this.department = department;
        this.loginStatus = loginStatus;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getLoginStatus() { return loginStatus; }
    public void setLoginStatus(String loginStatus) { this.loginStatus = loginStatus; }
}
