package com.testtaskcontacts.nicksokolov.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    static SQLiteDataBase dataBase;

    static List<ContactsInfo> infos = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private LinearLayoutManager horizontallLayoutManager;
    private ContactAdapter contactsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        verticalLayoutManager = new LinearLayoutManager(getApplicationContext());
        horizontallLayoutManager = new LinearLayoutManager(this);
        contactsRecyclerView.setLayoutManager(verticalLayoutManager);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerAdapter = new ContactAdapter(infos, this);

        dataBase = new SQLiteDataBase(this);
        getData();
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

    public void getData() {

        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
//        for (int i = 0; i < sqLiteDatabase.)
            infos.add(new ContactsInfo("Nick", "Sokolov", "+38-095-20-30-590"));
        infos.add(new ContactsInfo("John", "Petruha", "+38-066-20-10-998"));
        infos.add(new ContactsInfo("Kolobok", "Eretic", "+38-068-20-01-101"));
        infos.add(new ContactsInfo("Ifrem", "Kingsman", "+38-098-19-00-590"));
        infos.add(new ContactsInfo("Nika", "Chocolate", "+38-095-20-32-590"));
        infos.add(new ContactsInfo("Alex", "Smith", "+38-095-20-30-312"));
        infos.add(new ContactsInfo("Michael", "Self", "+38-050-21-34-490"));
        infos.add(new ContactsInfo("Kirill", "Swings", "+38-065-11-65-520"));



        /*ContentValues contentValues=new ContentValues();
        contentValues.put(SQLiteDataBase.KEY_NAME,"Nick");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Sokolov");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-095-20-30-590");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"John");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Petruha");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-066-20-10-998");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"Kolobok");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Eretic");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-068-20-01-101");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);

        contentValues.put(SQLiteDataBase.KEY_NAME,"Ifrem");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Kingsman");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-098-19-00-590");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"Nika");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Chocolate");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-095-20-32-590");


        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"Alex");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Smith");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-095-20-30-312");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"Michael");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Serif");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-050-21-34-490");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
        contentValues.put(SQLiteDataBase.KEY_NAME,"Kirill");
        contentValues.put(SQLiteDataBase.KEY_SURNAME,"Swings");
        contentValues.put(SQLiteDataBase.KEY_PHONE,"+38-065-11-65-520");

        sqLiteDatabase.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);
*/
        SQLiteDataBase.showTable(sqLiteDatabase);

        contactsRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View view, int adapterPosition) {

        ContactsInfo current = infos.get(adapterPosition);
        Intent intent = new Intent(this, ContactInfoActivity.class);
        intent.putExtra("name", current.getName());
        intent.putExtra("surname", current.getSurname());
        intent.putExtra("phoneNumber", current.getPhoneNumber());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        readDataBase();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AddNewContact.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
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
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex)
                        + ", surname = " + cursor.getString(surnameIndex) + ", phone = " + cursor.getString(phoneIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
        database.close();
    }
}

