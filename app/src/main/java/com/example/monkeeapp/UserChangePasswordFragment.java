package com.example.monkeeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.monkeeapp.Database.QAnh.sql_for_change_password.sql_for_change_password;
import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.QAnh.event_for_change_password.event_for_change_password;
import com.example.monkeeapp.User.user;

import java.sql.Connection;
import java.util.Objects;

public class UserChangePasswordFragment extends Fragment {

    Button btnSave;
    EditText currentPass;
    EditText newPass;
    ImageView eyeClose1;
    ImageView eyeClose2;
    EditText reType;
    Connection conn;

    Button btnBack;


    public UserChangePasswordFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_change_password, container, false);
        btnSave = view.findViewById(R.id.button6);
        btnBack = view.findViewById(R.id.buttonreturn_changePass);

        eyeClose1 = view.findViewById(R.id.eye1);
        eyeClose2 = view.findViewById(R.id.eye2);
        currentPass = view.findViewById(R.id.current_text);
        newPass = view.findViewById(R.id.new_text);
        reType = view.findViewById(R.id.retype_text);

        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;


        //event_eye_password
        eyeClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event_for_eye(eyeClose1, newPass, v);
            }
        });
        eyeClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event_for_eye(eyeClose2, reType, v);
            }
        });

        //Event for button save
        event_for_btnSave(btnSave, this.getContext(), currentPass, newPass, reType, conn);

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
    public void event_for_eye(ImageView eyeClose1, EditText newPass, View v) {
        if (eyeClose1.getBackground().getConstantState().equals(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.hide)).getConstantState())) {
            // Đổi background thành show
            eyeClose1.setBackgroundResource(R.drawable.view);
            // Hiển thị password dạng text
            newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Baloo2-Bold.ttf");
            newPass.setTypeface(typeface);
            // Đặt lại vị trí con trỏ về cuối
            newPass.setSelection(newPass.length());
        } else {
            // Đổi background thành hide
            eyeClose1.setBackgroundResource(R.drawable.hide);
            // Ẩn password
            newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Baloo2-Bold.ttf");
            newPass.setTypeface(typeface);
            // Đặt lại vị trí con trỏ về cuối
            newPass.setSelection(newPass.length());
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    public static void event_for_btnSave(Button btn, Context context, EditText current, EditText newpass, EditText retype, Connection conn)
    {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!event_for_change_password.check_current_pass(current, conn)) {
                    Toast.makeText(context.getApplicationContext(), "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
                } else if (!event_for_change_password.check_new_pass(newpass, retype)){
                    Toast.makeText(context.getApplicationContext(), "Mật khẩu mới không chính xác!", Toast.LENGTH_SHORT).show();
                } else {
                    sql_for_change_password.get_update_password(user.id_user, newpass.getText().toString(), conn);
                    if (sql_for_change_password.update_password(user.id_user, newpass.getText().toString(), conn)) {
                        Toast.makeText(context.getApplicationContext(), "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Không thể cập nhật được mật khẩu! Xin mời bạn kiểm tra lại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}