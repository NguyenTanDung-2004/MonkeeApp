package com.example.monkeeapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.monkeeapp.Dung.custom_viewpager.*;
import com.example.monkeeapp.Dung.Event_for_login.event_for_login;
public class quen_mat_khau extends AppCompatActivity {

    public static Button button_tieptuc_in_confirm;
    public static Context context;
    public static ViewPager viewpager_obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quen_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        setup_for_viewpager();
        event_for_back();

    }
    public void setup_for_viewpager(){
        ViewPager obj = findViewById(R.id.viewpager);
        viewpager_obj = obj;
        viewpagerAdapter obj1 = new viewpagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        obj.setAdapter(obj1);
        obj.setCurrentItem(1);
        email.set_data_for_viewpager(obj);
        code.set_data_for_viewpager(obj);
        new_password.set_data_for_viewpager(obj);
        confirm_new_password.set_data_for_viewpager(obj);
        obj.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    event_to_change_page.flag = 1;
                }
                if (position == 2){
                    event_to_change_page.flag = 2;
                }
                if (position == 3){
                    event_to_change_page.flag = 3;
                }
                if (position == 4){
                    event_to_change_page.flag = 4;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void event_for_back(){
        ImageView obj = findViewById(R.id.back);
        event_to_change_page.event_for_changing_page_for_back(viewpager_obj, obj);
    }

}