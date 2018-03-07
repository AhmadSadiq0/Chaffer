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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mac on 14/12/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button submit ;
    EditText loginText ;
    EditText passwordText ;
    TextView createAccount ;
    public static String IP="http://192.168.0.124:3000" ;
    private String loginUrl=IP+"/users/signin" ;
    ProgressBar progressBar ;
    private String urlFireToken = LoginActivity.IP + "/users/frtoken";

    public static String token;

    public static String userId;



    SharedPreferences.Editor editor ;
    SharedPreferences preferences ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editor = this.getSharedPreferences("User", MODE_PRIVATE) .edit();
         preferences=this.getSharedPreferences("User",MODE_PRIVATE) ;


        submit=(Button) findViewById(R.id.submit_login) ;
        loginText=(EditText) findViewById(R.id.text_email) ;
        passwordText=(EditText) findViewById(R.id.text_password) ;
        createAccount=(TextView)findViewById(R.id.create_account) ;
        createAccount.setOnClickListener(this);
        progressBar=findViewById(R.id.progressBar) ;



        submit.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {


        if (view.getId() == submit.getId()) {

            SharedPreferences prefs = getSharedPreferences("User", MODE_PRIVATE);



            //Progress bar Visible
            progressBar.setVisibility(View.VISIBLE);


            RequestQueue queue = Volley.newRequestQueue(this);


            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("email", loginText.getText().toString());
            postParam.put("password", passwordText.getText().toString());


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, loginUrl
                    , new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", response.toString());

                            try {
                                JSONObject object = new JSONObject(response.toString());

                                if (!object.getString("tk").equals("") && object.getString("info").equals("filled")) {


                                    //getting user id
                                    userId=object.getString("id") ;


                                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();

                                    //putting user id in shared prefrences
                                    editor.putString("tk", object.getString("tk"));
                                    editor.commit();


                                    //getting token

                                    token = object.getString("tk");


                                    Log.d("login_token", object.getString("tk"));


                                    //Firebase token
                                    sendRegistrationToServer( token);


                                    //Starting main activity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                                else if(!object.getString("tk").equals("") &&object.getString("info").equals("unfilled")) {



                                    //getting user id
                                    userId=object.getString("id") ;

                                    //getting token
                                    token = object.getString("tk");

                                    //Firebase token
                                    sendRegistrationToServer( token);


                                    Intent intent = new Intent(LoginActivity.this, SignUpUserForm.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                            }


                            //Hiding progress bar
                            progressBar.setVisibility(View.GONE);

                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: " + error.getMessage());

                    Toast.makeText(getApplicationContext(), "Error occured!Please try again", Toast.LENGTH_LONG).show();


                    //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                    //Hiding progress bar
                    progressBar.setVisibility(View.GONE);


                }
            }) {




            };

            jsonObjReq.setTag("json");
            // Adding request to request queue
            queue.add(jsonObjReq);

        }




        //Other buttons
        else if (view.getId() == createAccount.getId()) {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }







    }




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
                params.put("authorization", preferences.getString("tk",null));
                return params;
            }
        };

        jsonObjReq.setTag("json");
        // Adding request to request queue
        queue.add(jsonObjReq);
    }

}




