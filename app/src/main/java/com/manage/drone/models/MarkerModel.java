package com.manage.drone.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Phí Văn Tuấn on 13/11/2018.
 */

public class MarkerModel {
    private Marker marker;
    private List<LatLng> lstLatLng;

    public MarkerModel(Marker marker, List<LatLng> lstLatLng) {
        this.marker = marker;
        this.lstLatLng = lstLatLng;
    }

    public Marker getMarker() {
        return marker;
    }

    public List<LatLng> getLstLatLng() {
        return lstLatLng;
    }
}
