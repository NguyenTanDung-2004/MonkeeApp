package com.example.monkeeapp.QAnh.utils.SaveImg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;

public class SaveImg {
    public static byte[] ImageView_to_Byte(ImageView h) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) h.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] Img = byteArrayOutputStream.toByteArray();
        return Img;
    }
    public static Bitmap Byte_to_Img(byte[] byteImg, Connection conn){
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
        return  bitmap;
    }
}
