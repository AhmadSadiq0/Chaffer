package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.R;
import app.chaffer.Request;
import app.chaffer.adapter.RequestListAdapter;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 08/03/2018.
 */

public class FragmentMore extends Fragment {

    ProgressBar progressBar ;

    private String url= LoginActivity.IP +"/users/myinfo" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more,container,false) ;

        progressBar=view.findViewById(R.id.progressBar) ;

        prepareData();

        return view ;
    }

    void prepareData(){

        //Progress bar visibility
        progressBar.setVisibility(View.VISIBLE);


        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,url
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_userInfo", response.toString());

                        try {
                            //Cleaning the array

                            JSONArray array = new JSONArray(response.getString(""));
                            {
                                JSONArray offersArray=array.getJSONArray(0) ;

                                for (int i=0;i<offersArray.length();i++) {

                                    JSONObject object=offersArray.getJSONObject(i) ;
                                    JSONObject obj=new JSONObject(object.getString("row_to_json")) ;




                                    //As we are using same view details fragment for All offers and user's posted request that's status is of no importance here and i have
                                    //set it to empty where same status will be used in PostedOffer fragment.





                                    //  Log.d("des",obj.getString("description")) ;



                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter


                            }

                        }catch (Exception e){
//                             Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                            Log.d( "Error More" , e.toString());

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

