package app.com.maksab.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import app.com.maksab.R;
import app.com.maksab.api.dao.NearByResponse;

/**
 * Created by RWS-DESIGNER on 9/6/2018.
 */

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private NearByResponse.BrandData brandData;

    public CustomInfoWindowGoogleMap(Context ctx,NearByResponse.BrandData brandData){
        this.context = ctx;
        this.brandData = brandData;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.snippet);
        ImageView img = view.findViewById(R.id.badge);

//        TextView hotel_tv = view.findViewById(R.id.hotels);
//        TextView food_tv = view.findViewById(R.id.food);
//        TextView transport_tv = view.findViewById(R.id.transport);

        Log.e("getTitle",marker.getTitle()+"");
        Log.e("getSnippet",marker.getSnippet()+"");
        Log.e("getId",marker.getId()+"");
        Log.e("getBrandName",brandData.getBrandName()+"");
        Log.e("getBrandImg",brandData.getBrandImg()+"");

        //name_tv.setText(marker.getTitle());
        //details_tv.setText(marker.getSnippet());

       // NearByResponse.BrandData infoWindowData = (NearByResponse.BrandData) marker.getTag();
        InfoWindowData infoWindowData = (InfoWindowData)marker.getTag();

        /*int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
                "drawable", context.getPackageName());
        img.setImageResource(imageId);*/

       // hotel_tv.setText(infoWindowData.getHotel());
       // food_tv.setText(infoWindowData.getFood());
       // transport_tv.setText(infoWindowData.getTransport());

        return view;
    }
}
