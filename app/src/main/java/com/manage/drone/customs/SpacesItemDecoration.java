package com.manage.drone.customs;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.manage.drone.adapter.ConnectAdapter;
import com.manage.drone.models.BaseItemModel;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private boolean isHorizontalOffset = false;


    public SpacesItemDecoration(int space, boolean isHorizontalOffset) {
        this.space = space;
        this.isHorizontalOffset = isHorizontalOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (isHorizontalOffset) {
            outRect.left = space;
            outRect.right = space;
        }
        outRect.bottom = 0;
        outRect.top = space;


    }
}
