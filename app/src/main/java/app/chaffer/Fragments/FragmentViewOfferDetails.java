package app.chaffer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.chaffer.ChatActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;
import app.chaffer.dialog.DialogPayAndStartOrder;
import app.chaffer.dialog.DialogPostedRequestDetailsFromOffer;
import app.chaffer.dialog.DialogViewRatings;

import static app.chaffer.MainActivity.chatTag;

/**
 * Created by Mac on 13/03/2018.
 */


public class FragmentViewOfferDetails extends Fragment implements View.OnClickListener {

    TextView requestDescription ;
    Button viewRequestDetails ;
    TextView offerSenderName ;
    TextView offerDescription ;
    TextView offerTime ;
    TextView offerAmount ;
    Button acceptOffer ;

    Button chat ;

    Button personReviewAndComments ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_offer_details,container,false) ;


        chatTag="offer" ;

       offerSenderName=view.findViewById(R.id.offer_sender_name) ;
        offerDescription=view.findViewById(R.id.text_offer_description) ;
        offerTime=view.findViewById(R.id.text_offer_time) ;
        offerAmount=view.findViewById(R.id.text_offer_amount) ;

        viewRequestDetails=view.findViewById(R.id.btn_view_request_details) ;
        acceptOffer=view.findViewById(R.id.btn_accept_offer) ;

        personReviewAndComments=view.findViewById(R.id.btn_person_review_comments) ;
        personReviewAndComments.setOnClickListener(this);

        viewRequestDetails.setOnClickListener(this);
        acceptOffer.setOnClickListener(this);

        chat=(Button)view.findViewById(R.id.chat) ;
        chat.setOnClickListener(this);


        //Setting values from static variable which is set when a specific item from the list is clicked
//        requestDescription.setText(MainActivity.selectedOfferFromOffersList.getRequest().getOfferDescription());
        offerSenderName.setText(MainActivity.selectedOfferFromOffersList.getSenderName());
        offerDescription.setText(MainActivity.selectedOfferFromOffersList.getOfferDescription());
        offerTime.setText(MainActivity.selectedOfferFromOffersList.getTime());
        offerAmount.setText(MainActivity.selectedOfferFromOffersList.getAmount());










        return view ;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==viewRequestDetails.getId()){
            DialogPostedRequestDetailsFromOffer dialog=new DialogPostedRequestDetailsFromOffer(getActivity()) ;
            dialog.show();
        }else if(view.getId()==acceptOffer.getId()){
            DialogPayAndStartOrder payAndStartOrder =new DialogPayAndStartOrder(getActivity()) ;
            payAndStartOrder.show();
        }else if (view.getId()==chat.getId()){
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        }else if (view.getId()==personReviewAndComments.getId()){
            DialogViewRatings dialogViewRatings =new DialogViewRatings(getActivity()) ;
            dialogViewRatings.show();
        }


        
    }
}
