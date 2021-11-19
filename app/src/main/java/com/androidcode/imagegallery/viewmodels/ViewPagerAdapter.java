package com.androidcode.imagegallery.viewmodels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.androidcode.imagegallery.views.album;
import com.androidcode.imagegallery.views.picture;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //System.out.println("Position" + position);
        switch (position)
        {
            case 0 :
                return new picture();
            case 1 :
                return new album();
            default:
                return new picture();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
