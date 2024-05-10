package com.example.monkeeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monkeeapp.Database.Dung.SQL_DangKy.sql_dangky;
import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Dung.utils.encrypt_password;
import com.example.monkeeapp.Dung.event_for_dangky.event_for_dangky;
import java.sql.SQLException;

public class dung_dang_ky extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dung_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        event_for_input(this);
        event_for_button_dang_ky(this);
        event_for_eye(this);
        event_for_quay_lai_dang_nhap(this);
//        try {
//            Toast.makeText(this, encrypt_password.encryptToSHA1("nguyentandung"), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
    public void event_for_input(Context context){
        EditText email = findViewById(R.id.input_email);
        EditText password = findViewById(R.id.input_password);
        EditText confirm = findViewById(R.id.input_confirm_password);
        //EditText user_name = findViewById(R.id.input_user_name);
        event_for_login.event_for_input(email, context);
        event_for_login.event_for_input(password, context);
        event_for_login.event_for_input(confirm, context);
        //event_for_login.event_for_input(user_name, context);
    }
    public void event_for_button_dang_ky(Context context){
        Button dangky = findViewById(R.id.dangky);
        dangky.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        dangky.setBackground(ContextCompat.getDrawable(context, background1));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        dangky.setBackground(ContextCompat.getDrawable(context, background2));
                        event_for_dangky.check_email(findViewById(R.id.input_email));
                        event_for_dangky.check_pass_conf(findViewById(R.id.input_confirm_password), findViewById(R.id.input_password));
                        event_for_dangky.insert_data(findViewById(R.id.dangky), findViewById(R.id.input_email), findViewById(R.id.input_password));
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public void event_for_eye(Context context){
        EditText password = findViewById(R.id.input_password);
        EditText confirm = findViewById(R.id.input_confirm_password);
        ImageView password_img = findViewById(R.id.hide);
        ImageView confirm_img = findViewById(R.id.hide1);
        event_for_login.event_for_eye(password_img, password, context);
        event_for_login.event_for_eye(confirm_img, confirm, context);
    }
    public void event_for_quay_lai_dang_nhap(Context context){
        TextView dangkyngay = findViewById(R.id.dang_ky_ngay);
        dangkyngay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dangkyngay.setPaintFlags(dangkyngay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        dangkyngay.setPaintFlags(dangkyngay.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                        Intent intent = new Intent (context, login.class);
                        context.startActivity(intent);
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }

}