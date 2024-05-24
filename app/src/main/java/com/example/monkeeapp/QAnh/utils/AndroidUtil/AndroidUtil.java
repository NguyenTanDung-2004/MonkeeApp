package com.example.monkeeapp.QAnh.utils.AndroidUtil;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class AndroidUtil {
    public static void setAva (Context context, Uri uri, ImageView img)
    {
        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(img);
    }
}
