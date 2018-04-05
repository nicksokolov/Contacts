package com.testtaskcontacts.nicksokolov.contacts;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private ImageView imgAdd;
    private SearchView searchView;
    static SQLiteDataBase dataBase;
    static int totalCount;
    static List<ContactsInfo> contactsList = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private LinearLayoutManager horizontallLayoutManager;
    static ContactAdapter contactsRecyclerAdapter;
    private View view;

    public RecyclerViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                view = inflater.inflate(R.layout.fragment_recyclerview_list, container, false);
            }
            else view = inflater.inflate(R.layout.activity_main,container,false);
            contactsRecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler_view);
            verticalLayoutManager = new LinearLayoutManager(getActivity());
            horizontallLayoutManager = new LinearLayoutManager(getActivity());
            contactsRecyclerView.setLayoutManager(verticalLayoutManager);
            contactsRecyclerView.setHasFixedSize(true);
            contactsRecyclerAdapter = new ContactAdapter(contactsList, getActivity());
            dataBase = new SQLiteDataBase(getActivity());
            contactsRecyclerView.setAdapter(contactsRecyclerAdapter);
            MainActivity.getData();
            setSearchView(view);
            addNewContactFragment(view);
        }
        return view;
    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    private void setSearchView(View view) {
        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("mLog", "In onQueryTextChange  Нажата клавиша = " + query);
                contactsRecyclerAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

/*
    static public void getData() {

        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

        String query = "SELECT * FROM " + SQLiteDataBase.TABLE_CONTACTS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        ContactsInfo contact;
        totalCount = 1;
        if (cursor.moveToFirst()) {
            do {
                contact = new ContactsInfo();
                contact.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_NAME)));
                contact.setSurname(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_SURNAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SQLiteDataBase.KEY_PHONE)));
                contact.setId(totalCount);
                contactsList.add(contact);
                Log.d("mLog", "Contact:  " + contact.getName());
                totalCount++;
            } while (cursor.moveToNext());
        }
        contactsRecyclerAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void addNewContactFragment(View view) {
        imgAdd = (ImageView) view.findViewById(R.id.addNewContactView);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewContactFragment addNewContactFragment;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    addNewContactFragment = new AddNewContactFragment();
                    if(getFragmentManager() != null);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.place_holder, addNewContactFragment, null)
                            .addToBackStack(null)
                            .commit();
                } else {
                    addNewContactFragment = new AddNewContactFragment();
                    if (getFragmentManager() != null) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.place_for_support_fragments, addNewContactFragment, null)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
    }

}