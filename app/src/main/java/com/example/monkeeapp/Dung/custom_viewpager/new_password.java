package com.example.monkeeapp.Dung.custom_viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
import com.example.monkeeapp.R;
import com.example.monkeeapp.quen_mat_khau;

public class new_password extends Fragment {
    static ViewPager obj;
    static Button button1;
    static EditText pass;
    public static void set_data_for_viewpager(ViewPager obj1){
        obj = obj1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dung_quenmatkhau_new_password, container, false);
        event_for_login.event_for_input(view.findViewById(R.id.input_new_password), this.getContext());
        pass = view.findViewById(R.id.input_new_password);
        event_for_login.event_for_eye(view.findViewById(R.id.hide), view.findViewById(R.id.input_new_password), this.getContext());
        Button button = view.findViewById(R.id.tieptuc);
        button1 = button;
        event_to_change_page.event_to_change_page(this.obj, button);
        //event_for_login.event_changing_color_for_button(button, quen_mat_khau.context);
        return view;
    }
}
