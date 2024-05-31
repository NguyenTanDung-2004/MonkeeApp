package com.example.monkeeapp.Dung.custom_viewpager;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
import com.example.monkeeapp.Dung.event_for_quenmatkhau.event_for_quenmatkhau;
import com.example.monkeeapp.Dung.mail_handling.mail_handling;
import com.example.monkeeapp.R;
import com.example.monkeeapp.login;
import com.example.monkeeapp.quen_mat_khau;

import java.util.Random;

public class event_to_change_page {
    public static int flag = 1;
    public static String code = "";
    static int flag_email = 0;
    public static String new_pass = "";
    public static String email1 = "";
    public static void event_to_change_page(ViewPager obj, Button button){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        button.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background1));

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        button.setBackground(ContextCompat.getDrawable(quen_mat_khau.context, background2));
//                        flag++;
//                        obj.setCurrentItem(flag);
                        handle_button(button, obj);

                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }

    public static void event_for_changing_page_for_back(ViewPager obj, ImageView button){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (flag == 1){
                            Intent intent = new Intent(v.getContext(), login.class);
                            v.getContext().startActivity(intent);
                            break;
                        }
                        flag--;
                        obj.setCurrentItem(flag);
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public static void handle_button(Button button, ViewPager obj){
        if (button == email.button1){
            if (event_for_quenmatkhau.check_email(email.email) == 2){
                Random rd = new Random();
                int x1 = rd.nextInt(9 - 1 + 1) + 1;
                int x2 = rd.nextInt(9 + 1);
                int x3 = rd.nextInt(9 + 1);
                int x4 = rd.nextInt(9 + 1);
                code = x1 + "" + x2 + "" + x3 + "" + x4;
                mail_handling obj1 = new mail_handling(email.email.getText().toString(), "Mã đặt lại mật khẩu - MONKEEAPP", "Chào bạn, chúng tôi gửi cho bạn một mã gồm 4 chữ số để đổi mật khẩu: \n" + "Mã của bạn là: " + code);
                obj1.execute();
                flag++;
                obj.setCurrentItem(flag);
                email1 = email.email.getText().toString();
            }
        }
        if (button == com.example.monkeeapp.Dung.custom_viewpager.code.button1){
            if (event_for_quenmatkhau.check_otp(com.example.monkeeapp.Dung.custom_viewpager.code.otp, code) == 2){
                flag++;
                obj.setCurrentItem(flag);
            }
        }
        if (button == new_password.button1){
            if (event_for_quenmatkhau.check_new_pass(new_password.pass) == 1){
                new_pass = new_password.pass.getText().toString();
                flag++;
                obj.setCurrentItem(flag);
            }
        }
        if (button == confirm_new_password.button1){
        }
    }
}
