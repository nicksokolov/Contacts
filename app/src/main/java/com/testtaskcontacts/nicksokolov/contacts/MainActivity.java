package com.testtaskcontacts.nicksokolov.contacts;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerViewFragment fragment=new RecyclerViewFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.place_holder, fragment,null).commit();
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

