package com.testtaskcontacts.nicksokolov.contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewContactFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    EditText editName, editSurname, editPhoneNumber;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_contact_fragment, container, false);
        editName = (EditText) view.findViewById(R.id.edit_name);
        editSurname = (EditText) view.findViewById(R.id.edit_surname);
        editPhoneNumber = (EditText) view.findViewById(R.id.edit_phone);
        btn = (Button) view.findViewById(R.id.accept_button);
        btn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        if (editName.getText().toString().equals(""))
            Toast.makeText(MainActivity.context, "Введите имя", Toast.LENGTH_SHORT).show();
        else if (editSurname.getText().toString().equals("")) {
            Toast.makeText(MainActivity.context, "Введите Фамилию", Toast.LENGTH_SHORT).show();
        } else if (editPhoneNumber.getText().toString().equals("")) {
            Toast.makeText(MainActivity.context, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        } else {
            RecyclerViewFragment.contactsList.add(new ContactsInfo(editName.getText().toString(),
                    editSurname.getText().toString(), editPhoneNumber.getText().toString(), RecyclerViewFragment.totalCount++));
            RecyclerViewFragment.dataBase.saveNewContact(new ContactsInfo(editName.getText().toString(),
                    editSurname.getText().toString(), editPhoneNumber.getText().toString(), RecyclerViewFragment.totalCount)
                    , RecyclerViewFragment.totalCount - 1);

            MainActivity.readDataBase();
            RecyclerViewFragment fragment=new RecyclerViewFragment();
            getActivity().getSupportFragmentManager()
                    .popBackStack();

        }
    }
}
