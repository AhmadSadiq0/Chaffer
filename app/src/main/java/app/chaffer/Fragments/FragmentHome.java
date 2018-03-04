package app.chaffer.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.chaffer.MainActivity;
import app.chaffer.R;

/**
 * Created by Mac on 11/02/2018.
 */

public class FragmentHome extends Fragment implements View.OnClickListener{
    private Button btnPlaceOffer ;
    private Button btnViewOffer ;
    FragmentTransaction transaction;
    private TextView viewPostedOffers ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

         transaction=getFragmentManager().beginTransaction() ;

        btnPlaceOffer=(Button)view.findViewById(R.id.place_offer) ;
        btnViewOffer=(Button)view.findViewById(R.id.view_offer) ;
        viewPostedOffers=(TextView)view.findViewById(R.id.posted_offers) ;
        viewPostedOffers.setOnClickListener(this);
        btnPlaceOffer.setOnClickListener(this);
        btnViewOffer.setOnClickListener(this);




        return view;
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==btnPlaceOffer.getId()){


            FragmentOfferPlacement offerPlacement=new FragmentOfferPlacement() ;
            transaction.replace(R.id.layout,offerPlacement) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;


        }else if(view.getId()==btnViewOffer.getId()){

            FragmentOfferList offerList=new FragmentOfferList() ;
            transaction.replace(R.id.layout,offerList) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;


        }
        else if(view.getId()==viewPostedOffers.getId()){
            FragmentPostedOffers postedOffers=new FragmentPostedOffers() ;
            transaction.replace(R.id.layout,postedOffers) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;

        }

    }


}
