package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.manage.drone.R;
import com.manage.drone.adapter.BaseRecycleViewAdapter;
import com.manage.drone.adapter.GalleryAdapter;
import com.manage.drone.models.BaseGalleryModel;
import com.manage.drone.models.BaseItemModel;
import com.manage.drone.models.ImageModel;
import com.manage.drone.models.VideoModel;
import com.manage.drone.view.ViewImageActivity;
import com.manage.drone.view.ViewVideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.manage.drone.models.BaseGalleryModel.TYPE_IMAGE;

/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public class GalleryFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener{
    @BindView(R.id.rcView)
    RecyclerView rcView;
    private GalleryAdapter adapter;
    public static GalleryFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        adapter=new GalleryAdapter(getActivity(),this,getArguments().getInt("type"));
        rcView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        if (getArguments().getInt("type")== TYPE_IMAGE){
             adapter.loadData(getListImage());
             rcView.setLayoutManager(new GridLayoutManager(rcView.getContext(),2));
        }else {
            adapter.loadData(getListVideo());
            rcView.setLayoutManager(new LinearLayoutManager(rcView.getContext()));
            rcView.addItemDecoration(new DividerItemDecoration(rcView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public void onItemClick(int position) {
        if (getArguments().getInt("type")==BaseGalleryModel.TYPE_IMAGE){
            ViewImageActivity.openImage(getActivity(),adapter.getLstGallery().get(position).getImgThumb());
        }else {
            ViewVideoActivity.startVideo(getActivity(),((VideoModel)adapter.getLstGallery().get(position)).getPathVideo());
        }
    }

    private List<BaseGalleryModel> getListImage(){
        List<BaseGalleryModel> lst=new ArrayList<>();
        lst.add(new ImageModel(R.drawable.main_bg,"14:15 17/12/2018",BaseGalleryModel.STATE_DONE));
        lst.add(new ImageModel(R.drawable.main_bg,"14:15 17/12/2018",BaseGalleryModel.STATE_UPLOAD));
        lst.add(new ImageModel(R.drawable.main_bg,"14:15 17/12/2018",BaseGalleryModel.STATE_UPLOAD));
        lst.add(new ImageModel(R.drawable.main_bg,"14:15 17/12/2018",BaseGalleryModel.STATE_UPLOAD));
        lst.add(new ImageModel(R.drawable.main_bg,"14:15 17/12/2018",BaseGalleryModel.STATE_UPLOAD));
        return lst;
    }
    private List<BaseGalleryModel> getListVideo(){
        List<BaseGalleryModel> lst=new ArrayList<>();
        lst.add(new VideoModel(R.drawable.main_bg,"00:19",BaseGalleryModel.STATE_UPLOAD,R.raw.video,"Video1"));
        lst.add(new VideoModel(R.drawable.main_bg,"00:16",BaseGalleryModel.STATE_UPLOAD,R.raw.video,"Video2"));
        lst.add(new VideoModel(R.drawable.main_bg,"01:25",BaseGalleryModel.STATE_DONE,R.raw.video,"Video3"));
        lst.add(new VideoModel(R.drawable.main_bg,"02:19",BaseGalleryModel.STATE_UPLOAD,R.raw.video,"Video4"));
        lst.add(new VideoModel(R.drawable.main_bg,"00:19",BaseGalleryModel.STATE_UPLOAD,R.raw.video,"Video5"));
        return lst;
    }
}
