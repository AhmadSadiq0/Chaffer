package app.chaffer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import static app.chaffer.LoginActivity.userId;


/**
 * Created by Mac on 14/12/2017.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    Button submit ;
    EditText emailText ;
    EditText passwordText ;
    EditText userNameText ;
    EditText firstNameText ;
    EditText lastNameText ;
    EditText textCnic ;
    EditText textPhoneNumber ;
    ProgressBar progressBar ;
    SharedPreferences.Editor editor ;
    SharedPreferences prefs=this.getSharedPreferences("User", MODE_PRIVATE) ;
    private String urlFireToken = LoginActivity.IP + "/users/frtoken";
    SharedPreferences preferences ;




    private String signUpUrl=LoginActivity.IP+"/users/signup" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        editor = prefs.edit();


        //Intializing
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        submit=(Button) findViewById(R.id.submit_signup) ;
        emailText=(EditText) findViewById(R.id.text_email) ;
        userNameText=(EditText) findViewById(R.id.text_username) ;
        //firstNameText=(EditText) findViewById(R.id.text_first_name) ;
        //lastNameText=(EditText) findViewById(R.id.text_last_name) ;
        // textCnic=(EditText) findViewById(R.id.text_cnic) ;
        //textPhoneNumber=(EditText) findViewById(R.id.text_phoneNumber) ;
        passwordText=(EditText) findViewById(R.id.text_password) ;

        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==submit.getId()){

            //Progress bar Visible
            progressBar.setVisibility(View.VISIBLE);
            //Setting progressbar color to white


            RequestQueue queue = Volley.newRequestQueue(this);



            Map<String, String> postParam= new HashMap<String, String>();
            // postParam.put("user_name", userNameText.getText().toString());
            postParam.put("email", emailText.getText().toString());
            postParam.put("password", passwordText.getText().toString());



            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,signUpUrl
                    , new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", response.toString());

                            try {
                                JSONObject object = new JSONObject(response.toString());

                                if(!object.getString("tk").equals("")){

                                    //static varibale for user id
                                    userId=object.getString("id") ;


                                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();


                                    //putting user id in shared prefrences
                                    editor.putString("tk",object.getString("tk")) ;

                                    editor.commit();
                                  sendRegistrationToServer(preferences.getString("fire_token",null));



                                    //Starting main activity
                                    Intent intent=new Intent(SignupActivity.this,SignUpUserForm.class) ;
                                    startActivity(intent);
                                    finish();

                                }

                            }catch (Exception e){
                                // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                            }


                            //Hiding progress bar
                            progressBar.setVisibility(View.GONE);

                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d( "Error: " + error.getMessage());

                    Toast.makeText(getApplicationContext(),"Error occured!Please try again",Toast.LENGTH_LONG).show();


                    //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                    //Hiding progress bar
                    progressBar.setVisibility(View.GONE);


                }
            }) {


            };

            jsonObjReq.setTag("json");
            // Adding request to request queue
            queue.add(jsonObjReq);

            // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

        }

    }




    //Method to getFirebase token
    private void sendRegistrationToServer(String token) {

        // Update Token in database
        //Seding request

        RequestQueue queue = Volley.newRequestQueue(this);


        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("fkuser_id", LoginActivity.userId);
        postParam.put("token",token);


//                For testing purpose only
        JSONObject jsonObject=new JSONObject(postParam) ;
        Log.d("obj",jsonObject.toString()) ;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, urlFireToken
                , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response", response.toString());

                        try {
                            JSONObject object = new JSONObject(response.toString());
                            if (object.getString("token").equals("updated")) {
                            }

                        } catch (Exception e) {
                            // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
            }

        }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("authorization", LoginActivity.token);
                return params;
            }
        };

        jsonObjReq.setTag("json");
        // Adding request to request queue
        queue.add(jsonObjReq);
    }
}