package com.example.monkeeapp.Dung.custom_textview;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
    private static Typeface Baloo2_bold;
    private static Typeface Baloo2_regular;
    public static Typeface getBaloo2_bold(Context context) {
        if (Baloo2_bold == null){
            Baloo2_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Bold.ttf");
        }
        return Baloo2_bold;
    }
    public static Typeface getBaloo2_regular(Context context) {
        if (Baloo2_regular == null){
            Baloo2_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Baloo2-Regular.ttf");
        }
        return Baloo2_regular;
    }
}
