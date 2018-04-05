package com.testtaskcontacts.nicksokolov.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> implements Filterable {

    private List<ContactsInfo> contactsListFiltered;
    private List<ContactsInfo> contactsList;
    static FragmentActivity context;
    private ItemClickListener itemClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactsListFiltered = RecyclerViewFragment.contactsList;
                } else {
                    List<ContactsInfo> filteredList = new ArrayList<>();
                    for (ContactsInfo current : contactsList) {
                        if (current.getName().toLowerCase().contains(charString.toLowerCase())
                                || current.getSurname().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(current);
                        }
                    }

                    contactsListFiltered = filteredList;
                }
                FilterResults results = new FilterResults();
                results.count = contactsListFiltered.size();
                results.values = contactsListFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactsList = (List<ContactsInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, surname, phone, delete;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.text_name);
            surname = (TextView) view.findViewById(R.id.text_surname);
            phone = (TextView) view.findViewById(R.id.phone_number);
            delete = (TextView) view.findViewById(R.id.delete);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onClick(v, getAdapterPosition());
        }

    }

    public ContactAdapter(List<ContactsInfo> contactsList, FragmentActivity context) {
        this.contactsListFiltered = contactsList;
        this.contactsList = contactsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_contact_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ContactsInfo contactsInfo = contactsList.get(position);
        holder.name.setText(contactsInfo.getName());
        holder.surname.setText(contactsInfo.getSurname());
        holder.phone.setText(contactsInfo.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = new InfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("contactName", contactsInfo.getName());
                bundle.putString("contactSurname", contactsInfo.getSurname());
                bundle.putString("contactPhone", contactsInfo.getPhoneNumber());
                Log.d("mLog", contactsInfo.getName() + contactsInfo.getSurname() + contactsInfo.getPhoneNumber());
                infoFragment.setArguments(bundle);
                context.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.place_holder, infoFragment, null)
                        .addToBackStack(null)
                        .commit();
                /*
                fragmentJump(contactsInfo);*/
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteOrNotContact(v, contactsInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    private void DeleteOrNotContact(View v, final ContactsInfo contact) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Удаление контакта");
        alertDialog.setMessage("Вы действительно хотите удалить контакт?");
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecyclerViewFragment.dataBase.deleteContact(contact);
                MainActivity.readDataBase();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }


}
