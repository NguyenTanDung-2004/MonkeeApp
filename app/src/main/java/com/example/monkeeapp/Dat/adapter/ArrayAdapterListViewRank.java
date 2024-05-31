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

import com.example.monkeeapp.Dat.util.StatisticItemDisplay;
import com.example.monkeeapp.R;

import java.util.List;

public class ArrayAdapterListViewRank extends ArrayAdapter<StatisticItemDisplay> {
    Activity context;
    private int idLayout;
    private final List<StatisticItemDisplay> list;

    public ArrayAdapterListViewRank(Activity context, int idLayout, List<StatisticItemDisplay> list) {
        super(context, idLayout, list);
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(idLayout, null);
        StatisticItemDisplay item = list.get(position);
        ImageView imageView = convertView.findViewById(R.id.image_view);
        imageView.setImageResource(item.getImage());
        TextView textView = convertView.findViewById(R.id.rank_txttype);
        textView.setText(item.getTitle());
        TextView textView1 = convertView.findViewById(R.id.rank_txtmoney);
        textView1.setText(String.valueOf(item.getMoney()));
        return convertView;
    }
}
