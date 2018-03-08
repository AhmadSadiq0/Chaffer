package app.chaffer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.*;
import app.chaffer.Fragments.FragmentHome;
import app.chaffer.R;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 07/03/2018.
 */


public class DialogPlaceOffer extends Dialog implements View.OnClickListener{
    Button btnClose ;
    Button btnPlaceOffer ;
    Context context ;
    private String url = LoginActivity.IP + "/users/offer";
    ProgressBar progressBar ;


    public DialogPlaceOffer(@NonNull Context context) {

        super(context);
        this.context=context ;
    }

    EditText description ;
    EditText time ;
    EditText amount ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(app.chaffer.R.layout.custom_dialog_place_offer);

        btnClose=(Button) findViewById(R.id.close) ;
        btnPlaceOffer=(Button)findViewById(R.id.place_offer) ;
        btnClose.setOnClickListener(this);
        btnPlaceOffer.setOnClickListener(this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        description=(EditText) findViewById(R.id.editText_description) ;
        amount=(EditText)findViewById(R.id.editText_amount) ;
        time=(EditText)findViewById(R.id.editText_time) ;



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnClose.getId()) {
            dismiss();
        } else if (view.getId() == btnPlaceOffer.getId()) {

            if (!description.getText().toString().equals("") && !amount.getText().toString().equals("")
                    && !time.getText().toString().equals("")) {
                //putting data of request in a static array list defined in Main Activity

                //Displaying progress bar
                progressBar.setVisibility(View.VISIBLE);

                //Seding request
                RequestQueue queue = Volley.newRequestQueue(context);


                Map<String, String> postParam = new HashMap<String, String>();

                postParam.put("fkuser_id", MainActivity.selectedOfferFromRequestFeed.getUserId());
                postParam.put("fkrequest_id", MainActivity.selectedOfferFromRequestFeed.getRequestId());
                postParam.put("description", description.getText().toString());
                postParam.put("time_suggested", time.getText().toString());
                postParam.put("amount", amount.getText().toString());


//                For testing purpose only
                JSONObject jsonObject = new JSONObject(postParam);
                Log.d("obj", jsonObject.toString());


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST, url
                        , new JSONObject(postParam),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("response", response.toString());

                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    if (object.getString("offer").equals("posted")) {

                                        Toast.makeText(context, "Request Posted", Toast.LENGTH_LONG).show();

                                        //Disabling
                                        progressBar.setVisibility(View.GONE);

                                        dismiss();


                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Sorry there is an error", Toast.LENGTH_LONG).show();

                                    //Disabling
                                    progressBar.setVisibility(View.GONE);

                                }


                            }
                        }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: " + error.getMessage());

                        //Disabling
                        progressBar.setVisibility(View.GONE);

                        //Toast
                        Toast.makeText(context, "Error occurred!Please try again", Toast.LENGTH_LONG).show();


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
            } else {
                Toast.makeText(context, "Kindly provide required descriptions", Toast.LENGTH_LONG).show();
            }


        }
    }


}
