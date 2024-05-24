package com.example.monkeeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.monkeeapp.QAnh.custom_viewpage.EntertainAdapter;
import com.example.monkeeapp.QAnh.custom_viewpage.Entertainments;
import com.example.monkeeapp.QAnh.event_change_to_login.event_change_to_login;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class Start_2_Activity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private EntertainAdapter entertainAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start2);

        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);

        entertainAdapter = new EntertainAdapter(this, getListEntertain());
        viewPager.setAdapter(entertainAdapter);
        circleIndicator.setViewPager(viewPager);
        entertainAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        //Event transition for button Start
        Event_for_btnStart(this);
    }
    private List<Entertainments> getListEntertain() {
        List<Entertainments> list = new ArrayList<>();
        list.add(new Entertainments(R.layout.qa_text1));
        list.add(new Entertainments(R.layout.qa_text2));
        list.add(new Entertainments(R.layout.qa_text3));
        return list;
    }
    @SuppressLint("ClickableViewAccessibility")
    public void Event_for_btnStart(Context context){
        Button btnStart = (Button)findViewById(R.id.buttonStart);
        btnStart.setOnTouchListener((new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        int background1 = R.drawable.qa_btn_background_click;
                        btnStart.setBackground(ContextCompat.getDrawable(context, background1));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int background2 = R.drawable.qa_btn_background;
                        btnStart.setBackground(ContextCompat.getDrawable(context, background2));
                        event_change_to_login.event_change_to_login(Start_2_Activity.this);
                        break;
                }
                return false;
            }
        }));
    }
}