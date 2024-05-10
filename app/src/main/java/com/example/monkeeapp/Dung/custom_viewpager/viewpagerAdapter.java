package com.example.monkeeapp.Dung.custom_viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class viewpagerAdapter extends FragmentStatePagerAdapter {

    public viewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return new email();
        }
        if (position == 2){
            return new code();
        }
        if (position == 3){
            return new new_password();
        }
        if (position == 4){
            return new confirm_new_password();
        }
        return new successfully();
    }

    @Override
    public int getCount() {
        return 5;
    }
}
