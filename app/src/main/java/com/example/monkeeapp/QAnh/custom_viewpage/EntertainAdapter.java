package com.example.monkeeapp.QAnh.custom_viewpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.monkeeapp.R;

import java.util.List;

public class EntertainAdapter extends PagerAdapter {
    private Context context;
    private List<Entertainments> mListEntertains;

    public EntertainAdapter(Context context, List<Entertainments> mListEntertains) {
        this.context = context;
        this.mListEntertains = mListEntertains;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Entertainments> getmListEntertains() {
        return mListEntertains;
    }

    public void setmListEntertains(List<Entertainments> mListEntertains) {
        this.mListEntertains = mListEntertains;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the appropriate layout file based on position
        View contentView;
        switch (position) {
            case 0:
                contentView = inflater.inflate(R.layout.qa_text1, container, false);
                break;
            case 1:
                contentView = inflater.inflate(R.layout.qa_text2, container, false);
                break;
            case 2:
                contentView = inflater.inflate(R.layout.qa_text3, container, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }

        // Add the inflated view to the ViewPager
        container.addView(contentView);

        return contentView;
    }


    @Override
    public int getCount() {
        if(mListEntertains != null)
            return mListEntertains.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
