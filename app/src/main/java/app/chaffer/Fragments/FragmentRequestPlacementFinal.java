package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;

import static app.chaffer.LoginActivity.token;

public class FragmentRequestPlacementFinal extends Fragment implements View.OnClickListener {



    private EditText offerDescription ;
    Spinner time ;
    private EditText pickUplocationDescription,dropOffLocationDescription,packageDescription ;
    String timeText ;
    private Button placeOffer ;
    private String url=LoginActivity.IP+"/users/request" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_placement_two, container, false);




        offerDescription=(EditText)view.findViewById(R.id.editText_description) ;
         pickUplocationDescription=(EditText)view.findViewById(R.id.editText_pickUP_location_description_text) ;
         dropOffLocationDescription=(EditText)view.findViewById(R.id.editText_dropOff_location_description_text) ;
         packageDescription=(EditText)view.findViewById(R.id.edit_text_package_description) ;

        placeOffer=(Button) view.findViewById(R.id.place_offer) ;


         placeOffer.setOnClickListener(this);


        time=(Spinner)view.findViewById(R.id.timer_selection_spinner) ;

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                timeText=adapterView.getItemAtPosition(i).toString() ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==placeOffer.getId()){
            if(!offerDescription.getText().equals("") && !pickUplocationDescription.getText().equals("")){

                //putting data of request in a static array list deifned in Main Activity
                MainActivity.requestPlacementData.add(5,offerDescription.getText().toString()) ;
                MainActivity.requestPlacementData.add(6,pickUplocationDescription.getText().toString()) ;
                MainActivity.requestPlacementData.add(7,timeText) ;
                MainActivity.requestPlacementData.add(8,dropOffLocationDescription.getText().toString()) ;
                MainActivity.requestPlacementData.add(9,packageDescription.getText().toString()) ;





                //Seding request
                RequestQueue queue = Volley.newRequestQueue(getActivity());



                Map<String, String> postParam= new HashMap<String, String>();

                postParam.put("fkuser_id", MainActivity.requestPlacementData.get(0));
                postParam.put("description", MainActivity.requestPlacementData.get(5));
                postParam.put("time_to_deliver", MainActivity.requestPlacementData.get(7));
                postParam.put("loc_lat", MainActivity.requestPlacementData.get(1));
                postParam.put("loc_long", MainActivity.requestPlacementData.get(2));
                postParam.put("des_lat",MainActivity.requestPlacementData.get(3));
                postParam.put("des_long",MainActivity.requestPlacementData.get(4));
                postParam.put("pickup_des",MainActivity.requestPlacementData.get(6));
                postParam.put("dropoff_des",MainActivity.requestPlacementData.get(8));
                postParam.put("pkg_des",MainActivity.requestPlacementData.get(9));


//                For testing purpose only
//                JSONObject jsonObject=new JSONObject(postParam) ;
//                Log.d("obj",jsonObject.toString()) ;


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url
                        , new JSONObject(postParam),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("response", response.toString());

                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    if(object.getString("req").equals("posted")){

                                        Toast.makeText(getActivity(),"Request posted",Toast.LENGTH_LONG).show();


                                        //Changing fragment to home

                                        FragmentManager fm=getActivity().getSupportFragmentManager() ;
                                        FragmentTransaction transaction=getFragmentManager().beginTransaction() ;
                                        FragmentHome home=new FragmentHome() ;
                                        transaction.replace(R.id.layout,home) ;
                                        transaction.commit() ;




                                    }

                                }catch (Exception e){
                                    // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                                }


                                //Hiding progress bar

                            }
                        }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d( "Error: " + error.getMessage());

                        Toast.makeText(getActivity(),"Error occured!Please try again",Toast.LENGTH_LONG).show();


                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                        //Hiding progress bar


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






        }else {
                Toast.makeText(getActivity(),"Kindly provide reuquired descriptions",Toast.LENGTH_LONG).show(); ;
            }



        }
    }


