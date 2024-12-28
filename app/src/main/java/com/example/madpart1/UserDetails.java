package com.example.madpart1;

import com.google.firebase.firestore.PropertyName;

public class UserDetails {
    @PropertyName("Full name")
    private String fullName;
    @PropertyName("Email")
    private String email;
    @PropertyName("Phone Number")
    private String phoneNumber;
    @PropertyName("Date of Birth")
    private String dateOfBirth;
    @PropertyName("Gender")
    private String gender;
    @PropertyName("Nationality")
    private String nationality;
    @PropertyName("UID")
    private String uid; // Add this if you want to handle UID explicitly
    @PropertyName("Password")
    private String password; // Add this if you want to map the password field

    // Default constructor required for Firestore
    public UserDetails() {
    }

    // Getters and Setters
    @PropertyName("Full name")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("Full name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @PropertyName("Email")
    public String getEmail() {
        return email;
    }

    @PropertyName("Email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("Phone Number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("Phone Number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @PropertyName("Date of Birth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @PropertyName("Date of Birth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @PropertyName("Gender")
    public String getGender() {
        return gender;
    }

    @PropertyName("Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @PropertyName("Nationality")
    public String getNationality() {
        return nationality;
    }

    @PropertyName("Nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @PropertyName("UID")
    public String getUid() {
        return uid;
    }

    @PropertyName("UID")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @PropertyName("Password")
    public String getPassword() {
        return password;
    }

    @PropertyName("Password")
    public void setPassword(String password) {
        this.password = password;
    }
}
