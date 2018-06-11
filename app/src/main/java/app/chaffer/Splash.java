package app.chaffer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;
import static app.chaffer.LoginActivity.userName;

/**
 * Created by Mac on 14/12/2017.
 */

public class Splash extends Activity {
    /**
     * Duration of wait
     **/

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences prefs;
    private String url = LoginActivity.IP + "/users/secret";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        //StartSercie
        startService(new Intent(this,LocationService.class)) ;

        //getting prefrences
        try {
            prefs = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);

            token = prefs.getString("tk", "");

        }
        catch (Exception e){

        }



      //  Log.d("splash_token", prefs.getString("tk", null));


        if (!token.equals("") ) {




                //Sending string request to verify header

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals(null)) {
                            Log.d("response", response);

                            try {
                                JSONObject obj = new JSONObject(response.toString());

                                if (obj.getString("status").equals("true") &&  obj.getString("info").equals("filled")) {

                                    //getting id
                                    userId=obj.getString("id") ;
                                    userName=obj.getString("name") ;

                                    Toast.makeText(getApplicationContext(), "Welcome back!!", Toast.LENGTH_SHORT).show();

                                    //Changing activity
                                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();

                                }else if(obj.getString("status").equals("true") &&  obj.getString("info").equals("unfilled")){
                                    //getting id

                                    userId=obj.getString("id") ;
                                    userName=obj.getString("name") ;


                                    //Changing activity
                                    Intent mainIntent = new Intent(Splash.this, SignUpUserForm.class);
                                    startActivity(mainIntent);
                                    finish();

                                }
                                else {
                                    //Changing activity
                                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            } catch (Exception e) {

                            }


                        }
                        else {
                            Log.e("response", "Data Null");
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error is ", "" + error);
                        Toast.makeText(getApplicationContext(), "Sorry there is an error", Toast.LENGTH_SHORT).show();

                        finish();

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

                    //Pass Your Parameters here

                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

            }


        else {
            Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();

        }








    }
}