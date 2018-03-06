package app.chaffer.Fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.chaffer.Location;
import app.chaffer.R;
import app.chaffer.adapter.LocationListAdapter;

/**
 * Created by Mac on 21/02/2018.
 */

public class FragmentLocationSearch extends Fragment implements View.OnClickListener {
    EditText searchbar;
    String url;
    Button submit ;
    ProgressBar progressBar ;

    ArrayList<Location> locationList=new ArrayList<>() ;
    RecyclerView recyclerView ;

    LocationListAdapter adapter ;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_search, container, false);








        searchbar = view.findViewById(R.id.editText_enterLocation_text);
        progressBar=view.findViewById(R.id.progressBar) ;

        submit=view.findViewById(R.id.submit) ;
        submit.setOnClickListener(this);



        recyclerView=(RecyclerView) view.findViewById(R.id.recylerview_location_resul) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view ;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==submit.getId()) {
            //App crashes on spaces
           String locName= searchbar.getText().toString().replace(" ","%20") ;

            url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" +
                    "" + locName + "&location=33.6844,73.0479" +
                    "&key=AIzaSyC3GDDwlcmaH4TIH8JJLJ0e3Wrz5l_Eun0";

            prepareData(url);


        }
    }





    void prepareData(String url){




        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,url
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_location", response.toString());

                        try {
                            JSONArray locationArray = new JSONArray(response.getString("results"));

                            locationList.clear();

                            for (int i=0;i<locationArray.length();i++){

                                JSONObject obj=locationArray.getJSONObject(i) ;

                                //getting geometry object inside of array
                                JSONObject geometryObject= new JSONObject(obj.getString("geometry"));

                                //getting location(lat,lng) from geometrt obj
                                JSONObject objLoc=new JSONObject(geometryObject.getString("location"));


                                Location location=new Location(obj.getString("name"),obj.getString("formatted_address"),objLoc.getString("lat")
                                ,objLoc.getString("lng")) ;

                                locationList.add(location) ;



                               // Log.d("obj_loc",obj.toString());

                                Log.d("obj_loc",obj.getString("name"));
                                Log.d("obj_loc",obj.getString("formatted_address"));
                                Log.d("obj_loc",objLoc.getString("lat"));
                                Log.d("obj_loc",objLoc.getString("lng"));




                            }

                            progressBar.setVisibility(View.GONE);

                            //Fetching data and setting adapter
                            adapter=new LocationListAdapter(locationList) ;
                            recyclerView.setAdapter(adapter);



                        }
                        catch (Exception e){

                            Log.d("obj_loc",e.toString());


                        }



                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.d( "Error" , error.getMessage());

                progressBar.setVisibility(View.GONE);


                Toast.makeText(getActivity(),"Error occured!Please try again",Toast.LENGTH_LONG).show();




            }
        }) ;

        jsonObjReq.setTag("json");
        // Adding request to request queue
        queue.add(jsonObjReq);






    }


}


