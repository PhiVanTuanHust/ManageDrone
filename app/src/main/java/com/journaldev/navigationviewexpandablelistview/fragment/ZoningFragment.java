package com.journaldev.navigationviewexpandablelistview.fragment;

import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.googlemap.DrawingPanel;
import com.journaldev.navigationviewexpandablelistview.googlemap.MapFragment;
import com.journaldev.navigationviewexpandablelistview.utils.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;

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
    private LatLngBounds bounds;
    double maxDistanceFromCenter;
    private LatLng latLng = new LatLng(55.404290078521235, 89.46081262081861);
    private LatLngBounds latLngBounds;
    @BindView(R.id.frame_view)
    FrameLayout flMapContainer;
    @BindView(R.id.imgSelected)
    ImageView imgDraw;

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
                    mMap.clear();

                    //Join all points and draw polygon
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

                    //latLngBounds = toBounds(latLng, maxDistanceFromCenter);


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

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.404290078521235, 89.46081262081861), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(55.404290078521235, 89.46081262081861))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @OnClick(R.id.imgSelected)
    public void onDraw() {
        mMap.clear();
        drawingpanel.setVisibility(View.VISIBLE);
        drawingpanel.clear();
        polygonOptions = new PolygonOptions();
        latLngs=new ArrayList<>();


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
//                        imgDraw.setVisibility(View.GONE);
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
}
