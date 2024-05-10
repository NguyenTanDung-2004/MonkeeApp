package com.example.monkeeapp.Dung.custom_viewpager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.monkeeapp.Database.Dung.SQL_QuenMatKhau.sql_quenmatkhau;
import com.example.monkeeapp.Dung.event_for_quenmatkhau.event_for_quenmatkhau;
import com.example.monkeeapp.R;
import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
import com.example.monkeeapp.*;
public class confirm_new_password extends Fragment {
    static ViewPager obj;
    static Button button1;
    static EditText confirm;
    public static void set_data_for_viewpager(ViewPager obj1){
        obj = obj1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dung_quenmatkhau_confirm, container, false);
        event_for_login.event_for_input(view.findViewById(R.id.input_new_password), this.getContext());
        event_for_login.event_for_eye(view.findViewById(R.id.hide), view.findViewById(R.id.input_new_password), this.getContext());
        Button button_continue = view.findViewById(R.id.tieptuc);
        button1 = button_continue;
        confirm = view.findViewById(R.id.input_new_password);
        quen_mat_khau.button_tieptuc_in_confirm = button_continue;
        button_continue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        button_continue.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background1));

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (event_for_quenmatkhau.check_confirm_pass(confirm_new_password.confirm) == 1){
                            int background2 = R.drawable.dung_shape_button_danhnhap;
                            button_continue.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background2));
                            Dialog dialog = new Dialog(quen_mat_khau.context);
                            dialog.setContentView(R.layout.dung_dialog_reset_password);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCancelable(false);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            Button button = dialog.findViewById(R.id.tieptuc);
                            button.setOnTouchListener(new View.OnTouchListener() {

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                            int background1 = R.drawable.dung_shape_button_danhnhap_click;
                                            button.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background1));

                                            break;
                                        case MotionEvent.ACTION_UP:
                                            int background2 = R.drawable.dung_shape_button_danhnhap;
                                            button.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background2));
                                            Intent intent = new Intent(view.getContext(), login.class);
                                            view.getContext().startActivity(intent);
                                            event_to_change_page.flag = 1;
                                            break;
                                    }
                                    return true;
                                }
                            });
                            sql_quenmatkhau.update_password(event_to_change_page.email1, event_to_change_page.new_pass);
                            dialog.show();
                            return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
                        }

                }
                return true;
            }
        });
        return view;
    }
}
