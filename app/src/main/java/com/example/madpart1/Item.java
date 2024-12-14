package com.example.madpart1;

public class Item {

    String name;
    String date;
    int photo;

    public Item(String name, String date, int photo) {
        this.name = name;
        this.date = date;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

}
