package com.journaldev.navigationviewexpandablelistview.customs;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.journaldev.navigationviewexpandablelistview.adapter.ConnectAdapter;
import com.journaldev.navigationviewexpandablelistview.models.BaseItemModel;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class VerticalSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private boolean isHorizontalOffset = false;
    private ConnectAdapter adapter;

    public VerticalSpacesItemDecoration(int space, ConnectAdapter adapter) {
        this.space = space;
        this.adapter = adapter;
    }

    public VerticalSpacesItemDecoration(int space, boolean isHorizontalOffset) {
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

        int currentPos = parent.getChildLayoutPosition(view);

        int type = adapter.getItemViewType(currentPos);
        Log.e("getItemOffsets: ",currentPos +" " +type);
        // Add top margin only for the first item to avoid double space between items
        if (type == BaseItemModel.TYPE_HEADER && currentPos != 0) {
            outRect.top = space;

        } else {
            outRect.top = 0;
        }

    }
}
