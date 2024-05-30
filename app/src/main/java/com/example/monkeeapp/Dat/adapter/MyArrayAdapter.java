package com.example.monkeeapp.Dat.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monkeeapp.Dat.util.Type;
import com.example.monkeeapp.R;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Type> {
    Activity context;
    int idLayout;
    ArrayList<Type> myList;
    int position = 21;
    public MyArrayAdapter(Activity context, int idLayout, ArrayList<Type> myList, int position) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
        this.position = position;
    }
    public MyArrayAdapter(Activity context, int idLayout, ArrayList<Type> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layout = context.getLayoutInflater();
        convertView = layout.inflate(idLayout, null);
        Type item = myList.get(position);
        ImageView imageView = convertView.findViewById(R.id.img_item);
        imageView.setImageResource(item.getImage());
        TextView textView = convertView.findViewById(R.id.txt_type);
        textView.setText(item.getType());
        if (position != 21) {
            if (position == this.position) {
                convertView.setBackgroundResource(R.drawable.dat_bg_light_pink);
            }
        }
        return convertView;
    }

    public void setSelectedPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}
