package com.testtaskcontacts.nicksokolov.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    private static final int REQUEST_CODE = 1;
    private List<ContactsInfo> infos = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private ContactAdapter contactsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        verticalLayoutManager = new LinearLayoutManager(getApplicationContext());
        contactsRecyclerView.setLayoutManager(verticalLayoutManager);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerAdapter = new ContactAdapter(infos,this);
        getData();
        contactsRecyclerView.setAdapter(contactsRecyclerAdapter);

        contactsRecyclerAdapter.setClickListener(this);

    }


    public void getData() {
        infos.add(new ContactsInfo("Nick", "Sokolov","+38-095-20-30-590"));
        infos.add(new ContactsInfo("John", "Petruha","+38-066-20-10-998"));
        infos.add(new ContactsInfo("Kolobok", "Eretic","+38-068-20-01-101"));
        infos.add(new ContactsInfo("Ifrem", "Kingsman","+38-098-19-00-590"));
        infos.add(new ContactsInfo("Nika", "Chocolate","+38-095-20-32-590"));
        infos.add(new ContactsInfo("Alex", "Smith","+38-095-20-30-312"));
        infos.add(new ContactsInfo("Michael", "Self","+38-050-21-34-490"));
        infos.add(new ContactsInfo("Kirill", "Swings","+38-065-11-65-520"));
        contactsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int adapterPosition) {
        ContactsInfo current=infos.get(adapterPosition);
        Intent intent=new Intent(this,ContactInfoActivity.class);
        intent.putExtra("name",current.getName());
        intent.putExtra("surname",current.getSurname());
        intent.putExtra("phoneNumber",current.getPhoneNumber());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent intent = new Intent(this,AddNewContact.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }
}

