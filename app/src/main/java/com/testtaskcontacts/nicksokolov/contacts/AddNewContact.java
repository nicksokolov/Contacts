package com.testtaskcontacts.nicksokolov.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AddNewContact extends AppCompatActivity implements View.OnClickListener{

     EditText editName,editSurname,editPhoneNumber;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        /*btn=findViewById(R.id.accept_button);
        editName=findViewById(R.id.edit_name);
        editSurname=findViewById(R.id.edit_surname);
        editPhoneNumber=findViewById(R.id.edit_phone);
        */
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(editName.getText().toString().equals("")){
            Toast.makeText(this,"Введите имя",Toast.LENGTH_SHORT).show();}
            else if(editSurname.getText().toString().equals("")){
            Toast.makeText(this,"Введите Фамилию",Toast.LENGTH_SHORT).show();
        }else if(editPhoneNumber.getText().toString().equals("")){
            Toast.makeText(this,"Введите номер телефона", Toast.LENGTH_SHORT).show();
        }
       else {
            finish();
        }
    }

    @Override
    public void finish(){
        Intent returnIntent=new Intent();
        returnIntent.putExtra("editName",editName.getText());
        returnIntent.putExtra("editSurname",editSurname.getText());
        returnIntent.putExtra("editPhoneNumber",editPhoneNumber.getText());
        setResult(RESULT_OK,returnIntent);
        super.finish();
    }
}
