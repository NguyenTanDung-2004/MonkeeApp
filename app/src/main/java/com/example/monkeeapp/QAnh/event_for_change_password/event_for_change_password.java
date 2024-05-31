package com.example.monkeeapp.QAnh.event_for_change_password;

import android.widget.EditText;

import com.example.monkeeapp.Database.QAnh.sql_for_change_password.sql_for_change_password;
import com.example.monkeeapp.Dung.utils.encrypt_password;
import com.example.monkeeapp.User.user;

import java.sql.Connection;
import java.sql.SQLException;

public class event_for_change_password {

    public event_for_change_password() {

    }
    public static boolean check_current_pass(EditText current, Connection conn){
        String currentPass = "";
        try {
            currentPass = sql_for_change_password.get_password(user.id_user,conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!(current.getText().toString().equals(""))){
            String encrypt_pass = encrypt_password.encryptToSHA1(current.getText().toString());
            if(currentPass.equals(encrypt_pass))
                return true;
            return false;
        }
        return false;
    }
    public static boolean check_new_pass(EditText newPass, EditText reType){
        if(newPass.getText().toString().equals("") )
        {
            return false;
        }
        if(reType.getText().toString().equals(""))
        {
            return false;
        }
        if(!(newPass.getText().toString().equals(reType.getText().toString())))
        {
            return false;
        }
        return true;
    }
}
