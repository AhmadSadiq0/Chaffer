package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;
import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;

/**
 * Created by Mac on 14/04/2018.
 */

public class DialogAddPaymentMethod extends Dialog implements View.OnClickListener  {

    Button addPayment ;
    String urlToAddPayment= LoginActivity.IP+"/users/addpayment" ;

    ProgressBar progressBar ;
    RadioButton onlineDeliverRadio ;
  //  RadioButton cashOnDelivery ;

    CardEditor cardEditor ;
    Simplify simplify;
    CardToken card_Token ;

    SharedPreferences.Editor editor ;
    SharedPreferences preferences ;

    Context context ;


    public DialogAddPaymentMethod(@NonNull Context context) {
        super(context);
        this.context=context ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_add_payment_method);

        editor = context.getSharedPreferences("User", MODE_PRIVATE) .edit();
        preferences=context.getSharedPreferences("User",MODE_PRIVATE) ;


        cardEditor = (CardEditor) findViewById(R.id.card_editor);

        onlineDeliverRadio=(RadioButton) findViewById(R.id.radio_online_paymet) ;

        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;


        addPayment=(Button)findViewById(R.id.add_payment) ;
        addPayment.setOnClickListener(this);






        cardEditor.addOnStateChangedListener(new CardEditor.OnStateChangedListener() {
            @Override
            public void onStateChange(CardEditor cardEditor) {
                addPayment.setEnabled(cardEditor.isValid());


            }
        });






    }





    @Override
    public void onClick(View view) {

        if (view.getId()==addPayment.getId()){

            pushPaymentRequest(cardEditor.getCard().getNumber(),cardEditor.getCard().getCvc(),cardEditor.getCard().getExpMonth(),cardEditor.getCard().getExpYear());





        }


    }


    //Push payment request
    private void pushPaymentRequest(final String cardNum, final String cvc, final String exp_month, final String exp_year){
        //Progress bar Visible
        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(getContext());


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("fkuser_id", userId) ;
        postParam.put("card_num",cardNum) ;
        postParam.put("cvc",cvc) ;
        postParam.put("exp_month",exp_month) ;
        postParam.put("exp_year",exp_year) ;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, urlToAddPayment
                , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {
                            JSONObject object = new JSONObject(response.toString());

                            if (object.getString("valid").equals("yes")){
                                Toast.makeText(getContext(),"Payment method added",Toast.LENGTH_SHORT).show();

                                //adding card detials to shared prefrences
                                editor.putString("card_num",cardNum) ;
                                editor.putString("cvc",cvc) ;
                                editor.putString("exp_month",exp_month) ;
                                editor.putString("exp_year",exp_year) ;
                                editor.commit() ;

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
