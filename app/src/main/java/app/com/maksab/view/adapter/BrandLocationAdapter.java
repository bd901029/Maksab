package app.com.maksab.view.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.BrandDetailsResponse;
import app.com.maksab.databinding.RowBrandLocationBinding;
import app.com.maksab.listener.OnItemClickListener;

public class BrandLocationAdapter extends RecyclerView.Adapter<BrandLocationAdapter.ViewHolder> implements OnMapReadyCallback {

    private Context context;
    private ArrayList<BrandDetailsResponse.LocationList> categoryListArrayList;
    private OnItemClickListener onItemClickListener;
    public TextView title;
    public LatLng center;
    protected GoogleMap mGoogleMap;
    protected BrandDetailsResponse.LocationList locationList;
    int pos = 0;

    public BrandLocationAdapter(Context context, ArrayList<BrandDetailsResponse.LocationList> categoryListArrayList,
                                OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowBrandLocationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .row_brand_location, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(categoryListArrayList.get(position));
        BrandDetailsResponse.LocationList mapLocation = categoryListArrayList.get(position);
        holder.binding.map.onCreate(null);
        holder.binding.map.getMapAsync(this);


        holder.itemView.setTag(mapLocation);
        center = new LatLng(Double.parseDouble(mapLocation.getLatitude()), Double.parseDouble(mapLocation.getLongitude()));
        //holder.title.setText(mapLocation.getVenderName());
      // holder.binding.contact.setText(mapLocation.getLatitude() + " " + mapLocation.getLongitude());

        setMapLocation(mapLocation);
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowBrandLocationBinding binding;

        private ViewHolder(RowBrandLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * On Item click listener method
     * @param categoryList Store object of clicked position
     */
    public void onItemClick(BrandDetailsResponse.LocationList categoryList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryList), categoryList);
    }

    public void setMapLocation(BrandDetailsResponse.LocationList mapLocation) {
        locationList = mapLocation;

        // If the map is ready, update its content.
        if (mGoogleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        MapsInitializer.initialize(context);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        // If we have map data, update the map content.
        if (categoryListArrayList != null) {
            updateMapContents();
        }
    }

    protected void updateMapContents() {
        // Since the mapView is re-used, need to remove pre-existing mapView features.
        mGoogleMap.clear();
        //double lat = latlng.latitude;
        Log.e("getVenderName",categoryListArrayList.get(pos).getVenderName()+"");
         LatLng center =  new LatLng(Double.parseDouble(categoryListArrayList.get(pos).getLatitude()), Double.parseDouble(categoryListArrayList.get(pos).getLongitude()));
       // Log.e("longitude",locationList.getCenter().longitude+"");
        // Update the mapView feature data and camera position.
        mGoogleMap.addMarker(new MarkerOptions().position(center));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, 13.0f);
        pos++;
        mGoogleMap.moveCamera(cameraUpdate);
    }

}