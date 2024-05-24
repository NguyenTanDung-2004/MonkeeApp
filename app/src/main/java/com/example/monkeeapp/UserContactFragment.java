package com.example.monkeeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.monkeeapp.Database.QAnh.sql_for_contact.sql_for_contact;
import com.example.monkeeapp.Database.connect_database;

import java.sql.Connection;

public class UserContactFragment extends Fragment {

    Connection conn;

    TextView address;
    TextView email;
    TextView phone;
    Button btnBack;

    public UserContactFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);

        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;
        btnBack=view.findViewById(R.id.buttonreturn_contact);

        address = view.findViewById(R.id.text_address);
        address.setText(sql_for_contact.get_address(conn));

        phone = view.findViewById(R.id.text_phone);
        phone.setText(sql_for_contact.get_phone(conn));

        email = view.findViewById(R.id.text_mail);
        email.setText(sql_for_contact.get_email(conn));

        //Event button back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
            }
        });

        //Event for phone
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        return view;
    }
}