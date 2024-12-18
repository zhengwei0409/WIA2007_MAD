package com.example.madpart1;

public class Appointment {
    private String doctorAssigned;
    private String date;
    private String time;

    // Default constructor (required for Firestore)
    public Appointment() {}

    public Appointment(String doctorAssigned, String date, String time) {
        this.doctorAssigned = doctorAssigned;
        this.date = date;
        this.time = time;
    }

    public String getDoctorAssigned() {
        return doctorAssigned;
    }

    public void setDoctorName(String doctorName) {
        this.doctorAssigned = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

