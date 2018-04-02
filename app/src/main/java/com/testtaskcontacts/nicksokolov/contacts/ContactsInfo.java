package com.testtaskcontacts.nicksokolov.contacts;

import android.view.View;

import java.util.ArrayList;

public class ContactsInfo {
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;

    public ContactsInfo() {

    }

    public ContactsInfo(String name, String surname, String phoneNumber, int id) {
        setId(id);
        setName(name);
        setSurname(surname);
        setPhoneNumber(phoneNumber);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
