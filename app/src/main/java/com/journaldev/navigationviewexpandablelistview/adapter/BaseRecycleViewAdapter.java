package com.journaldev.navigationviewexpandablelistview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.journaldev.navigationviewexpandablelistview.models.BaseItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public abstract class BaseRecycleViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<BaseItemModel> mItems;
    protected LayoutInflater mInflater;
    protected ItemClickListener mItemClickListener;

    protected BaseRecycleViewAdapter(Context context,
                                   ItemClickListener itemClickListener) {
        mInflater = LayoutInflater.from(context);
        mItemClickListener = itemClickListener;
        mItems = new ArrayList<>();
    }



    public void replace(List<BaseItemModel> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }
    public abstract  BaseItemModel getItem(int position);
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
