package app.chaffer.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
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
    TextView firstName ;
    TextView lastName ;
    TextView cnic ;
    TextView phoneNumber ;
    TextView email ;
    ImageView userImage ;

    private String url= LoginActivity.IP +"/users/myinfo" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more,container,false) ;

        progressBar=view.findViewById(R.id.progressBar) ;


        firstName=view.findViewById(R.id.text_fn) ;
        lastName=view.findViewById(R.id.text_ln) ;
        cnic=view.findViewById(R.id.text_cninc);
        phoneNumber=view.findViewById(R.id.text_phone) ;
        email=view.findViewById(R.id.text_email) ;
        userImage=view.findViewById(R.id.profile_image) ;





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

                            JSONArray array = new JSONArray(response.getString("userinfo"));
                            {
                                JSONArray offersArray=array.getJSONArray(0) ;


                                    JSONObject Object=offersArray.getJSONObject(0) ;
                                    JSONObject innerObject=Object.getJSONObject("row_to_json") ;





                                    firstName.setText(innerObject.getString("first_name").toString());
                                    lastName.setText(innerObject.getString("last_name").toString());
                                    phoneNumber.setText(innerObject.getString("phone").toString());
                                    cnic.setText(innerObject.getString("cnic").toString());


                                    new DownloadImageTask(userImage).execute("https://scontent.fisb1-1.fna.fbcdn.net/v/t1.0-9/26219785_1305275912911579_859082525081003821_n.jpg?_nc_cat=0&oh=72f9098e18dd4a961c32630fa717b805&oe=5B2A6E46") ;




                                    //As we are using same view details fragment for All offers and user's posted request that's status is of no importance here and i have
                                    //set it to empty where same status will be used in PostedOffer fragment.





                                    //  Log.d("des",obj.getString("description")) ;



                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter




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





            //Code to download and load image from url
            private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
                ImageView bmImage;

                public DownloadImageTask(ImageView bmImage) {
                    this.bmImage = bmImage;
                }

                protected Bitmap doInBackground(String... urls) {
                    String urldisplay = urls[0];
                    Bitmap mIcon11 = null;
                    try {
                        InputStream in = new java.net.URL(urldisplay).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                    return mIcon11;
                }

                protected void onPostExecute(Bitmap result) {
                    bmImage.setImageBitmap(result);
                }
            }


            }

