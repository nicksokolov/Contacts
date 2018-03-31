package com.testtaskcontacts.nicksokolov.contacts;

import java.util.ArrayList;

class ContactsInfo {
    private String name;
    private String surname;
    private String phoneNumber;

    public ContactsInfo(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;

    }

    public String getSuranme() {
        return surname;

    }

    public String getPhoneNumber() {
        return phoneNumber;

    }

    public static ArrayList<ContactsInfo> getContactsInfo() {
      ArrayList<ContactsInfo> contactsInfos=new ArrayList<>();

      contactsInfos.add(new ContactsInfo("Nick","Sokolov","+38-095-20-30-590"));
      contactsInfos.add(new ContactsInfo("John","Petruha","+38-093-53-31-762"));
      contactsInfos.add(new ContactsInfo("Kolobok","Eretic","+38-068-99-10-491"));
      contactsInfos.add(new ContactsInfo("Ifrem","Kingsman","+38-066-01-06-804"));
      contactsInfos.add(new ContactsInfo("Nika","Chocolate","+38-063-92-15-101"));
      contactsInfos.add(new ContactsInfo("Michael","Self","+38-095-32-49-361"));
      contactsInfos.add(new ContactsInfo("Kirill","Swings","+38-098-34-68-189"));
      contactsInfos.add(new ContactsInfo("Alex","Smith","+38-066-67-32-001"));

      return contactsInfos;

    }

}
