package com.example.monkeeapp.QAnh.event_change_to_login;

import android.content.Context;
import android.content.Intent;

import com.example.monkeeapp.login;

public class event_change_to_login {
    public static void event_change_to_login(Context context) {
        Intent intent = new Intent(context, login.class);
        context.startActivity(intent);
    }
}
