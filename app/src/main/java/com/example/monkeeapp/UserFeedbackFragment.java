package com.example.monkeeapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.monkeeapp.Database.QAnh.sql_for_contact.sql_for_contact;
import com.example.monkeeapp.Database.QAnh.sql_for_feedback.sql_for_feedback;
import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.QAnh.event_for_feedback.event_for_feedback;
import com.example.monkeeapp.User.user;

import java.sql.Connection;


public class UserFeedbackFragment extends Fragment {



    EditText content;
    Button btnSend;
    Button btnBack;
    Connection conn;


    public UserFeedbackFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_feedback, container, false);
        content = view.findViewById(R.id.content_feedback);
        btnBack = view.findViewById(R.id.buttonreturn_feedback);
        btnSend = view.findViewById(R.id.button_send_feedback);

        String emailString = "";
        String subject = "";

        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;

        emailString = sql_for_contact.get_email(conn);
        subject = user.id_user;

        //Event for button send
        btnSend.isEnabled();
        btnSend.setAlpha(0.5f);
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSendButtonState(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        event_for_button_send(btnSend, this.getContext(), emailString, subject, content, conn);

        //Event for button back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
            }
        });
        return view;
    }
    private void updateSendButtonState(boolean b) {
        btnSend.setEnabled(b);
        btnSend.setAlpha(1.0f);
        btnSend.setAlpha(b ? 1f : 0.5f);
    }

    public static void event_for_button_send(Button btn, Context context, String emailAddress, String subject, EditText content, Connection conn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event_for_feedback.check_content_feedback(content, btn) == true)
                {
                    if(sql_for_feedback.insertFeedback(user.id_user, content.getText().toString(),conn))
                    {
                        Toast.makeText(context, "Cảm ơn vì sự đóng góp của bạn.", Toast.LENGTH_SHORT).show();

                        content.getText().clear();
                    }

                }else {
                    Toast.makeText(context, "Sự đóng góp của bạn chưa thể gửi được vì một vài sự cố của chúng tôi.", Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
            }
        });
    }
}