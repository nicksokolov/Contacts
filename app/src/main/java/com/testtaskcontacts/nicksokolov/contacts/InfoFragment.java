package com.testtaskcontacts.nicksokolov.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoFragment extends Fragment {

    private TextView mName, mSurname, mPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);
        setViews(view);
        return view;

    }

    private void setViews(View view){
        mName=(TextView) view.findViewById(R.id.contact_name);
        mSurname=(TextView) view.findViewById(R.id.contact_surname);
        mPhone=(TextView) view.findViewById(R.id.contact_number);

        mName.setText(getArguments().getString("contactName"));
        mSurname.setText(getArguments().getString("contactSurname"));
        mPhone.setText(getArguments().getString("contactPhone"));

    }


}
