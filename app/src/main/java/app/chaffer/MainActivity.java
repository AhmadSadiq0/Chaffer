package app.chaffer;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.chaffer.Fragments.FragmentHome;
import app.chaffer.Fragments.FragmentLocationSearch;
import app.chaffer.Fragments.FragmentMessagesList;
import app.chaffer.Fragments.FragmentOfferList;
import app.chaffer.Fragments.FragmentOfferPlacement;
import app.chaffer.Fragments.FragmentViewOfferDetails;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;
    private  FragmentHome home;
    public static ArrayList<String> offerPlacementData=new ArrayList<>() ;
    public static Offer selectedOfferFromOfferFeed ;
    //public static int currentOfferfeedItemSelected ;

//    public  Context context=getApplicationContext() ;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //On touch for home fragment
                   FragmentHome home=new FragmentHome() ;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, home).commit();
                    return true;

                case R.id.navigation_inbox:
                   // mTextMessage.setText("Inbox");

//                    FragmentOfferPlacement order=new FragmentOfferPlacement() ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Setting home as default layout
        home=new FragmentHome() ;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout, home).addToBackStack("home").commit();


    }





}
