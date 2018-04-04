package com.testtaskcontacts.nicksokolov.contacts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.testtaskcontacts.nicksokolov.contacts.MainActivity.readDataBase;

public class RecyclerMainFragment extends Fragment {

    static SQLiteDataBase dataBase;
    static int i;
    static List<ContactsInfo> contactsList = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private LinearLayoutManager horizontallLayoutManager;
    static ContactAdapter contactsRecyclerAdapter;
    SearchView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TextView name = findViewById(R.id.contact_name);
        name.setText(contactName);
        TextView surname = findViewById(R.id.contact_surname);
        surname.setText(contactSurname);
        TextView phoneNumber = findViewById(R.id.contact_number);
        phoneNumber.setText(contactPhoneNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_main, container, false);
    }


    public void setViewsAndData() {
        contactsRecyclerView = (RecyclerView) this.findViewById(R.id.contacts_recycler_view);
        verticalLayoutManager = new LinearLayoutManager(getApplicationContext());
        horizontallLayoutManager = new LinearLayoutManager(this);
        contactsRecyclerView.setLayoutManager(verticalLayoutManager);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerAdapter = new ContactAdapter(contactsList, this);

        contactsRecyclerView.setAdapter(contactsRecyclerAdapter);
        contactsRecyclerAdapter.setClickListener(this);
        dataBase = new SQLiteDataBase(this);
        getData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contactsRecyclerView.setLayoutManager(horizontallLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            contactsRecyclerView.setLayoutManager(verticalLayoutManager);
        }
    }

    static public void getData() {

        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

        String query = "SELECT * FROM " + SQLiteDataBase.TABLE_CONTACTS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        ContactsInfo contact;
        i=1;
        if (cursor.moveToFirst()) {
            do {
                contact = new ContactsInfo();
                contact.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_NAME)));
                contact.setSurname(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_SURNAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_PHONE)));
                contact.setId(i);
                contactsList.add(contact);
                i++;
            } while (cursor.moveToNext());
        }
        contactsRecyclerAdapter.notifyDataSetChanged();
        /*
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteDataBase.KEY_NAME, "Nick");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Sokolov");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-095-20-30-590");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "John");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Petruha");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-066-20-10-998");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "Kolobok");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Eretic");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-068-20-01-101");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);

        contentValues.put(SQLiteDataBase.KEY_NAME, "Ifrem");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Kingsman");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-098-19-00-590");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "Nika");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Chocolate");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-095-20-32-590");


        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "Alex");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Smith");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-095-20-30-312");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "Michael");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Serif");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-050-21-34-490");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME, "Kirill");
        contentValues.put(SQLiteDataBase.KEY_SURNAME, "Swings");
        contentValues.put(SQLiteDataBase.KEY_PHONE, "+38-065-11-65-520");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);*/
        readDataBase();
    }

    @Override
    public void onClick(View view, int adapterPosition) {

        ContactsInfo current = contactsList.get(adapterPosition);
        onContactSelected(current);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        readDataBase();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AddNewContact.class);
            startActivityForResult(intent, 1);
        } else if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
