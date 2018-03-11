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

import app.chaffer.LoginActivity;
import app.chaffer.Request;
import app.chaffer.R;
import app.chaffer.adapter.RequestListAdapter;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 12/02/2018.
 */

public class FragmentRequestList extends Fragment {


    RequestListAdapter adapter ;
    RecyclerView recyclerView ;
    ProgressBar progressBar ;

    ArrayList<Request> requestArrayList =new ArrayList<>() ;

    private String url= LoginActivity.IP+"/users/request" ;

    FragmentTransaction fm ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_request_list,container,false) ;
        //Fetching data from server
        prepareData();

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_offerList) ;
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar) ;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fm=getFragmentManager().beginTransaction() ;


        return view;

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

                        Log.d("response", response.toString());

                        try {
                            //Cleaning the array
                            requestArrayList.clear();

                            JSONArray array = new JSONArray(response.getString("reqAll"));
                            {
                                JSONArray offersArray=array.getJSONArray(0) ;

                                for (int i=0;i<offersArray.length();i++) {

                                    JSONObject object=offersArray.getJSONObject(i) ;
                                    JSONObject obj=new JSONObject(object.getString("row_to_json")) ;



                                    Log.d("response_RequestList",obj.toString()) ;

                                    //As we are using same view details fragment for All offers and user's posted request that's status is of no importance here and i have
                                    //set it to empty where same status will be used in PostedOffer fragment.

                                    Request request =new Request(obj.getString("fkuser_id"),obj.getString("request_id"),obj.getString("name"),
                                            obj.getString("description"),obj.getString("time_to_deliver"),obj.getString("loc_lat"),obj.getString("loc_long"),
                                            obj.getString("des_lat"),obj.getString("des_long"),obj.getString("pickup_des"),obj.getString("dropoff_des"),
                                            obj.getString("pkg_des") ,"") ;




                                  //  Log.d("des",obj.getString("description")) ;


                                    requestArrayList.add(request) ;

                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter
                                adapter=new RequestListAdapter(getActivity(), requestArrayList,fm) ;
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
