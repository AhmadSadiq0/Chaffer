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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.DeliveryOrder;
import app.chaffer.R;
import app.chaffer.ReceivingOrder;
import app.chaffer.adapter.deliveryOrderListAdapter;
import app.chaffer.adapter.receivingOrderListAdapter;

import static app.chaffer.LoginActivity.IP;
import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;
import static app.chaffer.MainActivity.deliveryOrders;
import static app.chaffer.MainActivity.receivingOrders;

/**
 * Created by Mac on 23/04/2018.
 */

public class FragmentOrderList extends Fragment implements View.OnClickListener {


    private String urlForFetchingDeliveryOrdersIds=IP+"/users/deliveryorders" ;

    private String urlForFetchingReceivingOrdersIds=IP+"/users/receivingorders" ;

    ProgressBar progressBar ;

    RecyclerView recyclerView ;

    deliveryOrderListAdapter adapter ;
    receivingOrderListAdapter adapter2 ;

    FragmentTransaction ft ;

    Boolean isDelivery=true ;

    Button order_type ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_orders_list,container,false) ;


        order_type=(Button)view.findViewById(R.id.btn_order_type) ;
        order_type.setOnClickListener(this);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_order_list) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar=view.findViewById(R.id.progressBar) ;


        fetchingDeliveryOrders();

        //intinalizing fragment transaction objection,it will be later used in OfferListAdapter
        ft=getFragmentManager().beginTransaction() ;


        return view ;
    }













    //method to fetch all ids of orders to be delivered

    private void fetchingDeliveryOrders(){

        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,urlForFetchingDeliveryOrdersIds
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_delivery", response.toString());

                        try {
                            //Cleaning the array
                            deliveryOrders.clear();

                            JSONArray array = new JSONArray(response.getString("dorders"));

                                //Steps to parse JSON data
                                JSONArray offersArray=array.getJSONArray(0) ;

                                Log.d("response_delivery", offersArray.toString());


                                for (int i=0;i<offersArray.length();i++) {

                                    JSONObject dataObject1=offersArray.getJSONObject(0) ;
                                    JSONObject dataObject2=dataObject1.getJSONObject("row_to_json");

                                    DeliveryOrder deliveryOrder=new DeliveryOrder(dataObject2.getString("order_id"),dataObject2.getString("description"),
                                            dataObject2.getString("des_long"),dataObject2.getString("des_lat"),dataObject2.getString("fkuser_id"),
                                            dataObject2.getString("receiver_name"),dataObject2.getString("pickup_des"),dataObject2.getString("dropoff_des"),
                                            dataObject2.getString("pkg_des")) ;

                                    deliveryOrders.add(deliveryOrder) ;




                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                            //Fetching data and setting adapter
                            adapter=new deliveryOrderListAdapter(getActivity(), deliveryOrders,ft) ;
                            recyclerView.setAdapter(adapter);






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


//For receiving orders list
    private void fetchingReceivingOrders(){

        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,urlForFetchingReceivingOrdersIds
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_receving", response.toString());

                        try {
                            //Cleaning the array

                            receivingOrders.clear();

                            JSONArray array = new JSONArray(response.getString("getorders"));

                            //Steps to parse JSON data
                            JSONArray offersArray=array.getJSONArray(0) ;

                            Log.d("response_receiving", offersArray.toString());


                            for (int i=0;i<offersArray.length();i++) {

                                JSONObject dataObject1=offersArray.getJSONObject(0) ;
                                JSONObject dataObject2=dataObject1.getJSONObject("row_to_json");

                                ReceivingOrder receivingOrder=new ReceivingOrder(dataObject2.getString("order_id"),dataObject2.getString("description"),
                                        dataObject2.getString("des_long"),dataObject2.getString("des_lat"),dataObject2.getString("fkuser_id"),
                                        dataObject2.getString("delivery_person_name"),dataObject2.getString("pickup_des"),dataObject2.getString("dropoff_des"),
                                        dataObject2.getString("pkg_des")) ;

                                receivingOrders.add(receivingOrder) ;




                            }

                            Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                            //Fetching data and setting adapter
                            adapter2=new receivingOrderListAdapter(getActivity(), receivingOrders,ft) ;
                            recyclerView.setAdapter(adapter2);






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




    @Override
    public void onClick(View view) {
        if (view.getId()==order_type.getId()){
            if (isDelivery){

                order_type.setBackground(getResources().getDrawable(R.mipmap.receiving_receiving));
                isDelivery=false ;
                fetchingReceivingOrders();

            }else {
                isDelivery=true ;
                order_type.setBackground(getResources().getDrawable(R.mipmap.delivery_orders));
                fetchingDeliveryOrders();

            }
        }
    }
}
