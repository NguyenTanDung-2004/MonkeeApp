package com.example.monkeeapp.Dung.event_for_dangky;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.monkeeapp.Database.Dung.SQL_DangKy.sql_dangky;
import com.example.monkeeapp.Dung.custom_viewpager.event_to_change_page;
import com.example.monkeeapp.R;
import com.example.monkeeapp.login;
import com.example.monkeeapp.quen_mat_khau;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class event_for_dangky {
    static int flag = 0;
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
    public static int check_email_regular_expression(String email){
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == true){
            return 1;
        }
        return 0;
    }
    public static int check_email_sql(String email){
        try {
            if (sql_dangky.check_email_exist(email) == true){
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static int check_pass_conf_equal(EditText confirm, EditText password){
        if (confirm.getText().toString().equals(password.getText().toString())){
            return 1;
        }
        return 0;
    }




    public static void check_email(EditText email){
        int count = 0;
        if (check_null_input(email) == 1){
            count++;
        }
        if (check_email_regular_expression(String.valueOf(email.getText())) == 1){
            count++;
        }
        if (check_email_sql(String.valueOf(email.getText())) == 1){
            count++;
        }
        if (count < 3){
            set_error_for_input(email);
        }
        if (count == 3){
            flag++;
        }
    }
    public static void check_pass_conf(EditText password, EditText confirm){
        int count = check_null_input(password) + check_null_input(confirm) + check_pass_conf_equal(confirm, password);
        if (count == 3){
            flag++;
        }
        else{
            set_error_for_input(password);
            set_error_for_input(confirm);
        }
    }
    public static void insert_data(Button dangky, EditText email, EditText password) {
        dangky.setText("Đang xử lí...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (flag == 2) {
                    try {
                        Thread.sleep(1000); // Simulate a delay (1 second)
                        sql_dangky.insert_user(email.getText().toString(), "Tên của bạn", password.getText().toString());
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }

                    // Update UI on the main thread
                    dangky.post(new Runnable() {
                        @Override
                        public void run() {
                            // Update button text after database operation
                            dangky.setText("ĐĂNG KÝ");
                            flag = 0;

                            // Show success dialog on UI thread
                            showSuccessDialog(dangky.getContext());
                        }
                    });
                }
                else{
                    dangky.setText("ĐĂNG KÝ");
                    flag = 0;
                }
            }
        }).start();
    }

    private static void showSuccessDialog(Context context) {
        // Create and show the success dialog on the main (UI) thread
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dung_dialog_dangky_thanhcong);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button button = dialog.findViewById(R.id.tieptuc);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        button.setBackground(ContextCompat.getDrawable(context, background1));
                        break;
                    case MotionEvent.ACTION_UP:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        button.setBackground(ContextCompat.getDrawable(context, background2));
                        Intent intent = new Intent(context, login.class);
                        context.startActivity(intent);
                        event_to_change_page.flag = 1;
                        break;
                }
                return true;
            }
        });

        dialog.show();
    }
}
