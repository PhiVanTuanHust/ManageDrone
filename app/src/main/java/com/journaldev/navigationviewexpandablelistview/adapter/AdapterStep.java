package com.journaldev.navigationviewexpandablelistview.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.journaldev.navigationviewexpandablelistview.fragment.ConnectFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.GuideFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.OperationFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.StartFragment;
import com.journaldev.navigationviewexpandablelistview.utils.Const;

import java.util.Locale;

/**
 * Created by Phí Văn Tuấn on 6/11/2018.
 */

public class AdapterStep extends FragmentStatePagerAdapter {
    private Context context;
    private ConnectFragment connectFragment;
    private OperationFragment operationFragment;
    private StartFragment startFragment;

    public AdapterStep(FragmentManager fm, Context context) {
        super(fm);
        connectFragment=ConnectFragment.newInstance();
        operationFragment=OperationFragment.newInstance();
        startFragment=StartFragment.newInstance(StartFragment.END);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return connectFragment;
            case 1:
                return operationFragment;
            case 2:
                return startFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }
}
