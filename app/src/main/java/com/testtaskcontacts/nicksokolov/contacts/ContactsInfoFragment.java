package com.testtaskcontacts.nicksokolov.contacts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsInfoFragment extends Fragment {

    private String contactName, contactSurname, contactPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contact_info, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState){

 //       contactName=view.findViewById(R.id.contact_name);

    }
}
