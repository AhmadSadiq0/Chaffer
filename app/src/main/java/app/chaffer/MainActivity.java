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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import app.chaffer.Fragments.FragmentHome;


public class MainActivity extends FragmentActivity {


    private TextView mTextMessage;
    private  FragmentHome home;
    public static ArrayList<String> requestPlacementData=new ArrayList<>() ;
    public static Request selectedOfferFromRequestFeed;

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

        progressBar=(ProgressBar) findViewById(R.id.progressBar) ;

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        startService(new Intent(this,MyFirebaseInstanceIDService.class)) ;

        //Receiving broadcast from Location service
        if(broadcastReceiver==null){


            broadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                        lat = Double.parseDouble(intent.getExtras().get("lat").toString());
                        lng = Double.parseDouble(intent.getExtras().get("lng").toString());

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
                   // mTextMessage.setText("Inbox");

//                    FragmentRequestPlacement order=new FragmentRequestPlacement() ;
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.layout, order).commit();

                    return true;
                case R.id.navigation_explore:
                    //mTextMessage.setText("Explore");
                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText("Notification");
                    return true;
                case R.id.navigation_more:
                  //  mTextMessage.setText("More");
                    return true;
            }
            return false;


        }
    };




}
