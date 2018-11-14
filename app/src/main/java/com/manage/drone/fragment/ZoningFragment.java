package com.manage.drone.fragment;

import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.googlemap.DrawingPanel;
import com.manage.drone.googlemap.MapFragment;
import com.manage.drone.models.MarkerModel;
import com.manage.drone.utils.Const;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ZoningFragment extends BaseFragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private DrawingPanel drawingpanel;
    private SupportMapFragment mapFragment;
    private ArrayList<LatLng> latLngs;
    private PolygonOptions polygonOptions;
    private Projection projection;
    private List<MarkerModel> lstMarkerModel;
    private List<Integer> lstPosition=new ArrayList<>();
    double maxDistanceFromCenter;
    private LatLng latLng = new LatLng(55.404290078521235, 89.46081262081861);
    @BindView(R.id.frame_view)
    FrameLayout flMapContainer;
    @BindView(R.id.imgSelected)
    ImageView imgDraw;
    @BindView(R.id.imgDelete)
    ImageView imgDelete;
    @BindView(R.id.imgDone)
    ImageView imgDone;
    private HashSet<List<LatLng>> set;
    private int position=0;
    private static final int WHAT_MARKER=0;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MARKER) {
                animateMarker();
            }
            super.handleMessage(msg);
        }
    };

    public static ZoningFragment newInstance() {

        Bundle args = new Bundle();
        ZoningFragment fragment = new ZoningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.zoning_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap != null)
            mMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    protected void initData() {
        drawingpanel = new DrawingPanel(getActivity());
        drawingpanel.setVisibility(View.GONE);
        drawingpanel.setBackgroundColor(Color.parseColor("#50000000"));
        flMapContainer.addView(drawingpanel);
        drawingpanel.setOnDragListener(new DrawingPanel.OnDragListener() {
            @Override
            public void onDragEvent(MotionEvent motionEvent) {

                //Track touch point
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                int x_co = Integer.parseInt(String.valueOf(Math.round(x)));
                int y_co = Integer.parseInt(String.valueOf(Math.round(y)));

                //get Projection from google map
                projection = mMap.getProjection();
                Point x_y_points = new Point(x_co, y_co);
                LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);

                if (latLngs == null)
                    latLngs = new ArrayList<LatLng>();
                latLngs.add(latLng);

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {



                    polygonOptions.strokeWidth(10);
                    polygonOptions.strokeColor(getResources().getColor(R.color.colorRed));
                    polygonOptions.addAll(latLngs);
                    mMap.addPolygon(polygonOptions);

                    drawingpanel.setVisibility(View.GONE);


                    //find radius and center from drawing
                    LatLng latLng1 = latLng;
                    LatLng latLngxmin = projection.fromScreenLocation(drawingpanel.getPointXMin());
                    LatLng latLngxmax = projection.fromScreenLocation(drawingpanel.getPointxMax());
                    LatLng latLngymin = projection.fromScreenLocation(drawingpanel.getPointYmin());
                    LatLng latLngymax = projection.fromScreenLocation(drawingpanel.getPointYmax());


                    if (drawingpanel.getPointXMin().x != 0 && drawingpanel.getPointXMin().y != 0)
                        maxDistanceFromCenter = distance(latLng1.latitude, latLng1.longitude, latLngxmin.latitude, latLngxmin.longitude);


                    double tempdistance = 0;
                    if (drawingpanel.getPointxMax().x != 0 && drawingpanel.getPointxMax().y != 0)
                        tempdistance = distance(latLng1.latitude, latLng1.longitude, latLngxmax.latitude, latLngxmax.longitude);
                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;

                    if (drawingpanel.getPointYmin().x != 0 && drawingpanel.getPointYmin().y != 0)
                        tempdistance = distance(latLng1.latitude, latLng1.longitude, latLngymin.latitude, latLngymin.longitude);
                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;

                    if (drawingpanel.getPointYmax().x != 0 && drawingpanel.getPointYmax().y != 0)
                        tempdistance = distance(latLng1.latitude, latLng1.longitude, latLngymax.latitude, latLngymax.longitude);

                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;


                    drawingpanel.clear();
                    if (!Const.lstPolygonOptions.contains(polygonOptions)){
                        Const.lstPolygonOptions.add(polygonOptions);
                    }
                    drawingpanel.setVisibility(View.GONE);
                    imgDone.setVisibility(View.VISIBLE);
                    imgDelete.setVisibility(View.VISIBLE);
                    imgDraw.setVisibility(View.GONE);

                }

            }
        });


    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(this);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);

        ((MapFragment) mapFragment).setListener(new MapFragment.OnTouchListener() {
            @Override
            public void onTouch() {

                YoYo.with(Techniques.FadeIn).playOn(imgDraw);

                setFadeOutAfterSomeTime();

            }
        });

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to location user
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        addMarkerToMap();
        animateMarker();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @OnClick(R.id.imgSelected)
    public void onDraw() {

        drawingpanel.setVisibility(View.VISIBLE);
        drawingpanel.clear();
        polygonOptions = new PolygonOptions();
        latLngs=new ArrayList<>();

    }
    @OnClick(R.id.imgDelete)
    public void onDelete() {
        mMap.clear();
        addMarkerToMap();
        animateMarker();
        Const.lstPolygonOptions=new ArrayList<>();
        drawingpanel.clear();
        imgDraw.setVisibility(View.VISIBLE);
        imgDone.setVisibility(View.GONE);
        imgDelete.setVisibility(View.GONE);
        polygonOptions = new PolygonOptions();
        latLngs=new ArrayList<>();

    }
    @OnClick(R.id.imgDone)
    public void onDone() {
        MainActivity mainActivity=(MainActivity)getActivity();
        if (mainActivity!=null){
            mainActivity.switchFragment(ObserveFragment.newInstance());
        }

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        Location selected_location = new Location("locationA");
        selected_location.setLatitude(lat1);
        selected_location.setLongitude(lon1);
        Location near_locations = new Location("locationA");
        near_locations.setLatitude(lat2);
        near_locations.setLongitude(lon2);

        double distance = selected_location.distanceTo(near_locations);
        return distance;
    }

    private void setFadeOutAfterSomeTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.FadeOut).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                    }

                }).playOn(imgDraw);
            }
        }, 10000);
    }

    private void animateMarker(){
        for (int i=0;i<lstMarkerModel.size();i++){
            int position=lstPosition.get(i);
            MarkerModel markerModel=lstMarkerModel.get(i);
            Marker marker=markerModel.getMarker();
            if (position<markerModel.getLstLatLng().size()-1){
                position=position+1;
            }else {
                position=0;
            }
            lstPosition.set(i,position);
            marker.setPosition(markerModel.getLstLatLng().get(position));
        }

            mHandler.sendEmptyMessageDelayed(WHAT_MARKER,100);
    }

    private void addMarkerToMap(){

        set=Const.getLatLng(getActivity());
        lstPosition=new ArrayList<>();
        lstMarkerModel=new ArrayList<>();
        for (List<LatLng> lstLatLng:set){
            LatLng latLng=lstLatLng.get(0);
            lstPosition.add(0);
            Marker marker=mMap.addMarker(new MarkerOptions().position(latLng));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drone));
            lstMarkerModel.add(new MarkerModel(marker,lstLatLng));
        }

    }
}
