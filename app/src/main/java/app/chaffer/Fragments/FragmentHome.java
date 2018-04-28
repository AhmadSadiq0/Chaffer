package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.chaffer.R;

/**
 * Created by Mac on 11/02/2018.
 */

public class FragmentHome extends Fragment implements View.OnClickListener{
    private Button btnPlaceOffer ;
    private Button btnViewOffer ;
    FragmentTransaction transaction;
    private TextView viewPostedRequests ;
    private TextView viewPostedOffer ;
    private TextView newOrder ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            container.removeAllViews();

        View view=inflater.inflate(R.layout.fragment_home, container, false);



         transaction=getFragmentManager().beginTransaction() ;


        btnPlaceOffer=(Button)view.findViewById(R.id.place_offer) ;
        btnViewOffer=(Button)view.findViewById(R.id.view_offer) ;
        viewPostedRequests=(TextView)view.findViewById(R.id.posted_request) ;
        viewPostedOffer=(TextView)view.findViewById(R.id.posted_offers) ;
        newOrder=(TextView)view.findViewById(R.id.new_orders) ;


        newOrder.setOnClickListener(this);
        viewPostedOffer.setOnClickListener(this);
        viewPostedRequests.setOnClickListener(this);
        btnPlaceOffer.setOnClickListener(this);
        btnViewOffer.setOnClickListener(this);




        return view;
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==btnPlaceOffer.getId()){


            FragmentRequestPlacement offerPlacement=new FragmentRequestPlacement() ;
            transaction.replace(R.id.layout,offerPlacement) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;


        }else if(view.getId()==btnViewOffer.getId()){

            FragmentRequestList offerList=new FragmentRequestList() ;
            transaction.replace(R.id.layout,offerList) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;


        }
        else if(view.getId()==viewPostedRequests.getId()){
            FragmentPostedRequest postedOffers=new FragmentPostedRequest() ;
            transaction.replace(R.id.layout,postedOffers) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;

        }else if(view.getId()==viewPostedOffer.getId()){
            FragmentOfferList postedOffers=new FragmentOfferList() ;
            transaction.replace(R.id.layout,postedOffers) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;

        }else if (view.getId()==newOrder.getId()){
            FragmentOrderList fragmentOrderList=new FragmentOrderList() ;
            transaction.replace(R.id.layout,fragmentOrderList) ;
            transaction.addToBackStack("home") ;
            transaction.commit() ;
        }

    }


}
