package com.testtaskcontacts.nicksokolov.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AddNewContact extends AppCompatActivity implements View.OnClickListener {

    EditText editName, editSurname, editPhoneNumber;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.readDataBase();
        setContentView(R.layout.activity_add_new_contact);
        btn = findViewById(R.id.accept_button);
        editName = findViewById(R.id.edit_name);
        editSurname = findViewById(R.id.edit_surname);
        editPhoneNumber = findViewById(R.id.edit_phone);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (editName.getText().toString().equals("")) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        } else if (editSurname.getText().toString().equals("")) {
            Toast.makeText(this, "Введите Фамилию", Toast.LENGTH_SHORT).show();
        } else if (editPhoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.infos.add(new ContactsInfo(editName.getText().toString(), editSurname.getText().toString(), editPhoneNumber.getText().toString()));
            SQLiteDatabase database=MainActivity.dataBase.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(SQLiteDataBase.KEY_NAME,editName.getText().toString());
            contentValues.put(SQLiteDataBase.KEY_SURNAME,editSurname.getText().toString());
            contentValues.put(SQLiteDataBase.KEY_PHONE,editPhoneNumber.getText().toString());
            database.insert(SQLiteDataBase.TABLE_CONTACTS,null,contentValues);

            MainActivity.readDataBase();
            finish();
        }
    }
}
