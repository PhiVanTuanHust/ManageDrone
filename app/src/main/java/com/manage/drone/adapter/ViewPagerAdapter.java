package com.manage.drone.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.manage.drone.fragment.BaseFragment;
import com.manage.drone.fragment.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
       return GalleryFragment.newInstance(position+3);
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Image";
            case 1:
                return "Video";
                default:
                    return null;
        }
    }
}
