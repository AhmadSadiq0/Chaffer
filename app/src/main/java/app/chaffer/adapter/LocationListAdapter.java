package app.chaffer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.chaffer.Location;
import app.chaffer.R;

/**
 * Created by Mac on 12/02/2018.
 */

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.MyViewHolder> {

    private List<Location> locationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView locationName, address;

        public MyViewHolder(View view) {
            super(view);

            locationName = (TextView) view.findViewById(R.id.location_name);
            address = (TextView) view.findViewById(R.id.address);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_location_results, parent, false);

        return new MyViewHolder(itemView);
    }


    public LocationListAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }


    @Override
    public void onBindViewHolder(LocationListAdapter.MyViewHolder holder, int position) {

        Location currentLocation = locationList.get(position);
        holder.locationName.setText("" + currentLocation.getLocationName());
        holder.address.setText("" + currentLocation.getLocationAddress() ) ;


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


}


