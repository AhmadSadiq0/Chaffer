package app.chaffer.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import app.chaffer.Fragments.FragmentRequestPlacement;
import app.chaffer.Location;
import app.chaffer.MainActivity;
import app.chaffer.R;

/**
 * Created by Mac on 12/02/2018.
 */

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.MyViewHolder> {

    private List<Location> locationList;
    private Context context ;
    FragmentTransaction fm ;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView locationName, address;
        RelativeLayout parentLayout ;


        public MyViewHolder(View view) {
            super(view);

            locationName = (TextView) view.findViewById(R.id.location_name);
            address = (TextView) view.findViewById(R.id.address);
            parentLayout = (RelativeLayout) view.findViewById(R.id.parent_layout);


        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_location_results, parent, false);

        return new MyViewHolder(itemView);
    }


    public LocationListAdapter(List<Location> locationList, Context context, FragmentTransaction fm) {
        this.locationList = locationList;
        this.context=context ;
        this.fm=fm ;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Location currentLocation = locationList.get(position);

        holder.locationName.setText("" + currentLocation.getLocationName());
        holder.address.setText("" + currentLocation.getLocationAddress() ) ;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LatLng latLng=new LatLng(Double.parseDouble(currentLocation.getLat()),Double.parseDouble(currentLocation.getLng())) ;
                if (MainActivity.requestPickupLatLng==null){
                    MainActivity.requestPickupLatLng=latLng ;
                }else if(MainActivity.requestDeliveryLatLng==null){
                    MainActivity.requestDeliveryLatLng=latLng ;
                }


                FragmentRequestPlacement requestPlacement=new FragmentRequestPlacement() ;
                fm.replace(R.id.layout,requestPlacement) ;
                fm.commit() ;


            }
        });

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


}


