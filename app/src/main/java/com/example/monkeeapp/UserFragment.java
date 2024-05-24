package com.example.monkeeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.monkeeapp.Database.QAnh.sql_for_setting.sql_for_setting;
import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.QAnh.utils.AndroidUtil.AndroidUtil;
import com.example.monkeeapp.QAnh.utils.SaveImg.SaveImg;
import com.example.monkeeapp.User.user;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.sql.Connection;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserFragment extends Fragment {
    ConstraintLayout account;
    ConstraintLayout feedback;
    ConstraintLayout contact;
    Button btnLogOut;
    Button btnChange;
    ImageView ava;
    ImageView edit;
    CardView avaframe;
    Connection conn;
    EditText name;
    ActivityResultLauncher<Intent> imgPickLauncher;
    Uri selectedImgUri;


    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgPickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImgUri = data.getData();
                            AndroidUtil.setAva(getContext(), selectedImgUri, ava);
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        account = view.findViewById(R.id.layout_account);
        feedback = view.findViewById(R.id.layout_feedback);
        contact = view.findViewById(R.id.layout_contact);
        btnLogOut = view.findViewById(R.id.btnLogout);
        btnChange = view.findViewById(R.id.button_Change);
        ava = view.findViewById(R.id.ava);
        edit = view.findViewById(R.id.imgEdit);
        name = view.findViewById(R.id.name_text);

        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;

        //event for constraintlayout
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserChangePasswordFragment();
                transitionFragment(fragment);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserFeedbackFragment();
                transitionFragment(fragment);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserContactFragment();
                transitionFragment(fragment);
            }
        });

        //Event for avatar
        ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UserFragment.this).crop().compress(512).maxResultSize(512, 512)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                imgPickLauncher.launch(intent);
                                return null;
                            }
                        });
            }
        });

        //Get ava & name
        name.setText(sql_for_setting.get_username(user.id_user, conn));

        String avaSQL = sql_for_setting.get_ava(user.id_user, conn);
        if (avaSQL != null && !avaSQL.isEmpty()) {
            // Assuming the URL is a base64 encoded string or similar, you may need to adjust the conversion.
            byte[] imageBytes = Base64.decode(avaSQL, Base64.DEFAULT);
            Bitmap avatarBitmap = SaveImg.Byte_to_Img(imageBytes, conn);
            if (avatarBitmap != null) {
                ava.setImageBitmap(avatarBitmap);
            } else {
                // Set a default or error image
                ava.setImageResource(R.drawable.ava);
            }
        }
        else {
            // Set a default or error image
            ava.setImageResource(R.drawable.ava);
        }

        //Event for button edit
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusable(true);
                name.setFocusableInTouchMode(true);
                name.setBackgroundResource(R.drawable.qa_edit_text_focused);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusable(true);
                name.setFocusableInTouchMode(true);
                name.setBackgroundResource(R.drawable.qa_edit_text_focused);
            }
        });

        //Convert new ava
        event_for_save(btnChange, this.getContext(), name, ava,conn);

        //Event log out
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Start_2_Activity.class);
                startActivity(intent);
            }
        });

        //Set up UI
        setupUI(view.findViewById(R.id.user), name);
        return view;
    }
    public void transitionFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
    }
    public void event_for_save(Button btn, Context context, EditText name, ImageView img, Connection conn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql_for_setting.update_info(user.id_user, name, img, conn);
                if(sql_for_setting.check_update(user.id_user, name, img, conn))
                {
                    Toast.makeText(context.getApplicationContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
                name.setFocusable(false);
            }
        });
    }
    public void setupUI(View view, EditText editText) {
        // Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    editText.setFocusable(false);
                    name.setFocusableInTouchMode(false);
                    name.setBackgroundResource(R.drawable.qa_edit_text_normal);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, editText);
            }
        }
    }
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            currentFocus.clearFocus();
        }
    }
}