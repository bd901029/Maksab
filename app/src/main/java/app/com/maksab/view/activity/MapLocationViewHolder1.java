/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.com.maksab.view.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.maksab.R;
import app.com.maksab.api.dao.BrandDetailsResponse;

public class MapLocationViewHolder1 extends RecyclerView.ViewHolder implements OnMapReadyCallback {
    public TextView title;
    public TextView description;

    protected GoogleMap mGoogleMap;
    protected BrandDetailsResponse.LocationList mMapLocation;

    public MapView mapView;
    private Context mContext;

    public MapLocationViewHolder1(Context context, View view) {
        super(view);

        mContext = context;

        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
        mapView = (MapView) view.findViewById(R.id.map);

        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    public void setMapLocation(BrandDetailsResponse.LocationList mapLocation) {
        mMapLocation = mapLocation;

        // If the map is ready, update its content.
        if (mGoogleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        MapsInitializer.initialize(mContext);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        // If we have map data, update the map content.
        if (mMapLocation != null) {
            updateMapContents();
        }
    }

    protected void updateMapContents() {
        // Since the mapView is re-used, need to remove pre-existing mapView features.
        mGoogleMap.clear();

        Log.e("",mMapLocation.center+"");
        // Update the mapView feature data and camera position.
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-30.749913,22.589747)));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-30.749913,22.589747), 10f);
        mGoogleMap.moveCamera(cameraUpdate);
    }
}
