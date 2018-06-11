package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.Inbox;
import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;
import app.chaffer.RatingsAndComments;
import app.chaffer.adapter.InboxMessageListAdapter;
import app.chaffer.adapter.RatingAndCommentsAdapter;

import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userName;
import static app.chaffer.MainActivity.chatHistoryList;
import static app.chaffer.MainActivity.ratingsAndComments;

/**
 * Created by Mac on 08/05/2018.
 */

public class DialogViewRatings extends Dialog {

    public String url= LoginActivity.IP+"/users/getreviews/"+MainActivity.selectedOfferFromOffersList.getOfferid() ;
    ProgressBar progressBar ;
    TextView username;
    Context context ;
    RecyclerView recyclerView ;
    RatingAndCommentsAdapter adapter ;


    public DialogViewRatings(@NonNull Context context) {

        super(context);
        this.context=context ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_rating_comment);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_rating_comments);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;
        username=(TextView)findViewById(R.id.user_name) ;


        username.setText(MainActivity.selectedOfferFromOffersList.getSenderName());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareData();





    }

    private void prepareData(){

        //Progress bar Visible
        progressBar.setVisibility(View.VISIBLE);

        ratingsAndComments.clear();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response_user_reivew_comment", response.toString());

                        try {
                            JSONArray array = new JSONArray(response.getString("rat"));

                                //Steps to parse JSON data
                                JSONArray messageArray=array.getJSONArray(0) ;
//                                Log.d("response_inboxList1",messageArray.toString()) ;
//                                Log.d("response_inboxList2",message.toString()) ;

                                for (int i=0;i<messageArray.length();i++) {

                                    JSONObject message=messageArray.getJSONObject(i) ;
                                    JSONObject data=message.getJSONObject("row_to_json") ;


                                    Log.d("commet",data.toString()) ;


                                    RatingsAndComments rA=new RatingsAndComments(data.getString("rating"),data.getString("comments"));

                                    ratingsAndComments.add(rA) ;






                                }

                                Toast.makeText(context, "Data fetched", Toast.LENGTH_LONG).show();

                             //   Fetching data and setting adapter
                                adapter=new RatingAndCommentsAdapter( ratingsAndComments,context) ;
                                recyclerView.setAdapter(adapter);



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
