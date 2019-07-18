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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.HashSet;

import app.com.maksab.R;
import app.com.maksab.api.dao.BrandDetailsResponse;

public class MapLocationAdapter1 extends RecyclerView.Adapter<MapLocationViewHolder1> {
    protected HashSet<MapView> mMapViews = new HashSet<>();
    //protected ArrayList<MapLocation> mMapLocations;
    protected ArrayList<BrandDetailsResponse.LocationList> mMapLocations;

    public void setMapLocations(ArrayList<BrandDetailsResponse.LocationList> mapLocations) {
        mMapLocations = mapLocations;
    }

    @Override
    public MapLocationViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int position) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_item, viewGroup, false);
        MapLocationViewHolder1 viewHolder = new MapLocationViewHolder1(viewGroup.getContext(), view);

        mMapViews.add(viewHolder.mapView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MapLocationViewHolder1 viewHolder, int position) {
        BrandDetailsResponse.LocationList mapLocation = mMapLocations.get(position);

        viewHolder.itemView.setTag(mapLocation);

        viewHolder.title.setText(mapLocation.getVenderName());
       // viewHolder.description.setText(mapLocation.center.latitude + " " + mapLocation.center.longitude);

        viewHolder.setMapLocation(mapLocation);
    }

    @Override
    public int getItemCount() {
        return mMapLocations == null ? 0 : mMapLocations.size();
    }

    public HashSet<MapView> getMapViews() {
        return mMapViews;
    }
}
