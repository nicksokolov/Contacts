package com.testtaskcontacts.nicksokolov.contacts;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
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

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    static SQLiteDataBase dataBase;

    static ArrayList<ContactsInfo> infos = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private LinearLayoutManager horizontallLayoutManager;
    static ContactAdapter contactsRecyclerAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        dataBase = new SQLiteDataBase(this);
       /* SQLiteDatabase db = dataBase.getWritableDatabase();
        dataBase.onUpgrade(db, 2, 3);
       */ getData();

    }

    public void setViews() {
        contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        verticalLayoutManager = new LinearLayoutManager(getApplicationContext());
        horizontallLayoutManager = new LinearLayoutManager(this);
        contactsRecyclerView.setLayoutManager(verticalLayoutManager);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerAdapter = new ContactAdapter(infos, this);

        contactsRecyclerView.setAdapter(contactsRecyclerAdapter);
        contactsRecyclerAdapter.setClickListener(this);

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

        if (cursor.moveToFirst()) {
            do {
                contact = new ContactsInfo();
                contact.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_NAME)));
                contact.setSurname(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_SURNAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_PHONE)));
                infos.add(contact);
            } while (cursor.moveToNext());
        }
        contactsRecyclerAdapter.notifyDataSetChanged();
/*         ContentValues contentValues = new ContentValues();
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

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS, null, contentValues);
       */  readDataBase();

    }

    @Override
    public void onClick(View view, int adapterPosition) {

        ContactsInfo current = infos.get(adapterPosition);
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

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public void onContactSelected(ContactsInfo contact) {
        Intent intent = new Intent(this, ContactInfoActivity.class);
        intent.putExtra("name", contact.getName());
        intent.putExtra("surname", contact.getSurname());
        intent.putExtra("phoneNumber", contact.getPhoneNumber());
        startActivity(intent);
    }

    public static void readDataBase() {
        SQLiteDatabase database = dataBase.getReadableDatabase();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("mLog", "In onQueryTextChange");
                contactsRecyclerAdapter.getFilter().filter(query);
                return true;
            }
        });
        return true;
    }


}

