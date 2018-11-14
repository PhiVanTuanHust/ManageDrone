package com.manage.drone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.GroupModel;
import com.manage.drone.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 14/11/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context context;
    private List<GroupModel> lstProfile;

    public ProfileAdapter(Context context) {
        this.context = context;
        lstProfile = Const.getListProfile();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          GroupModel profile=lstProfile.get(position);
          if (profile!=null){
              holder.imgThumb.setImageResource(profile.getResDrawable());
              holder.tvTitle.setText(profile.getTitle());
          }
          holder.imgEdit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
    }


    @Override
    public int getItemCount() {
        return lstProfile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgThumb)
        ImageView imgThumb;
        @BindView(R.id.imgEdit)
        ImageView imgEdit;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
