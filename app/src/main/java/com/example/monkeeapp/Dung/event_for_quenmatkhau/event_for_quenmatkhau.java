package com.example.monkeeapp.Dung.event_for_quenmatkhau;

import static com.example.monkeeapp.R.*;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.chaos.view.PinView;
import com.example.monkeeapp.Database.Dung.SQL_QuenMatKhau.sql_quenmatkhau;
import com.example.monkeeapp.Dung.custom_viewpager.event_to_change_page;
import com.example.monkeeapp.R;

import java.sql.SQLException;

public class event_for_quenmatkhau {
    public static void set_error_for_input(EditText obj){
        int background1 = R.drawable.dung_shape_input_error;
        obj.setBackground(ContextCompat.getDrawable(obj.getContext(), background1));
        obj.setPadding(80, 0, 0, 0);
    }
    public static int check_null_input(EditText obj) {
        if (obj.getText().toString().equals("")) {
            return 0;
        }
        return 1;
    }
    public static int check_email_exist(EditText email){
        try {
            if (sql_quenmatkhau.check_email_exist(email.getText().toString()) == true){
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static int check_email(EditText email){
        int count = check_email_exist(email) + check_null_input(email);
        if (count == 2){

        }
        else{
            set_error_for_input(email);
        }
        return count;
    }
    public static int check_code(PinView otp, String code){
        if (otp.getText().toString().equals(code)){
            return 1;
        }
        return 0;
    }
    public static int check_code_null(PinView otp){
        if (otp.getText().toString().equals("")){
            return 0;
        }
        return 1;
    }
    public static void set_error_for_otp(PinView otp){
        int color = ContextCompat.getColor(otp.getContext(), R.color.Dung_code_error);
        otp.setLineColor(color);
    }
    public static int check_otp(PinView otp, String code){
        int count = check_code(otp, code) + check_code_null(otp);
        if (count == 2){

        }
        else{
            set_error_for_otp(otp);
        }
        return count;
    }
    public static int check_new_pass(EditText pass){
        if (pass.getText().toString().equals("")){
            set_error_for_input(pass);
            return 0;
        }
        return 1;
    }
    public static int check_confirm_pass(EditText confirm){
        if (confirm.getText().toString().equals("")){
            set_error_for_input(confirm);
            return 0;
        }
        else{
            if (confirm.getText().toString().equals(event_to_change_page.new_pass)){
                return 1;
            }
            set_error_for_input(confirm);
            return 0;
        }
    }
}
