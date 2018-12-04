package com.manage.drone.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.DashBoard;
import com.manage.drone.utils.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private List<DashBoard> mItems;
    private onItemClick onItemClick;

    public DashboardAdapter() {
        mItems = Const.getDashBoard();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashBoard dashBoard = mItems.get(position);
        holder.icon.setImageResource(dashBoard.getIcon());
        holder.tvTitle.setText(dashBoard.getTitle());
        holder.tvDes.setText(dashBoard.getDes());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClick(DashboardAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDes)
        TextView tvDes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) onItemClick.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface onItemClick {
        void onItemClick(int position);
    }
}
