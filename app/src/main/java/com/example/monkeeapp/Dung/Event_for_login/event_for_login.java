package com.example.monkeeapp.Dung.Event_for_login;
import android.content.Context;
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

import androidx.core.content.ContextCompat;

import com.example.monkeeapp.R;

public class event_for_login {
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
                        password.setBackground(ContextCompat.getDrawable(context, background));
                        email.setBackground(ContextCompat.getDrawable(context, background));
                        password.setPadding(80, 0, 0, 0);
                        email.setPadding(80, 0, 0, 0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.dung_shape_button_danhnhap;
                        DangNhap.setBackground(ContextCompat.getDrawable(context, background2));
                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
    }

}
