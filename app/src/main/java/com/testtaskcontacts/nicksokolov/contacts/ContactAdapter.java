package com.testtaskcontacts.nicksokolov.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> implements Filterable {

    private List<ContactsInfo> contactsListFiltered;
    private List<ContactsInfo> contactsList;
    private Context context;
    private ItemClickListener itemClickListener;

    // Разобраться с фильтром!

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactsListFiltered = MainActivity.contactsList;
                } else {
                    List<ContactsInfo> filteredList = new ArrayList<>();
                    for (ContactsInfo current : contactsList) {
                        if (current.getName().toLowerCase().contains(charString.toLowerCase())
                                ||current.getSurname().toLowerCase().contains(charString.toLowerCase())) {
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

    public ContactAdapter(List<ContactsInfo> contactsList, Context context) {
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

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
                MainActivity.dataBase.deleteContact(contact);
                MainActivity.readDataBase();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }


}
