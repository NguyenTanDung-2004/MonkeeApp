package com.example.monkeeapp.Dung.Event_for_login;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.monkeeapp.Database.Dung.SQL_DangNhap.sql_dangnhap;
import com.example.monkeeapp.MainActivity;
import com.example.monkeeapp.R;
import com.example.monkeeapp.Dung.custom_viewpager.event_to_change_page;
import com.example.monkeeapp.Dung.*;
import com.example.monkeeapp.User.user;
import com.example.monkeeapp.dung_dang_ky;
import com.example.monkeeapp.quen_mat_khau;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class event_for_login {
    static int flag = 0;
    public static void event_for_input(EditText obj, Context context){
        obj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Khi EditText được focus (chỉnh sửa)
                    obj.setTextColor(ContextCompat.getColor(context, R.color.Dung_buttonColor));
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Bold.ttf");
                    obj.setTypeface(typeface);
                    int background_input = R.drawable.dung_shape_input_forcus;
                    obj.setBackground(ContextCompat.getDrawable(context, background_input));
                    obj.setPadding(80, 0, 0, 0);
                } else {
                    // Khi EditText không còn focus
                    obj.setTextColor(ContextCompat.getColor(context, R.color.black));
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Regular.ttf");
                    obj.setTypeface(typeface);
                    int background_input = R.drawable.dung_shape_input;
                    obj.setBackground(ContextCompat.getDrawable(context, background_input));
                    obj.setPadding(80, 0, 0, 0);
                }
            }
        });
    }
    public static void event_for_eye(ImageView obj, EditText obj1, Context context) {
        obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = obj.getDrawable();

                if (drawable != null && drawable.getConstantState() != null) {
                    if (drawable.getConstantState().equals(context.getResources().getDrawable(R.drawable.hide).getConstantState())) {
                        // Nếu hình ảnh hiện tại là hide, thực hiện các hành động khi nhấp vào
                        obj.setImageResource(R.drawable.view);
                        obj1.setInputType(InputType.TYPE_CLASS_TEXT); // Thiết lập inputType là văn bản
                        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Bold.ttf");
                        obj1.setTypeface(typeface);
                    } else {
                        // Nếu hình ảnh hiện tại không phải là hide (có thể là view), thực hiện các hành động khi nhấp vào
                        obj.setImageResource(R.drawable.hide);
                        obj1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // Thiết lập inputType là mật khẩu
                        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Bold.ttf");
                        obj1.setTypeface(typeface);
                    }
                }
            }
        });
    }

    public static void event_for_QuenMatKhau(TextView obj, Context context){
        obj.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        obj.setPaintFlags(obj.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        obj.setPaintFlags(obj.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                        Intent intent = new Intent(v.getContext(), quen_mat_khau.class);
                        v.getContext().startActivity(intent);
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public static void event_for_continue_google(RelativeLayout obj, Context context){
        obj.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background = R.drawable.dung_shape_button_google_touch;
                        obj.setBackground(ContextCompat.getDrawable(context, background));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background1 = R.drawable.dung_shape_button_google;
                        obj.setBackground(ContextCompat.getDrawable(context, background1));
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }

    public static void event_for_DangKyNgay(TextView obj, Context context){
        obj.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        obj.setPaintFlags(obj.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        obj.setPaintFlags(obj.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                        Intent intent = new Intent(v.getContext(), dung_dang_ky.class);
                        v.getContext().startActivity(intent);
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public static void event_for_DangNhap(Button DangNhap, EditText email, EditText password, Context context){
        DangNhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background = R.drawable.dung_shape_input_error;
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        DangNhap.setBackground(ContextCompat.getDrawable(context, background1));
//                        password.setBackground(ContextCompat.getDrawable(context, background));
//                        email.setBackground(ContextCompat.getDrawable(context, background));
//                        password.setPadding(80, 0, 0, 0);
//                        email.setPadding(80, 0, 0, 0);
                        DangNhap.setText("Đang xử lí...");
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        DangNhap.setBackground(ContextCompat.getDrawable(context, background2));
                        check_email(email);
                        check_password(email, password);
                        if (flag == 2){
                            try {
                                user.id_user = sql_dangnhap.get_id_from_email(email.getText().toString());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            //Toast.makeText(context, user.id_user, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        flag = 0;
                        DangNhap.setText("ĐĂNG NHẬP");
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public static void event_for_OTP(com.chaos.view.PinView obj, Context context){
        obj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Khi EditText được focus (chỉnh sửa)
                    obj.setTextColor(ContextCompat.getColor(context, R.color.Dung_buttonColor));
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Bold.ttf");
                    obj.setTypeface(typeface);
                    int background_input = R.drawable.dung_shape_input_forcus;
                    //obj.setBackground(ContextCompat.getDrawable(context, background_input));
                    //obj.setPadding(80, 0, 0, 0);
                } else {
                    // Khi EditText không còn focus
                    obj.setTextColor(ContextCompat.getColor(context, R.color.black));
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Regular.ttf");
                    obj.setTypeface(typeface);
                    int background_input = R.drawable.dung_shape_input;
                    //obj.setBackground(ContextCompat.getDrawable(context, background_input));
                    //obj.setPadding(80, 0, 0, 0);
                }
            }
        });
    }
    public static void event_changing_color_for_button(Button button, Context context){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.dung_shape_button_danhnhap_click;
                        button.setBackground(ContextCompat.getDrawable(context, background1));

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        button.setBackground(ContextCompat.getDrawable(context, background2));
                        event_to_change_page.event_to_change_page(quen_mat_khau.viewpager_obj, button);
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }
    public static int check_null(EditText obj){
        if (obj.getText().toString().equals("")){
            return 0;
        }
        return 1;
    }
    public static int check_email_exist(EditText email){
        try {
            if (sql_dangnhap.check_email_exist(email.getText().toString()) == true){
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
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
    public static int check_password_comparable_with_email(EditText password, EditText email){
        try {
            if (sql_dangnhap.check_password(password.getText().toString(), email.getText().toString())){
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static void set_error_for_input(EditText obj){
        int background1 = R.drawable.dung_shape_input_error;
        obj.setBackground(ContextCompat.getDrawable(obj.getContext(), background1));
        obj.setPadding(80, 0, 0, 0);
    }
    public static void check_email(EditText email){
        int count = check_null(email) + check_email_exist(email) + check_email_regular_expression(email.getText().toString());
        if (count == 3){
            flag++;
        }
        else{
            set_error_for_input(email);
        }
    }
    public static void check_password(EditText email, EditText password){
        int count = check_null(password) + check_password_comparable_with_email(password, email);
        if (count == 2){
            flag++;
        }
        else{
            set_error_for_input(password);
        }
    }
}
