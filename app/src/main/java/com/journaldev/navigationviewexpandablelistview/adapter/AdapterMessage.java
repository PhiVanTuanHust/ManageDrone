package com.journaldev.navigationviewexpandablelistview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.models.MessageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 1/11/2018.
 */

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder> {
    private List<MessageModel> lstMessage;
    private Context context;
    private BaseRecycleViewAdapter.ItemClickListener itemClickListener;

    public AdapterMessage(Context context,BaseRecycleViewAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener=itemClickListener;
        lstMessage = new ArrayList<>();
    }

    public void loadData(List<MessageModel> lstMessage) {
        if (lstMessage != null) {
            this.lstMessage.clear();
            this.lstMessage.addAll(lstMessage);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MessageModel message = lstMessage.get(position);
        if (message.getState()==MessageModel.NOT_SEEN){
            holder.txtCount.setVisibility(View.VISIBLE);
        }else {
            holder.txtCount.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
        holder.txtTime.setText(message.getDate());
        holder.txtDes.setText(message.getDescription());
        holder.txtFrom.setText(message.getFrom());
    }


    @Override
    public int getItemCount() {
        return lstMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDes)
        TextView txtDes;
        @BindView(R.id.tvCountMessage)
        TextView txtCount;
        @BindView(R.id.tvFrom)
        TextView txtFrom;
        @BindView(R.id.tvTime)
        TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
