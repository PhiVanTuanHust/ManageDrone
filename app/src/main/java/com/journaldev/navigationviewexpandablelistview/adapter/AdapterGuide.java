package com.journaldev.navigationviewexpandablelistview.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.journaldev.navigationviewexpandablelistview.fragment.GuideFragment;
import com.journaldev.navigationviewexpandablelistview.utils.Const;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class AdapterGuide extends FragmentStatePagerAdapter {
    private Context context;

    public AdapterGuide(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }


    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return Const.getGuide(context).size();
    }
}
