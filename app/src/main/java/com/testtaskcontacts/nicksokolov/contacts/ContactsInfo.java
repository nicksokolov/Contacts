package com.testtaskcontacts.nicksokolov.contacts;

import android.view.View;

import java.util.ArrayList;

public class ContactsInfo {
    private String name;
    private String surname;
    private String phoneNumber;
    //private String delete;

    public ContactsInfo() {

    }

    public ContactsInfo(String name, String surname, String phoneNumber) {
        setName(name);
        setSurname(surname);
        setPhoneNumber(phoneNumber);
      //  setDelete(delete);
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

//    public String getDelete() {
//        return delete;
//    }

    //
//    public void setDelete(String delete) {
//        this.delete = delete;
//    }
}
