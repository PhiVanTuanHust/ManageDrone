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
import com.journaldev.navigationviewexpandablelistview.models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.ViewHolder> {
    private List<QuestionModel> lstQuestion;
    private Context context;
    private BaseRecycleViewAdapter.ItemClickListener itemClickListener;

    public AdapterQuestion(Context context, BaseRecycleViewAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        lstQuestion = new ArrayList<>();
    }

    public void loadQuestion(List<QuestionModel> lstQuestion) {
        if (lstQuestion != null) {
            this.lstQuestion.clear();
            this.lstQuestion.addAll(lstQuestion);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_question, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        QuestionModel questionModel = lstQuestion.get(position);
        holder.tvQuestion.setText(questionModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return lstQuestion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvQuestion)
        TextView tvQuestion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
