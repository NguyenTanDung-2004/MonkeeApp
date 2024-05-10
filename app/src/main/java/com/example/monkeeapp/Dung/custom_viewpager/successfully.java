package com.example.monkeeapp.Dung.custom_viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monkeeapp.R;
import com.example.monkeeapp.Dung.*;
import com.example.monkeeapp.login;

public class successfully extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dung_quenmatkhau_successfull, container, false);
        Button button_back_login = view.findViewById(R.id.tieptuc);
        return view;
    }
}
