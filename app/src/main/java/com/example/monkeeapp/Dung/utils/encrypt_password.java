package com.example.monkeeapp.Dung.utils;

import android.os.Build;

import java.security.MessageDigest;
import java.util.Base64;

public class encrypt_password {
    private static String add = "asdfasdfasd;@asdfasdfasd.?";

    public static String encryptToSHA1(String str) {
        String result = null;
        String s = str + add;
        try {
            byte[] data_to_byte = s.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(data_to_byte);
            byte[] digest = md.digest();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                result = Base64.getEncoder().encodeToString(digest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
