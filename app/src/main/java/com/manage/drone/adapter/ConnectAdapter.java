package com.manage.drone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.BaseItemModel;
import com.manage.drone.models.HeaderConnect;
import com.manage.drone.models.ItemConnect;
import com.manage.drone.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import static com.manage.drone.models.BaseItemModel.TYPE_HEADER;
import static com.manage.drone.models.BaseItemModel.TYPE_ITEM;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ConnectAdapter extends BaseRecycleViewAdapter {

    private List<BaseItemModel> clone = new ArrayList<>();
    private Context context;
    private List<BaseItemModel> connected;

    public ConnectAdapter(Context context, ItemClickListener itemClickListener) {
        super(context, itemClickListener);
        this.context = context;
        connected = new ArrayList<>();
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

    public void connectDrone(String key) {
        for (BaseItemModel baseItemModel : mItems) {
            if (baseItemModel.getTitle().toLowerCase().equals(key.toLowerCase())) {
                baseItemModel.setConnect(true);
                updateConnect();
                break;
            }
        }
    }

    public void updateConnect() {

        List<BaseItemModel> connecteds = getListConnect();
        List<BaseItemModel> notConnecteds = getListNotConnected();
        mItems.clear();
        if (!connecteds.isEmpty()) {
            mItems.add(new HeaderConnect("Đang kết nối"));
            mItems.addAll(connecteds);

        }
        if (!notConnecteds.isEmpty()) {
            mItems.add(new HeaderConnect("Có sẵn"));
            mItems.addAll(notConnecteds);

        }

        notifyDataSetChanged();

    }

    public List<BaseItemModel> getListConnect() {
        List<BaseItemModel> itemModels = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            BaseItemModel item = mItems.get(i);
            if (item.getType() == TYPE_ITEM) {
                if (item.isConnect())
                    itemModels.add(item);

            }
        }
        return itemModels;
    }

    public List<BaseItemModel> getListNotConnected() {
        List<BaseItemModel> itemModels = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            BaseItemModel item = mItems.get(i);
            if (item.getType() == TYPE_ITEM) {
                if (!item.isConnect())
                    itemModels.add(item);

            }
        }
        return itemModels;
    }

    public boolean checkConnected(String id) {
        for (int i = 0; i < mItems.size(); i++) {
            BaseItemModel baseItemModel = mItems.get(i);
            if (baseItemModel.getTitle().equals(id) && baseItemModel.isConnect()) return true;

        }
        return false;
    }

    public void remove(int positon) {
        if (positon < 0 || positon >= mItems.size()) return;
        mItems.remove(positon);
        this.clone.clear();
        this.clone.addAll(mItems);
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
        ImageView imgWifi;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvDrone);
            divider = itemView.findViewById(R.id.divider);
            imgWifi = itemView.findViewById(R.id.imgWifi);
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
