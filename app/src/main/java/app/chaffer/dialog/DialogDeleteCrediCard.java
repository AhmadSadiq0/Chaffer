package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.R;

import static android.content.Context.MODE_PRIVATE;
import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 07/05/2018.
 */

public class DialogDeleteCrediCard extends Dialog implements View.OnClickListener {
    Context context;
    TextView cardNum ;
    TextView userName ;
    TextView expiryDate ;
    ProgressBar progressBar ;
    Button confirm ;
    String urlToDeletePayment= LoginActivity.IP+"/users/addpayment" ;

    SharedPreferences.Editor editor ;
    SharedPreferences preferences ;

    public DialogDeleteCrediCard(@NonNull Context context) {
        super(context);
        this.context=context ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_delete_card);

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

        Log.d("card_number",preferences.getString("card_num", null + ""));





    }



    @Override
    public void onClick(View view) {
        if (view.getId()==confirm.getId()){
            deletePaymentRequest();
        }
    }



    //Push payment request
    private void deletePaymentRequest(){
        //Progress bar Visible
        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.PUT, urlToDeletePayment
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {
                            JSONObject object = new JSONObject(response.toString());

                            if (object.getString("valid").equals("yes")){

                                Toast.makeText(getContext(),"Payment method has been deleted",Toast.LENGTH_SHORT).show();

                                editor.putString("card_num","") ;
                                editor.putString("cvc","") ;
                                editor.putString("exp_month","") ;
                                editor.putString("exp_year","") ;
                                editor.commit() ;


                                //adding card detials to shared prefrences
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
