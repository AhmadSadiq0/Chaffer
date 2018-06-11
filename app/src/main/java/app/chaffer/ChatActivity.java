package app.chaffer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.adapter.MessageAdapter;

import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;
import static app.chaffer.LoginActivity.userName;
import static app.chaffer.MainActivity.chatTag;
import static app.chaffer.MainActivity.messageBoxName;

/**
 * Created by Mac on 04/05/2018.
 */

public class ChatActivity extends Activity {

    EditText chatMessage ;
    Button send ;
    ProgressBar progressBar ;
    //String messageTimeAndDate =new Date().getTime() +"";
    private ListView listView;

    FirebaseDatabase firebaseDatabase ;
     DatabaseReference ref ;
    private FirebaseListAdapter<ChatMessage> adapter;


    String url=LoginActivity.IP+"/users/chat" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        listView=(ListView)findViewById(R.id.list) ;
        chatMessage=(EditText)findViewById(R.id.mesage_text) ;
        send=(Button) findViewById(R.id.fab) ;
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;


        //First id =delivery person
        //Second id=receiving person
        //User1=>delivery User2=>Receiving
        if (chatTag.equals("offer")){

            messageBoxName=MainActivity.selectedOfferFromOffersList.getSenderID()+userId ;

            sendChatBoxNameToServer(MainActivity.selectedOfferFromOffersList.getSenderID(),userId,messageBoxName);

        }else if (chatTag.equals("deliveryOrder")){

            messageBoxName=userId+MainActivity.selectedDeliveryOrderFromList.getReceiverId() ;
            sendChatBoxNameToServer(userId,MainActivity.selectedDeliveryOrderFromList.getReceiverId(),messageBoxName);



        }else if (chatTag.equals("receivingOrder")){
            messageBoxName=MainActivity.selectedReceivingOrderFromList.getDeliveryPersonId()+userId ;
            sendChatBoxNameToServer(MainActivity.selectedReceivingOrderFromList.getDeliveryPersonId(),userId,messageBoxName);

        }else {
            messageBoxName=MainActivity.currentSelectChatFromInbox.getBoxName();

        }


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance() ;
        ref= firebaseDatabase.getReference().child(messageBoxName) ;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chatMessage.getText().toString().equals("")) {
                    ref.push().setValue(new ChatMessage(chatMessage.getText().toString(), userName + ""));
                    chatMessage.setText("");
                }
            }
        });


        showAllOldMessages();
    }




    private void showAllOldMessages( ) {

        MainActivity activity=new MainActivity();
        adapter = new MessageAdapter(activity, ChatMessage.class, R.layout.item_in_message, ref,this);
        listView.setAdapter(adapter);
    }


    //
    private void sendChatBoxNameToServer(String user1,String user2,String messageBoxName){
        //Seding request

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam = new HashMap<String, String>();
        //First id =delivery person
        //Second id=receiving person
        postParam.put("fkuser_id1",user1);
        postParam.put("fkuser_id2",user2);
        postParam.put("box_name", messageBoxName);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url
                , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response_offerList", response.toString());

                        try {
                            if (response.getString("done").equals("yes")){

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);


                    }



                        //Hiding progress bar


                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "Error_offerlist" , error.toString());




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


