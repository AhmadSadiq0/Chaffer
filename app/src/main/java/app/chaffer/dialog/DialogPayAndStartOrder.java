package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 14/04/2018.
 */

public class DialogPayAndStartOrder extends Dialog implements View.OnClickListener  {

    Button payAndStart ;
    String url= LoginActivity.IP+"/users/order" ;
    ProgressBar progressBar ;
    Simplify simplify;
    CardEditor cardEditor ;
    RadioButton onlineDeliverRadio ;
    RadioButton cashOnDelivery ;
    public DialogPayAndStartOrder(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_pay_and_start_order);


         cardEditor = (CardEditor) findViewById(R.id.card_editor);

        onlineDeliverRadio=(RadioButton) findViewById(R.id.radio_online_paymet) ;
        cashOnDelivery=(RadioButton) findViewById(R.id.radio_cash_on_deliver) ;

        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;


        payAndStart=(Button)findViewById(R.id.start_order) ;
        payAndStart.setOnClickListener(this);

        //Simplify payment
        simplify = new Simplify();
        simplify.setApiKey("sbpb_YmQwMjgxYzgtOGRlYy00NTk1LWFhYzMtNTY5ODNmNjMxMzY3");


        cardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                payAndStart.setEnabled(cardEditor.isValid());

            }
        });

        //handling radio buttons
        onlineDeliverRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onlineDeliverRadio.isChecked()){
                    cardEditor.setVisibility(View.VISIBLE);
                }else {
                    cardEditor.setVisibility(View.GONE);
                }
            }
        });

        cashOnDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cashOnDelivery.isChecked()){
                    payAndStart.setEnabled(true);
                }else {
                    payAndStart.setEnabled(false);
                }
            }
        });





    }





    @Override
    public void onClick(View view) {

        if (view.getId()==payAndStart.getId()){

            prepareRequest();

            //Creating token
            simplify.createCardToken(cardEditor.getCard(), new CardToken.Callback() {
                @Override
                public void onSuccess(CardToken cardToken) {
                    // ...


                }
                @Override
                public void onError(Throwable throwable) {
                    // ...
                }
            });



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








}
