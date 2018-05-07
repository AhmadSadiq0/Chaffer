package app.chaffer.Fragments;

import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
import app.chaffer.dialog.DialogAddPaymentMethod;
import app.chaffer.dialog.DialogDeleteCrediCard;

import static android.content.Context.MODE_PRIVATE;
import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;

/**
 * Created by Mac on 08/03/2018.
 */

public class FragmentMore extends Fragment implements View.OnClickListener{

    ProgressBar progressBar ;
    TextView firstName ;
    TextView lastName ;
    TextView cnic ;
    TextView phoneNumber ;
    TextView email ;
    ImageView userImage ;
    TextView availableFunds ;
    TextView ratings ;

    Button btn_addPayement ;
    Button btn_deletePayement ;


    private String url= LoginActivity.IP +"/users/myinfo" ;


    SharedPreferences preferences ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more,container,false) ;

        progressBar=view.findViewById(R.id.progressBar) ;



        preferences=getActivity().getSharedPreferences("User",MODE_PRIVATE) ;


        firstName=view.findViewById(R.id.text_fn) ;
        lastName=view.findViewById(R.id.text_ln) ;
        cnic=view.findViewById(R.id.text_cninc);
        phoneNumber=view.findViewById(R.id.text_phone) ;
        email=view.findViewById(R.id.text_email) ;
        userImage=view.findViewById(R.id.profile_image) ;
        availableFunds=(TextView)view.findViewById(R.id.available_funds) ;
        ratings=(TextView)view.findViewById(R.id.average_rating) ;

        btn_addPayement=view.findViewById(R.id.btn_addPayment) ;
        btn_addPayement.setOnClickListener(this);

        btn_deletePayement=view.findViewById(R.id.btn_deletePayment) ;
        btn_deletePayement.setOnClickListener(this);


//        if (!preferences.getString("card_num",null).equals(null)){
//            btn_addPayement.setVisibility(View.GONE);
//            btn_deletePayement.setVisibility(View.VISIBLE);
//        }else {
//            btn_addPayement.setVisibility(View.VISIBLE);
//            btn_deletePayement.setVisibility(View.GONE);
//        }




        prepareData();

        return view ;
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==btn_addPayement.getId())
        {
            DialogAddPaymentMethod addPaymentMethod=new DialogAddPaymentMethod(getActivity()) ;
            addPaymentMethod.show();
        }
        else if (view.getId()==btn_deletePayement.getId()){

            DialogDeleteCrediCard deleteCrediCard=new DialogDeleteCrediCard(getActivity());
            deleteCrediCard.show() ;

        }
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





//  Getting user data
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
                                availableFunds.setText(innerObject.getString("available_funds")+"-/Rs");
                                ratings.setText(innerObject.getString("rating")+"/5");

                                new DownloadImageTask(userImage).execute("https://scontent.fisb1-1.fna.fbcdn.net/v/t1.0-9/26219785_1305275912911579_859082525081003821_n.jpg?_nc_cat=0&oh=72f9098e18dd4a961c32630fa717b805&oe=5B2A6E46") ;


                            }

                            Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();


                        }catch (Exception e){

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

