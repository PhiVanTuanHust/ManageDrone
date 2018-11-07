package com.manage.drone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.BaseItemModel;
import com.manage.drone.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import static com.manage.drone.models.BaseItemModel.TYPE_HEADER;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ConnectAdapter extends BaseRecycleViewAdapter {

    private List<BaseItemModel> clone = new ArrayList<>();
    private Context context;

    public ConnectAdapter(Context context, ItemClickListener itemClickListener) {
        super(context, itemClickListener);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
            return new HeaderViewHolder(mInflater.inflate(R.layout.item_header_connect, parent, false));
        return new ItemViewHolder(mInflater.inflate(R.layout.item_drone_connect, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (getItemViewType(position + 1) == TYPE_HEADER || getItemViewType(position + 1) == -1) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((ItemViewHolder) holder).divider.getLayoutParams();
                params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
                ((ItemViewHolder) holder).divider.setLayoutParams(params);
                if (getItemViewType(position + 1) == -1)
                    ((ItemViewHolder) holder).divider.setVisibility(View.INVISIBLE);
                else ((ItemViewHolder) holder).divider.setVisibility(View.VISIBLE);
            } else {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((ItemViewHolder) holder).divider.getLayoutParams();
                params.setMargins((int) ViewUtil.convertDpToPixel(20, context), 0, 0, 0); //substitute parameters for left, top, right, bottom
                ((ItemViewHolder) holder).divider.setLayoutParams(params);
                if (position == getItemCount() - 1)
                    ((ItemViewHolder) holder).divider.setVisibility(View.INVISIBLE);
                else ((ItemViewHolder) holder).divider.setVisibility(View.VISIBLE);
            }
            ((ItemViewHolder) holder).title.setText(mItems.get(position).getTitle());


        } else if (holder instanceof HeaderViewHolder) {
            if (position == 0) ((HeaderViewHolder) holder).divider.setVisibility(View.INVISIBLE);
            else ((HeaderViewHolder) holder).divider.setVisibility(View.VISIBLE);
            ((HeaderViewHolder) holder).title.setText(mItems.get(position).getTitle());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < 0 || position > mItems.size() - 1) return -1;
        return mItems.get(position).getType();
    }


    public void replace(List<BaseItemModel> items) {
        this.clone.clear();
        this.clone.addAll(items);
        super.replace(items);

    }

    @Override
    public BaseItemModel getItem(int position) {
        if (mItems != null && position >= 0 && position < mItems.size())
            return mItems.get(position);
        return null;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View divider;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvDrone);
            divider = itemView.findViewById(R.id.divider);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View divider;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvHeader);
            divider = itemView.findViewById(R.id.header_divider);
        }
    }

}
