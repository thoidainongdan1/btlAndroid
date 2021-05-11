package com.example.btl.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    public FragmentViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new NoteFragment();
            case 1: return new ScheduleFragment();
            default: return new NoteFragment();
        }
    }

    @Override
    public int getCount() {return 2;}

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
