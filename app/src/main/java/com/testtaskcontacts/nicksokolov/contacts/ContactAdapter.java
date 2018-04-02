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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<ContactsInfo> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = contactsList;
                } else {
                    for (ContactsInfo row : contactsList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = contactsListFiltered.size();
                filterResults.values = contactsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactsListFiltered = (List<ContactsInfo>) results.values;
                MainActivity.contactsRecyclerAdapter.notifyDataSetChanged();
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
                DeleteOrNotContact(v, position);
            }
        });
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void DeleteOrNotContact(View v, final int pos) {
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
                MainActivity.dataBase.deleteContact(pos);
                MainActivity.readDataBase();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
