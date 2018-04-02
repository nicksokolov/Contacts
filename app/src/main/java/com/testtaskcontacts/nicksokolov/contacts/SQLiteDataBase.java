package com.testtaskcontacts.nicksokolov.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactsDB";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_PHONE = "phone";


    public SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key autoincrement," + KEY_NAME + " text,"
                + KEY_SURNAME + " text," + KEY_PHONE + " text" + "\n" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void saveNewContact(ContactsInfo contact,int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_SURNAME, contact.getSurname());
        contentValues.put(KEY_PHONE, contact.getPhoneNumber());
        db.insert(TABLE_CONTACTS, null, contentValues);
        db.close();
        MainActivity.contactsRecyclerAdapter.notifyItemInserted(id);
    }

    public void deleteContact(int pos){
        int id=pos+1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTS + " WHERE _id='" + id + "'");
        MainActivity.contactsRecyclerAdapter.notifyItemRemoved(id-1);
        MainActivity.readDataBase();
        db.close();
    }

}
