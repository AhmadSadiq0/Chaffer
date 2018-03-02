package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.chaffer.MainActivity;
import app.chaffer.R;

/**
 * Created by Mac on 02/03/2018.
 */

public class FragmentViewOfferDetails extends Fragment {

    TextView username,offerDes,pickUpDes,dropoffDes ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_offer_details,container,false) ;

        username=(TextView) view.findViewById(R.id.user_name) ;

        offerDes=(TextView) view.findViewById(R.id.offer_description_text) ;
        pickUpDes=(TextView) view.findViewById(R.id.picklocation_description_text) ;
        dropoffDes=(TextView) view.findViewById(R.id.dropoff_description_text) ;

        //setting text
        username.setText(MainActivity.selectedOfferFromOfferFeed.getUserName());
        offerDes.setText(MainActivity.selectedOfferFromOfferFeed.getOfferDescription());
        pickUpDes.setText(MainActivity.selectedOfferFromOfferFeed.getPickUpLocationDescription());
        dropoffDes.setText(MainActivity.selectedOfferFromOfferFeed.getDrofOffLocationDescription());


        return view ;
    }
}
