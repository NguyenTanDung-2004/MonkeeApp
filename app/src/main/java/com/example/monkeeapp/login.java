package com.example.monkeeapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monkeeapp.Database.Dung.SQL_DangKy.sql_dangky;
import com.example.monkeeapp.Database.Dung.SQL_DangNhap.sql_dangnhap;

import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
import com.example.monkeeapp.Dung.User.user;
import com.example.monkeeapp.Dung.utils.encrypt_password;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLException;
import java.util.HashMap;

public class login extends AppCompatActivity {

    RelativeLayout button_continue;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient googlesigninClient;
    int RC_google_signin = 20;
    public static String email_google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connect_database obj = new connect_database();
        obj.create_connect_database();
        event_for_input(this);
        event_for_eye(this);
        event_for_QuenMatKhau(this);
        event_for_continuegoogle(this);
        event_for_DangKyNgay(this);
        event_for_DangNhap(this);
        // setup cho continue google
        button_continue = findViewById(R.id.continue_google);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail().build();
        googlesigninClient = GoogleSignIn.getClient(this, gso);
        Context context = this;
        button_continue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int background = R.drawable.dung_shape_button_google_touch;
                        button_continue.setBackground(ContextCompat.getDrawable(context, background));
                        TextView obj = findViewById(R.id.text_continue);
                        obj.setText("Đang xử lí...");
                        auth.signOut();
                        googleSignin();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true; // Trả về true để xử lý sự kiện chạm hoàn chỉnh
            }
        });
//        if (auth.getCurrentUser() != null){
//            Intent intent = new Intent(MainActivity.this, secondActivity.class);
//            startActivity(intent);
//            finish();
//        }
        googlesigninClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void googleSignin() {
        Intent intent = googlesigninClient.getSignInIntent();
        startActivityForResult(intent, RC_google_signin);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_google_signin){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }
            catch(Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                user.email = e.toString();
                Intent intent = new Intent(login.this, Home.class);
                startActivity(intent);
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());
                            map.put("gamil", user.getEmail());
                            database.getReference().child("user").child(user.getUid()).setValue(map);
                            email_google = user.getEmail();
                            try {
                                if (sql_dangky.check_email_exist(email_google) == false){
                                    com.example.monkeeapp.User.user.id_user = sql_dangnhap.get_id_from_email(email_google);
                                }
                                else{
                                    sql_dangky.insert_user(email_google, user.getDisplayName(), encrypt_password.encryptToSHA1("123123asdfasdfasd@"));
                                    com.example.monkeeapp.User.user.id_user = sql_dangnhap.get_id_from_email(email_google);
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            TextView obj = findViewById(R.id.text_continue);
                            obj.setText("Đăng nhập bằng Google");
                        }
                        else{
                            Toast.makeText(login.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void event_for_input(Context context){
        EditText email = findViewById(R.id.input_email);
        EditText password = findViewById(R.id.input_password);
        event_for_login.event_for_input(email, context);
        event_for_login.event_for_input(password, context);
    }
    public void event_for_eye(Context context){
        ImageView obj = findViewById(R.id.hide);
        EditText password = findViewById(R.id.input_password);
        event_for_login.event_for_eye(obj, password,context);
    }
    public void event_for_QuenMatKhau(Context context){
        TextView QuenMatKhau = findViewById(R.id.QuenMatKhau);
        event_for_login.event_for_QuenMatKhau(QuenMatKhau, context);
    }
    public void event_for_continuegoogle(Context context){
        RelativeLayout obj = findViewById(R.id.continue_google);
        event_for_login.event_for_continue_google(obj, context);
    }
    public void event_for_DangKyNgay(Context context){
        TextView obj = findViewById(R.id.dang_ky_ngay);
        event_for_login.event_for_DangKyNgay(obj, context);
    }
    public void event_for_DangNhap(Context context){
        Button DangNhap = findViewById(R.id.DangNhap);
        EditText email = findViewById(R.id.input_email);
        EditText password = findViewById(R.id.input_password);
        event_for_login.event_for_DangNhap(DangNhap, email, password, context);
    }

}