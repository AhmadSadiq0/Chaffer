package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
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

import app.chaffer.MainActivity;
import app.chaffer.R;

import static app.chaffer.LoginActivity.IP;
import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 25/04/2018.
 */

public class DialogMarkOrderComplete extends Dialog implements View.OnClickListener{

    Button btnClose ;
    Button submit ;
    String url=IP+"/users/review" ;
    //String urlMarkOrder=IP+"/order/"+MainActivity.selectedReceivingOrderFromList.getOrderId() ;
    ProgressBar progressBar ;
    EditText comments;
    RatingBar ratingBar ;
    int ratings ;

    RequestQueue queue ;


    public DialogMarkOrderComplete(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_mark_order);

        queue = Volley.newRequestQueue(getContext());

        ratingBar=(RatingBar)findViewById(R.id.ratingBar) ;
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                ratings=(int)v ;
            }
        });

        btnClose=(Button)findViewById(R.id.close) ;
        btnClose.setOnClickListener(this);
        submit=(Button)findViewById(R.id.btn_submit) ;
        submit.setOnClickListener(this);

        comments=(EditText)findViewById(R.id.eidtext_comment) ;

        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;




    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnClose.getId()) {
            dismiss();
        } else if (view.getId() == submit.getId()) {

            if (!comments.getText().toString().equals("")) {
                //putting data of request in a static array list defined in Main Activity

                //Displaying progress bar
                progressBar.setVisibility(View.VISIBLE);

                //Seding request
                 queue = Volley.newRequestQueue(getContext());


                Map<String, String> postParam = new HashMap<String, String>();

                postParam.put("fkuser_id", MainActivity.selectedReceivingOrderFromList.getDeliveryPersonId());
                postParam.put("comments", comments.getText().toString());
                postParam.put("rating", ratings + "");
                postParam.put("fkorder_id", MainActivity.selectedReceivingOrderFromList.getOrderId());


//                For testing purpose only
                JSONObject jsonObject = new JSONObject(postParam);
                Log.d("mark_order", jsonObject.toString());


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST, url
                        , new JSONObject(postParam),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("mark_order", response.toString());

                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    if (object.getString("review").equals("done")) {

                                        Toast.makeText(getContext(), "Order has been marked as complete", Toast.LENGTH_LONG).show();

                                        //Disabling
                                        progressBar.setVisibility(View.GONE);

                                        dismiss();


                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "Sorry there is an error", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(), "Error occurred!Please try again", Toast.LENGTH_LONG).show();


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
                Toast.makeText(getContext(), "Kindly write comment", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
