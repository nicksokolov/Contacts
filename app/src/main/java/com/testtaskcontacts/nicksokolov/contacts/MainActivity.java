package com.testtaskcontacts.nicksokolov.contacts;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity {
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerViewFragment fragment=new RecyclerViewFragment();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction().add(R.id.place_holder, fragment, null).commit();
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager().beginTransaction().add(R.id.place_for_support_fragments, fragment, null).commit();
        }
    }



    static public void getData() {

        SQLiteDatabase sqLiteDatabase = RecyclerViewFragment.dataBase.getWritableDatabase();

        String query = "SELECT * FROM " + SQLiteDataBase.TABLE_CONTACTS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        ContactsInfo contact;
        RecyclerViewFragment.totalCount = 1;
        if (cursor.moveToFirst()) {
            do {
                contact = new ContactsInfo();
                contact.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_NAME)));
                contact.setSurname(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_SURNAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_PHONE)));
                contact.setId(RecyclerViewFragment.totalCount);
                RecyclerViewFragment.contactsList.add(contact);
                Log.d("mLog", "Contact:  " + contact.getName());
                RecyclerViewFragment.totalCount++;
            } while (cursor.moveToNext());
        }
        RecyclerViewFragment.contactsRecyclerAdapter.notifyDataSetChanged();
    }

    public static void readDataBase() {
        SQLiteDatabase database = RecyclerViewFragment.dataBase.getReadableDatabase();
        Cursor cursor = database.query(SQLiteDataBase.TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(SQLiteDataBase.KEY_ID);
            int nameIndex = cursor.getColumnIndex(SQLiteDataBase.KEY_NAME);
            int surnameIndex = cursor.getColumnIndex(SQLiteDataBase.KEY_SURNAME);
            int phoneIndex = cursor.getColumnIndex(SQLiteDataBase.KEY_PHONE);
            do {
                Log.d("mLog", "\nID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex)
                        + ", surname = " + cursor.getString(surnameIndex) + ", phone = " + cursor.getString(phoneIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
        database.close();
    }
}

