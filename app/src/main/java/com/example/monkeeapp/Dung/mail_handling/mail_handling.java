package com.example.monkeeapp.Dung.mail_handling;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mail_handling extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "SendEmailTask";
    String host = "smtp.gmail.com";
    String port = "587";
    String username = "tandungnguyen918@gmail.com";
    String password = "oese kloq vtgv mxii";
    private String to;
    private String subject;
    private String content;
    public mail_handling(String to, String subject, String content){
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        try {
            // Parse the recipient email address
            Message message = new MimeMessage(session);

            // Đặt thông tin người gửi (email A)
            message.setFrom(new InternetAddress(username));

            // Đặt thông tin người nhận (email B)


            // Gửi email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Đặt chủ đề của email
            message.setSubject(subject);

            // Đặt nội dung của email
            message.setText(content);
            Transport.send(message);
            return true; // Email sent successfully
        } catch (MessagingException e) {
            // Handle other messaging exceptions (e.g., connection failure, etc.)
            Log.e(TAG, "Failed to send email", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean isEmailSent) {

    }
}
