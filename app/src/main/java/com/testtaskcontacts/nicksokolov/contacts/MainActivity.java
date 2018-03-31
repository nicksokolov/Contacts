package com.testtaskcontacts.nicksokolov.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private RecyclerAdapter contactsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contactsRecyclerView.setLayoutManager(verticalLayoutManager);

        contactsRecyclerAdapter = new RecyclerAdapter();
        contactsRecyclerView.setAdapter(contactsRecyclerAdapter);
        contactsRecyclerAdapter.addInfos(ContactsInfo.getContactsInfo());

    }
}
