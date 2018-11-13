package com.manage.drone.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.utils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 31/10/2018.
 */

public class ObserveFragment extends BaseFragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private LatLng latLng;
    private double latMean, lonMean = 0;
    private Marker oldMarker,marker;
    private int position = 0;
    private List<LatLng> lstPositionMoving = new ArrayList<>();
    private static final int WHAT_MARKER=0;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MARKER) {
                animateMarker(position);
            }
            super.handleMessage(msg);
        }
    };

    public static ObserveFragment newInstance() {

        Bundle args = new Bundle();
        ObserveFragment fragment = new ObserveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.observe_fragment;
    }

    @Override
    protected void initView(View view) {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap != null)
            mMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    protected void initData() {


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);
        if (Const.lstPolygonOptions.size() > 0) {


            PolygonOptions polygonOptions = Const.lstPolygonOptions.get(0);
            polygonOptions.strokeWidth(10);
            polygonOptions.strokeColor(getResources().getColor(R.color.colorRed));
            LatLng marker = getPositionObserve(polygonOptions);
            addMarkerObserve(marker);
//                if (oldMarker!=null){
//                    animateMarker(this.oldMarker, this.marker.getPosition());
//                }
            latMean = latMean + marker.latitude;
            lonMean = lonMean + marker.longitude;
            mMap.addPolygon(polygonOptions);

            latLng = new LatLng(latMean / Const.lstPolygonOptions.size(), lonMean / Const.lstPolygonOptions.size());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.setOnMarkerClickListener(this);
            animateMarker(0);
        }

    }

    private void addMarkerObserve(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        marker=mMap.addMarker(markerOptions);

    }

    private LatLng getPositionObserve(PolygonOptions polygonOptions) {

        LatLng latLng = null;
        double latitude = 0, longitude = 0;
        int size = polygonOptions.getPoints().size();
        for (int i = 0; i < size; i++) {
            LatLng item = polygonOptions.getPoints().get(i);
            Log.e("lat",item.latitude+"  lng  "+item.longitude);
            latitude = latitude + item.latitude;
            longitude = longitude + item.longitude;
            lstPositionMoving.add(new LatLng(item.latitude, item.longitude));
            if (this.oldMarker == null) {
                oldMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drone));
            }

        }

        latLng = new LatLng(latitude / size, longitude / size);
        return latLng;
    }

    public void animateMarker(int position) {

        if (position < lstPositionMoving.size() - 1) {
            position = position+1;
        } else {
            position = 0;
        }
        this.position = position;
        double lng = lstPositionMoving.get(position).longitude + 0.0000001;
        double lat = lstPositionMoving.get(position).latitude + 0.0000001;
        oldMarker.setPosition(new LatLng(lat, lng));
        mHandler.sendEmptyMessageDelayed(WHAT_MARKER, 100);
    }


}
