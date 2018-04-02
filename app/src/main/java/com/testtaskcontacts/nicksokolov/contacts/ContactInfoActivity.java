package com.testtaskcontacts.nicksokolov.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity implements ItemClickListener {
    private String contactName, contactSurname, contactPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("surname")) {
            contactName = getIntent().getStringExtra("name");
            contactSurname = getIntent().getStringExtra("surname");
            contactPhoneNumber = getIntent().getStringExtra("phoneNumber");
            setInfo(contactName,contactSurname,contactPhoneNumber);
        }
    }

    private void setInfo(String contactName, String contactSurname, String contactPhoneNumber) {
        TextView name = findViewById(R.id.contact_name);
        name.setText(contactName);
        TextView surname = findViewById(R.id.contact_surname);
        surname.setText(contactSurname);
        TextView phoneNumber = findViewById(R.id.contact_number);
        phoneNumber.setText(contactPhoneNumber);
    }

    @Override
    public void onClick(View view, int adapterPosition) {

    }
}
