package app.chaffer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.chaffer.Fragments.FragmentHome;
import app.chaffer.Fragments.FragmentMore;
import app.chaffer.Fragments.FragmentNotifications;

import static app.chaffer.LoginActivity.IP;
import static app.chaffer.LoginActivity.token;
import static app.chaffer.LoginActivity.userId;


public class MainActivity extends FragmentActivity {


    private TextView mTextMessage;
    private  FragmentHome home;
    public static ArrayList<String> requestPlacementData=new ArrayList<>() ;
    public static Request selectedOfferFromRequestFeed;
    public static Offer selectedOfferFromOffersList ;
    public static DeliveryOrder selectedDeliveryOrderFromList ;
    public static ReceivingOrder selectedReceivingOrderFromList ;

    public static ArrayList<String> postedOfferAgainstRequestList=new ArrayList<>() ;

    public static ArrayList<DeliveryOrder> deliveryOrders=new ArrayList<>() ;
    public static ArrayList<ReceivingOrder> receivingOrders=new ArrayList<>() ;

    FragmentManager fm =this.getSupportFragmentManager() ;

      BroadcastReceiver broadcastReceiver ;
      public static double lat ;
      public static  double lng ;
      ProgressBar progressBar;
      public static LatLng requestPickupLatLng ;
      public static LatLng requestDeliveryLatLng ;

      private boolean isLocationFetched=false ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance() ;
        final DatabaseReference ref= firebaseDatabase.getReference().child(userId) ;
        final Map<String,String> location=new HashMap<>() ;
        location.put("lat","33.656320478941716") ;
        location.put("lng","73.15705857869006") ;
        ref.setValue(location) ;





        progressBar=(ProgressBar) findViewById(R.id.progressBar) ;

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        startService(new Intent(this,MyFirebaseInstanceIDService.class)) ;

        FirebaseMessaging.getInstance().subscribeToTopic("all");


        //Receiving broadcast from Location service
        if(broadcastReceiver==null){


            broadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                        lat = Double.parseDouble(intent.getExtras().get("lat").toString());
                        lng = Double.parseDouble(intent.getExtras().get("lng").toString());

                        //setting current lat lng values in hashmap and pushing it to firebase
                        location.put("lat",lat+"") ;
                        location.put("lng",lng+"") ;
                        //pushing values to firebase
                        ref.setValue(location) ;

                        //fetching location at the start of application
                        if (!isLocationFetched) {
                            if(lat!=0.0) {
                                //Setting home as default layout
                            home = new FragmentHome();
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.layout, home).commit();
                            isLocationFetched = true;

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }
            };

        } registerReceiver(broadcastReceiver,new IntentFilter("location_update")) ;




        Log.d("latlngM",lat+" "+lng ) ;




        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Checking whether location is fetched or not
                    if(isLocationFetched) {
                        //poping out fragment from backstack
                        fm.popBackStack();
                        //On touch for home fragment
                        FragmentHome home = new FragmentHome();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout, home).commit();

                    }


                    return true;

                case R.id.navigation_inbox:


                    return true;
                case R.id.navigation_explore:
                    //mTextMessage.setText("Explore");
                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText("Notification");
                    FragmentNotifications notifications = new FragmentNotifications();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, notifications).commit();
                    return true;
                case R.id.navigation_more:
                    FragmentMore more = new FragmentMore();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, more).commit();
                    return true;
            }
            return false;


        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }











}
