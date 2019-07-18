package app.com.maksab.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import app.com.maksab.R;
import app.com.maksab.api.dao.ContactUsResponse;
import app.com.maksab.databinding.RowContactUsLocationBinding;
import app.com.maksab.listener.OnItemClickListener;

public class ContactUsLocationAdapter extends RecyclerView.Adapter<ContactUsLocationAdapter.ViewHolder> implements OnMapReadyCallback {

    private Context context;
    private ArrayList<ContactUsResponse.ContactList> categoryListArrayList;
    private OnItemClickListener onItemClickListener;
    public TextView title;
    public LatLng center;
    protected GoogleMap mGoogleMap;
    protected ContactUsResponse.ContactList locationList;
    int pos = 0;

    public ContactUsLocationAdapter(Context context, ArrayList<ContactUsResponse.ContactList> categoryListArrayList,
                                    OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryListArrayList = categoryListArrayList;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowContactUsLocationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .row_contact_us_location, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setAdapter(this);
        holder.binding.setModel(categoryListArrayList.get(position));
        ContactUsResponse.ContactList mapLocation = categoryListArrayList.get(position);
        holder.binding.map.onCreate(null);
        holder.binding.map.getMapAsync(this);
        holder.itemView.setTag(mapLocation);
        center = new LatLng(Double.parseDouble(mapLocation.getLatitude()), Double.parseDouble(mapLocation.getLongitude()));

        setMapLocation(mapLocation);
    }

    @Override
    public int getItemCount() {
        return categoryListArrayList != null ? categoryListArrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RowContactUsLocationBinding binding;

        private ViewHolder(RowContactUsLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * On Item click listener method
     * @param categoryList Store object of clicked position
     */
    public void onItemClick(ContactUsResponse.ContactList categoryList) {
        onItemClickListener.onClick(categoryListArrayList.indexOf(categoryList), categoryList);
    }

    public void setMapLocation(ContactUsResponse.ContactList mapLocation) {
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
        mGoogleMap.clear();
         LatLng center =  new LatLng(Double.parseDouble(categoryListArrayList.get(pos).getLatitude()), Double.parseDouble(categoryListArrayList.get(pos).getLongitude()));
        mGoogleMap.addMarker(new MarkerOptions().position(center));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, 13.0f);
        pos++;
        mGoogleMap.moveCamera(cameraUpdate);
    }

}