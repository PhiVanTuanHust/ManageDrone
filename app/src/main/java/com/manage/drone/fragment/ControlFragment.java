package com.manage.drone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.utils.Const;
import com.manage.drone.utils.ViewUtil;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 6/11/2018.
 */

public class ControlFragment extends BaseFragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private static final int WHAT_MARKER = 0;
    private GoogleMap mMap;
    private Marker marker;
    private CameraPosition cameraPosition;
    private LatLng latLng = new LatLng(55.40441324369938, 89.46476418524982);

    public static ControlFragment newInstance() {

        Bundle args = new Bundle();

        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.control_fragment;
    }

    @Override
    protected void initView(View view) {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (mMap != null) {
            mMap.getUiSettings().setRotateGesturesEnabled(false);
        }

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        mMap.getUiSettings().setZoomControlsEnabled(false);
        cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(this);
        marker = mMap.addMarker(new MarkerOptions().position(latLng));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drone));
        animateMarker();
    }

    public void animateMarker() {


        LatLng latLng = marker.getPosition();
        double lng = latLng.longitude + 0.000000001;
        double lat = latLng.latitude + 0.0000000001;
        marker.setPosition(new LatLng(lat, lng));

        mHandler.sendEmptyMessageDelayed(WHAT_MARKER, 100);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MARKER) {
                animateMarker();
            }
            super.handleMessage(msg);
        }
    };
}
