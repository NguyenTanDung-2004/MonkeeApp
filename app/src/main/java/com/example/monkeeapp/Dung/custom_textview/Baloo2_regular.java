package com.example.monkeeapp.Dung.custom_textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class Baloo2_regular extends AppCompatTextView {

    public Baloo2_regular(@NonNull Context context) {
        super(context);
        set_fontsTextView();
    }

    public Baloo2_regular(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        set_fontsTextView();
    }

    public Baloo2_regular(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        set_fontsTextView();
    }

    private void set_fontsTextView() {
        Typeface typeface = Utils.getBaloo2_regular(getContext());
        setTypeface(typeface);
    }
}
