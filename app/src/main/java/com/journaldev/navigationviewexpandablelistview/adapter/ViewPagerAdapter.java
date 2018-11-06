package com.journaldev.navigationviewexpandablelistview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.journaldev.navigationviewexpandablelistview.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> lstBaseFragments;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        lstBaseFragments=new ArrayList<>();
    }

    public void setLstBaseFragments(List<BaseFragment> lstBaseFragments) {
        this.lstBaseFragments = lstBaseFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return lstBaseFragments.get(position);
    }

    @Override
    public int getCount() {
        return lstBaseFragments.size();
    }
}
