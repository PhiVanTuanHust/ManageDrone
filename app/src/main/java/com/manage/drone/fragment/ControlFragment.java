package com.manage.drone.fragment;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.models.MarkerModel;
import com.manage.drone.utils.Const;
import com.manage.drone.utils.ViewUtil;

import java.util.ArrayList;
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
    private  PolylineOptions options;
    private int position = 0;
    private CameraPosition cameraPosition;
    private List<LatLng> lstPositionMoving = new ArrayList<>();
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
        LatLng latLng=getLatLng();
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
        mMap.addPolyline(options);
        animateMarker(0);
    }
    public void animateMarker(int position) {

        if (options!=null){
            lstPositionMoving=options.getPoints();
            if (position < lstPositionMoving.size() - 1) {
                position = position+1;
            } else {
                position = 0;
            }
            this.position = position;
            double lng = lstPositionMoving.get(position).longitude + 0.0000001;
            double lat = lstPositionMoving.get(position).latitude + 0.0000001;

            marker.setPosition(new LatLng(lat, lng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mHandler.sendEmptyMessageDelayed(WHAT_MARKER, 1000);
        }

    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MARKER) {
                animateMarker(position);
            }
            super.handleMessage(msg);
        }
    };

    private LatLng getLatLng(){
        double latitude = 0, longitude = 0;
        LatLng latLng2 = null;
        if (Const.options!=null){
            options=Const.options;
            
            List<LatLng> lst=options.getPoints();
            for (int i=0;i<lst.size();i++){
                if (i==0){
                    latLng=new LatLng(lst.get(i).latitude,lst.get(i).longitude);
                }
                latitude=latitude+lst.get(i).latitude;
                longitude=longitude+lst.get(i).longitude;
            }
            latitude=latitude/lst.size();
            longitude=longitude/lst.size();
            return new LatLng(latitude,longitude);
        }else {
            HashSet<List<LatLng>> set=Const.getLatLng(getActivity());
            List<Integer> lstPosition=new ArrayList<>();
            for (List<LatLng> lstLatLng:set){
                options=new PolylineOptions();
                lstPosition.add(0);
                options.width(8).color(Color.RED).geodesic(true);

                if (lstPosition.size()==1){
                    options.addAll(lstLatLng);
                    for (int i=0;i<lstLatLng.size();i++){
                        if (i==0){
                            latLng=new LatLng(lstLatLng.get(i).latitude,lstLatLng.get(i).longitude);
                        }
                        latitude=latitude+lstLatLng.get(i).latitude;
                        longitude=longitude+lstLatLng.get(i).longitude;
                    }
                    latitude=latitude/lstLatLng.size();
                    longitude=longitude/lstLatLng.size();
                    return new LatLng(latitude,longitude);

                }


            }
            marker = mMap.addMarker(new MarkerOptions().position(latLng2));
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drone));
            mMap.addPolyline(options);
            return latLng;
        }
    }
}
