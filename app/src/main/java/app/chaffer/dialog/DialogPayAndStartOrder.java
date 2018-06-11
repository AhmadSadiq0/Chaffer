package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.simplify.android.sdk.CardEditor;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;
import app.chaffer.SignUpUserForm;

import static android.content.Context.MODE_PRIVATE;
import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 14/04/2018.
 */

public class DialogPayAndStartOrder extends Dialog implements View.OnClickListener  {

    Context context;
    TextView cardNum ;
    TextView userName ;
    TextView expiryDate ;
    ProgressBar progressBar ;
    Button confirm ;
    String url= LoginActivity.IP+"/users/order" ;

    SharedPreferences.Editor editor ;
    SharedPreferences preferences ;

    public DialogPayAndStartOrder(@NonNull Context context) {
        super(context);
        this.context=context ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_pay_and_start_order);

        preferences=context.getSharedPreferences("User",MODE_PRIVATE) ;



        editor = context.getSharedPreferences("User", MODE_PRIVATE) .edit();
        preferences=context.getSharedPreferences("User",MODE_PRIVATE) ;

        cardNum=(TextView)findViewById(R.id.card_number);
        userName=(TextView)findViewById(R.id.user_name);
        expiryDate=(TextView)findViewById(R.id.expiry_date);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        confirm=(Button)findViewById(R.id.confirm_btn) ;
        confirm.setOnClickListener(this);

        userName.setText(LoginActivity.userName) ;
        cardNum.setText(preferences.getString("card_num", null + ""));
        expiryDate.setText(preferences.getString("exp_month", null + "")+"/"+preferences.getString("exp_year", null + ""));





    }





    @Override
    public void onClick(View view) {

        if (view.getId()==confirm.getId()){
            if (!cardNum.getText().equals("") && !cardNum.getText().equals(null) ) {

                prepareRequest();
            }else {
                Toast.makeText(context,"Kindly attach payment method first",Toast.LENGTH_SHORT).show();
            }

            //Creating token
//            simplify.createCardToken(cardEditor.getCard(), new CardToken.Callback() {
//                @Override
//                public void onSuccess(CardToken cardToken) {
//                    // ...
//                card_Token=cardToken ;
//
//                }
//                @Override
//                public void onError(Throwable throwable) {
//                    // ...
//                    Toast.makeText(getContext(),"Can not create card token",Toast.LENGTH_SHORT).show();
//                }
//            });



        }


    }


    //Start order request
    private void prepareRequest(){
        //Progress bar Visible
        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(getContext());


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("fkoffer_id", MainActivity.selectedOfferFromOffersList.getOfferid());



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
                , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {
                            JSONObject object = new JSONObject(response.toString());

                            if (object.getString("order").equals("started")){
                                Toast.makeText(getContext(),"Order started",Toast.LENGTH_SHORT).show();

                                dismiss();
                            }


                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }


                        //Hiding progress bar
                        progressBar.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

                Toast.makeText(getContext(), "Error occured!Please try again", Toast.LENGTH_LONG).show();


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


    private void pushPaymentRequest(){
        //Progress bar Visible
        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(getContext());


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("fkoffer_id", MainActivity.selectedOfferFromOffersList.getOfferid());



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
                , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {
                            JSONObject object = new JSONObject(response.toString());

                            if (object.getString("order").equals("started")){
                                Toast.makeText(getContext(),"Order started",Toast.LENGTH_SHORT).show();

                                dismiss();
                            }


                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }


                        //Hiding progress bar
                        progressBar.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

                Toast.makeText(getContext(), "Error occured!Please try again", Toast.LENGTH_LONG).show();


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
