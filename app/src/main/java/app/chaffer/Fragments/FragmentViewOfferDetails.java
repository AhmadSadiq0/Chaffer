package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.Offer;
import app.chaffer.R;
import app.chaffer.adapter.PostedOrderListAdapter;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 02/03/2018.
 */

public class FragmentViewOfferDetails extends Fragment implements View.OnClickListener {

    TextView username,offerDes,pickUpDes,dropoffDes,status ;
    Button btn_closeOffer ;
    Button btn_viewMap ;

    //Url to close a request
    private String url= LoginActivity.IP+"/users/request/"+MainActivity.selectedOfferFromOfferFeed.getRequestId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view_offer_details,container,false) ;

        username=(TextView) view.findViewById(R.id.user_name) ;
        offerDes=(TextView) view.findViewById(R.id.offer_description_text) ;
        pickUpDes=(TextView) view.findViewById(R.id.picklocation_description_text) ;
        dropoffDes=(TextView) view.findViewById(R.id.dropoff_description_text) ;
        status=(TextView) view.findViewById(R.id.status) ;



        btn_viewMap=(Button) view.findViewById(R.id.view_map) ;
        btn_viewMap.setOnClickListener(this);

        btn_closeOffer=(Button) view.findViewById(R.id.close_request) ;
        btn_closeOffer.setOnClickListener(this);

        //setting text
        username.setText(MainActivity.selectedOfferFromOfferFeed.getUserName());
        offerDes.setText(MainActivity.selectedOfferFromOfferFeed.getOfferDescription());
        pickUpDes.setText(MainActivity.selectedOfferFromOfferFeed.getPickUpLocationDescription());
        dropoffDes.setText(MainActivity.selectedOfferFromOfferFeed.getDrofOffLocationDescription());


        //Setting status text
        if(MainActivity.selectedOfferFromOfferFeed.getStatus().equals("ACTIVE")){
            //Enabling button and setting status text as Active

            status.setVisibility(View.VISIBLE);
            status.setText("Active");
            status.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn_closeOffer.setVisibility(View.VISIBLE) ;

        }
        else if(MainActivity.selectedOfferFromOfferFeed.getStatus().equals("CLOSED")) {
            //Removing button and setting status text as closed
            status.setVisibility(View.VISIBLE);
            status.setTextColor(getResources().getColor(R.color.red));
            status.setText("Closed");
            btn_closeOffer.setVisibility(View.GONE) ;

        }




        return view ;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btn_closeOffer.getId()){

            //Seding request
            RequestQueue queue = Volley.newRequestQueue(getActivity());







            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,url
                    , new JSONObject(),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("response", response.toString());
                            try {
                                if (response.getString("val").equals("done")) {

                                    //Removing button and setting status text as closed
                                    btn_closeOffer.setVisibility(View.GONE) ;
                                    status.setText("Closed");
                                    status.setTextColor(R.color.red);



                                }
                            }catch (Exception e){
                                Log.d("view_details_exception",e.toString()) ;
                                Toast.makeText(getActivity(),"Sorry there is an error",Toast.LENGTH_LONG).show();
                            }




                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d( "Closing_offer" , error.toString());

                    Toast.makeText(getActivity(),"Error occured!Please try again",Toast.LENGTH_LONG).show();




                }
            }) {


                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //  params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", token);
                    return params;
                }



            };

            jsonObjReq.setTag("json");
            // Adding request to request queue
            queue.add(jsonObjReq);






        }

        else if (view.getId()==btn_viewMap.getId()){

            FragmentMapsViewOfferDetails fragment=new FragmentMapsViewOfferDetails() ;

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.addToBackStack("viewOffer") ;
            transaction.replace(R.id.layout,fragment);
            transaction.commit();


        }

        }
    }

