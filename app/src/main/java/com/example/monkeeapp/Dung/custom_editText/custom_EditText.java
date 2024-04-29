package com.example.monkeeapp.Dung.custom_editText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class custom_EditText extends AppCompatEditText {

    public custom_EditText(@NonNull Context context) {
        super(context);
        set_font();
    }

    public custom_EditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        set_font();
    }

    public custom_EditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        set_font();
    }
    public void set_font(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Baloo2-Regular.ttf");

        // Thiết lập font cho CustomEditText
        setTypeface(typeface);
    }
}
