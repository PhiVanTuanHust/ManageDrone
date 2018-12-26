package com.manage.drone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manage.drone.R;
import com.manage.drone.models.BaseGalleryModel;
import com.manage.drone.models.ImageModel;
import com.manage.drone.models.VideoModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.manage.drone.models.BaseGalleryModel.TYPE_IMAGE;


/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.BaseViewHolder>{
    private int type;
    private Context context;
    private LayoutInflater mInflater;
    private List<BaseGalleryModel> lstGallery;
    private BaseRecycleViewAdapter.ItemClickListener itemClickListener;
    public GalleryAdapter(Context context, BaseRecycleViewAdapter.ItemClickListener itemClickListener, int type) {
        this.context=context;
        this.type=type;
        this.itemClickListener=itemClickListener;
        lstGallery=new ArrayList<>();
        mInflater=LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == TYPE_IMAGE)
            return new GalleryAdapter.ImageViewHolder(mInflater.inflate(R.layout.item_image, parent, false));
        return new GalleryAdapter.VideoViewHolder(mInflater.inflate(R.layout.item_video, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        final BaseGalleryModel model=lstGallery.get(position);
        if (model.getState()==BaseGalleryModel.STATE_UPLOAD){
            holder.imgCheck.setImageResource(R.drawable.check);
        }else if (model.getState()==BaseGalleryModel.STATE_PREPARE){
            holder.imgCheck.setImageResource(R.drawable.checked);
        }else {
            holder.imgCheck.setVisibility(View.GONE);
        }
        if (type==TYPE_IMAGE){
            ImageModel imageModel= (ImageModel) lstGallery.get(position);
            Glide.with(context).load(imageModel.getImgThumb()).into(((ImageViewHolder)holder).imgView);
            ((ImageViewHolder)holder).tvTime.setText(imageModel.getTime());
        }else {
            VideoModel videoModel=(VideoModel)lstGallery.get(position);
            Uri videoURI = Uri.parse("android.resource://" + context.getPackageName() +"/"
                    +videoModel.getPathVideo());
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, videoURI);
            Bitmap bitmap = retriever
                    .getFrameAtTime(100000,MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);

            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
            Glide.with(context).load(stream.toByteArray())
                    .asBitmap().into(((VideoViewHolder)holder).imgThumb);
            ((VideoViewHolder)holder).tvTime.setText(videoModel.getTime());
            ((VideoViewHolder)holder).txtName.setText(videoModel.getName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
        holder.imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getState()==BaseGalleryModel.STATE_UPLOAD){
                    holder.imgCheck.setImageResource(R.drawable.checked);
                    model.setState(BaseGalleryModel.STATE_PREPARE);
                }else if (model.getState()==BaseGalleryModel.STATE_PREPARE){
                    model.setState(BaseGalleryModel.STATE_UPLOAD);
                    holder.imgCheck.setImageResource(R.drawable.check);
                }
            }
        });
    }

    public void loadData(List<BaseGalleryModel> lstGallery){
        if (lstGallery!=null&&lstGallery.size()>0){
            this.lstGallery.clear();
            this.lstGallery.addAll(lstGallery);
            notifyDataSetChanged();
        }
    }

    public List<BaseGalleryModel> getLstGallery() {
        return lstGallery;
    }

    @Override
    public int getItemCount() {
        return lstGallery.size();
    }

    public class ImageViewHolder extends BaseViewHolder{
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.imgView) ImageView imgView;

        public ImageViewHolder(View itemView) {
            super(itemView);

        }
    }
    public class VideoViewHolder extends BaseViewHolder{
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.imgThumb) ImageView imgThumb;
        @BindView(R.id.txtName) TextView txtName;

        public VideoViewHolder(View itemView) {
            super(itemView);

        }
    }
    public class BaseViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imgCheck) ImageView imgCheck;
        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
