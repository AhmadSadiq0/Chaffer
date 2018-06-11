package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.chaffer.Inbox;
import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.Offer;
import app.chaffer.R;
import app.chaffer.Request;
import app.chaffer.adapter.InboxMessageListAdapter;
import app.chaffer.adapter.OfferListAdapter;

import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userName;
import static app.chaffer.MainActivity.chatHistoryList;

/**
 * Created by Mac on 07/05/2018.
 */

public class FragmentInbox extends Fragment {
    private String url= LoginActivity.IP+"/users/chathistory" ;
    ProgressBar progressBar ;
    InboxMessageListAdapter adapter ;
    RecyclerView recyclerView ;
    FragmentTransaction ft ;
   // ArrayList<Inbox> chatHistory=new ArrayList<>() ;
    String otherPersonUserName ;
    String messageBoxName ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inbox,container,false) ;

        progressBar=(ProgressBar)view.findViewById(R.id.progressBar) ;
        recyclerView=(RecyclerView)view.findViewById(R.id.recylerview_inbox) ;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        prepareData();


        return view ;
    }

    //Prepare data to fetch
    void prepareData(){




        //Seding request
        RequestQueue queue = Volley.newRequestQueue(getActivity());






        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,url
                , new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_inboxList", response.toString());

                        try {
                            //Cleaning the array
                            chatHistoryList.clear();

                            JSONArray array = new JSONArray(response.getString("box1"));
                            {
                                //Steps to parse JSON data
                                JSONArray messageArray=array.getJSONArray(0) ;
//                                Log.d("response_inboxList1",messageArray.toString()) ;
//                                Log.d("response_inboxList2",message.toString()) ;

                                for (int i=0;i<messageArray.length();i++) {

                                    JSONObject message=messageArray.getJSONObject(i) ;
                                    JSONObject data=message.getJSONObject("row_to_json") ;


                                    if (data.getString("name1").equals(userName)){
                                        otherPersonUserName=data.getString("name2") ;
                                    }else{
                                        otherPersonUserName=data.getString("name1") ;
                                    }


                                    Inbox inbox =new Inbox(otherPersonUserName,data.getString("box_name")) ;



                                    chatHistoryList.add(inbox) ;

                                    Log.d("response_inboxList", MainActivity.chatHistoryList.size()+"") ;


                                }

                                Toast.makeText(getActivity(), "Data fetched", Toast.LENGTH_LONG).show();

                                //Fetching data and setting adapter
                                  adapter=new InboxMessageListAdapter( chatHistoryList,getActivity(),ft) ;
                                    recyclerView.setAdapter(adapter);




                            }

                        }catch (Exception e){
//                             Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                            Log.d( "Error_inboxList" , e.toString());

                        }


                        //Hiding progress bar

                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "Error_inboxList" , error.toString());

                Toast.makeText(getActivity(),"Error occured!Please try again",Toast.LENGTH_LONG).show();


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
