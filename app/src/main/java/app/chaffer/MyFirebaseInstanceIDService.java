package app.chaffer;

/**
 * Created by Mac on 06/03/2018.
 */

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;

import static app.chaffer.LoginActivity.token;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIDService";
    private String url = LoginActivity.IP + "/users/frtoken";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server
        if(refreshedToken == null){
            Log.d("NoToken", "Refreshed token: ");
        }

        if (LoginActivity.userId != null && LoginActivity.token != null) {
            sendRegistrationToServer(refreshedToken);
        }
        else{
            SharedPreferences.Editor editor = this.getSharedPreferences("User", MODE_PRIVATE) .edit();
            editor.putString("fire_token",refreshedToken) ;
            editor.commit() ;

        }

        // Update Token in database
        //Seding request
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//
//        Map<String, String> postParam = new HashMap<String, String>();
//
//        postParam.put("fkuser_id", MainActivity.selectedOfferFromRequestFeed.getUserId());
//        postParam.put("firebase_token", refreshedToken);
//
//
////                For testing purpose only
//        JSONObject jsonObject=new JSONObject(postParam) ;
//        Log.d("obj",jsonObject.toString()) ;
//
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
//                , new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.d("response", response.toString());
//
//                        try {
//                            JSONObject object = new JSONObject(response.toString());
//                            if (object.getString("token").equals("updated")) {
//                            }
//
//                        } catch (Exception e) {
//                            // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Error: " + error.getMessage());
//            }
//
//        }) {
//
//            //This is for Headers If You Needed
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                //  params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("authorization", token);
//                return params;
//            }
//        };
//
//        jsonObjReq.setTag("json");
//        // Adding request to request queue
//        queue.add(jsonObjReq);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        // Update Token in database
        //Seding request

        RequestQueue queue = Volley.newRequestQueue(this);


        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("fkuser_id", LoginActivity.userId);
        postParam.put("token", token);


//                For testing purpose only
        JSONObject jsonObject=new JSONObject(postParam) ;
        Log.d("obj",jsonObject.toString()) ;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
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