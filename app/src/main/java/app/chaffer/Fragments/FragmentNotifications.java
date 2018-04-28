package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import app.chaffer.Notifications;
import app.chaffer.Offer;
import app.chaffer.R;
import app.chaffer.Request;
import app.chaffer.adapter.NotificationListAdapter;
import app.chaffer.adapter.OfferListAdapter;

import static app.chaffer.LoginActivity.IP;
import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 15/03/2018.
 */

public class FragmentNotifications extends Fragment {

    private String url=IP+"/users/notifs" ;

    ProgressBar progressBar ;
    NotificationListAdapter adapter ;
    RecyclerView recyclerView ;
    FragmentTransaction ft ;

    ArrayList<Notifications> notificationArrayList =new ArrayList<>() ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_notification,container,false) ;

        progressBar=view.findViewById(R.id.progressBar) ;



        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_notifications) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareData();




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

                        Log.d("response_NotificationList", response.toString());

                        try {
                            //Cleaning the array
                            notificationArrayList.clear();

                            JSONArray array = new JSONArray(response.getString("all"));


                                //END

                                for (int i=0;i<array.length();i++) {
                                    //Steps to parse JSON data
                                    JSONObject notifications=array.getJSONObject(i) ;


                                   // Log.d("response_NotificationList",notifications.getString("fkuser_id")) ;

                                    Notifications notification=new Notifications(notifications.getString("notif_id"),notifications.getString("description"),notifications.getString("fkuser_id"),
                                            notifications.getString("fkrel_id"),notifications.getString("fkrel_type"),notifications.getString("isRead") );





////
//                                    Request request =new Request(obj.getString("request_id"),
//                                            obj.getString("request_desc"),obj.getString("time_to_deliver"),obj.getString("loc_lat"),obj.getString("loc_long"),
//                                            obj.getString("des_lat"),obj.getString("des_long"),obj.getString("pickup_des"),obj.getString("dropoff_des"),
//                                            obj.getString("pkg_des") ,obj.getString("request_status")) ;
//
//
//                                    Offer offer=new Offer(dataObject.getString("offer_id"),dataObject.getString("offer_sender_id"),dataObject.getString("offer_sender_name"),
//                                            dataObject.getString("offer_desc"),dataObject.getString("amount"),dataObject.getString("offer_status"),dataObject.getString("time_suggested"),request) ;
//



                                    notificationArrayList.add(notification) ;








                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter
                                adapter=new NotificationListAdapter(getActivity(), notificationArrayList,ft) ;
                                recyclerView.setAdapter(adapter);




                            }

                        }catch (Exception e){
//                             Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                            Log.d( "Error_Notificationlist" , e.toString());

                        }


                        //Hiding progress bar

                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "Error_Notificationlist" , error.toString());

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
