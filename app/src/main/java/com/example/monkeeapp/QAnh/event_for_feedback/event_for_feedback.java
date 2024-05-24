package com.example.monkeeapp.QAnh.event_for_feedback;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class event_for_feedback {
    public static boolean check_content_feedback (EditText content, Button btnSend){
        if(content.getText().toString().equals(""))
            return false;
        return true;
    }
    public static void sendEmail(Context context, String emailAddress, String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            context.startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Feedback của bạn chưa thể gửi được vì một vài sự cố của chúng tôi.", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean check_send_email(Context context, String emailAddress, String subject, String content)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            context.startActivity(Intent.createChooser(intent, "Send Email"));
            return true; // Thành công
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Sự đóng góp của bạn chưa thể gửi được vì một vài sự cố của chúng tôi.", Toast.LENGTH_SHORT).show();
            return false; // Không thành công
        }
    }
}
