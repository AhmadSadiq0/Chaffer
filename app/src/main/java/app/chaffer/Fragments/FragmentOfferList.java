package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.Offer;
import app.chaffer.R;
import app.chaffer.Request;
import app.chaffer.adapter.OfferListAdapter;
import app.chaffer.adapter.RequestListAdapter;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 08/03/2018.
 */

public class FragmentOfferList extends Fragment {

    ProgressBar progressBar ;
    OfferListAdapter adapter ;
    RecyclerView recyclerView ;
    FragmentTransaction ft ;

    ArrayList<Offer> offerListArray =new ArrayList<>() ;

    private String url= LoginActivity.IP + "/users/getmyoffers";

    private String urlPostedOffer= LoginActivity.IP + "/users/sentoffers";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_offers_list,container,false) ;


        recyclerView=(RecyclerView) view.findViewById(R.id.offer_list_recycler_view) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        progressBar=(ProgressBar) view.findViewById(R.id.progressBar) ;


        prepareData();


        //intinalizing fragment transaction objection,it will be later used in OfferListAdapter
        ft=getFragmentManager().beginTransaction() ;






        return view ;

    }

    //Prepare data to fetch
    void prepareData(){




        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());






        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,url
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_offerList", response.toString());

                        try {
                            //Cleaning the array
                            offerListArray.clear();

                            JSONArray array = new JSONArray(response.getString("receivedOffers"));
                            {
                                //Steps to parse JSON data
                                JSONArray offersArray=array.getJSONArray(0) ;

                                //END




                                for (int i=0;i<offersArray.length();i++) {

                                    JSONObject jsonBuildObject=offersArray.getJSONObject(i) ;
                                    JSONObject dataObject=jsonBuildObject.getJSONObject("json_build_object") ;

                                    Log.d("response_offerList",dataObject.toString()) ;

                                    //request data
                                    JSONObject obj=dataObject.getJSONObject("requestInfo") ;





//
                                    Request request =new Request(obj.getString("request_id"),
                                            obj.getString("request_desc"),obj.getString("time_to_deliver"),obj.getString("loc_lat"),obj.getString("loc_long"),
                                            obj.getString("des_lat"),obj.getString("des_long"),obj.getString("pickup_des"),obj.getString("dropoff_des"),
                                            obj.getString("pkg_des") ,obj.getString("request_status")) ;


                                    Offer offer=new Offer(dataObject.getString("offer_id"),dataObject.getString("offer_sender_id"),dataObject.getString("offer_sender_name"),
                                            dataObject.getString("offer_desc"),dataObject.getString("amount"),dataObject.getString("offer_status"),dataObject.getString("time_suggested"),request) ;




                                    offerListArray.add(offer) ;






                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter
                                adapter=new OfferListAdapter(getActivity(), offerListArray,ft) ;
                                recyclerView.setAdapter(adapter);




                            }

                        }catch (Exception e){
//                             Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                            Log.d( "Error_offerlist" , e.toString());

                        }


                        //Hiding progress bar

                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "Error_offerlist" , error.toString());

                Toast.makeText(getActivity(),"Error occured!Please try again",Toast.LENGTH_LONG).show();


                //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                //Hiding progress bar
                progressBar.setVisibility(View.GONE);


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







}
