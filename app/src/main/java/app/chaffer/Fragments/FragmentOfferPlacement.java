package app.chaffer.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;

import static android.content.Context.MODE_PRIVATE;

public class FragmentOfferPlacement extends Fragment implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST=500;

    private double userCurrentLat ;
    private double userCurrentLong ;

    private LatLng packagePickupLocation ;
    private LatLng packageDropOffLocation ;

    private  MarkerOptions pickupMarker=null ;
    private  MarkerOptions deliverMarker=null ;

    private boolean isPickupLocationMarked=false ;
    private boolean isDeliveryLocationMarked=false ;

    private Button removeMaker ;
    private Button pickUpPoint ;
    private Button deliveryPoint ;
    private Button next ;
    SharedPreferences prefs ;

    EditText enterLocation ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_placement, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //getting prefrences
        prefs = getActivity().getSharedPreferences("User", MODE_PRIVATE);

        //initializing
        removeMaker=view.findViewById(R.id.btn_remover_markers) ;
        pickUpPoint=view.findViewById(R.id.btn_pickup_point) ;
        deliveryPoint=view.findViewById(R.id.btn_delivery_point) ;
        next=view.findViewById(R.id.btn_next) ;
        enterLocation=view.findViewById(R.id.editText_enterLocation_text) ;

        //setting listeners
        removeMaker.setOnClickListener(this);
        pickUpPoint.setOnClickListener(this);
        deliveryPoint.setOnClickListener(this);
        next.setOnClickListener(this);
       // enterLocation.setOnClickListener(this);

        //touch listener for edittext
        enterLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                FragmentLocationSearch locationSearch = new FragmentLocationSearch();
                changeToSecondFragment(locationSearch);

                return false ;
            }
        });


        //Pick up location
        Toast.makeText(getActivity(),"Long press on pickup location to set a marker",Toast.LENGTH_LONG).show();

        return view;
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting permissions
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},500);

        }
        mMap.setMyLocationEnabled(true);



        //On click listener for my location button for getting longtitude and latitude
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {


                try {
                    userCurrentLat = mMap.getMyLocation().getLatitude();
                    userCurrentLong = mMap.getMyLocation().getLongitude();

                    //Latlong
                    Log.d("LatLng",userCurrentLat+"  "+userCurrentLong) ;

                    Toast.makeText(getActivity(),mMap.getMyLocation().toString(),Toast.LENGTH_SHORT).show();

                }catch (Exception e){

                }

//                LatLng initialLocation = new LatLng(userCurrentLat, userCurrentLong);
//                mMap.addMarker(new MarkerOptions().position(initialLocation).title("Your initial  location"));



                return false;
            }
        });







        //on map long pressed click listerner to set markers on any position
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                if (!isPickupLocationMarked) {

                    pickupMarker = new MarkerOptions();
                    packagePickupLocation = latLng;
                    pickupMarker.title("Pick up location: \n"+latLng.latitude+" "+latLng.longitude) ;
                    pickupMarker.position(packagePickupLocation);
                    mMap.addMarker(pickupMarker) ;
                    isPickupLocationMarked=true ;


                    Toast.makeText(getActivity(),"Long press on Delivery location to set a marker",Toast.LENGTH_LONG).show();


                }else{
                    if (!isDeliveryLocationMarked) {
                        deliverMarker = new MarkerOptions();
                        packageDropOffLocation = latLng;


                        deliverMarker.position(packageDropOffLocation);
                        deliverMarker.title("Delivery location: " + latLng.latitude + " " + latLng.longitude);
                        deliverMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        mMap.addMarker(deliverMarker);
                        isDeliveryLocationMarked=true ;
                    }
                    }




                }




        } );


        // Add a marker in Sydney and move the camera
       LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }








    //Getting response of requested permission
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
                case LOCATION_REQUEST:
                    if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                         mMap.setMyLocationEnabled(true);



                    }
                    break;




        }
    }


    @Override
    public void onClick(View view) {

        if (view.getId()==deliveryPoint.getId()){
            if(deliverMarker!=null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(packageDropOffLocation));
            }
        }else if(view.getId()==pickUpPoint.getId())


        {
            if(pickupMarker!=null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(packagePickupLocation));
            }
        }


        else if(view.getId()==removeMaker.getId()){
            deliverMarker=null;
            pickupMarker=null ;
            mMap.clear();

            isPickupLocationMarked=false ;
            isDeliveryLocationMarked=false ;


        }

        else if(view.getId()==next.getId())
        {

            //
            //MainActivity.offerPlacementData.add("sadsa") ;

            if(deliverMarker!=null &&pickupMarker!=null) {

                FragmentOfferPlacementFinal offerFinal = new FragmentOfferPlacementFinal();

                changeToSecondFragment(offerFinal);

                //ID,desc,time to deliver,locationdescription,lat,lng

                //putting data of request in a static array list deifned in Main Activity

                //



                MainActivity.offerPlacementData.add(0,LoginActivity.userId);
                MainActivity.offerPlacementData.add(1,""+packagePickupLocation.latitude) ;
                MainActivity.offerPlacementData.add(2,""+packagePickupLocation.longitude) ;
                MainActivity.offerPlacementData.add(3,""+packageDropOffLocation.latitude) ;
                MainActivity.offerPlacementData.add(4,""+packageDropOffLocation.latitude) ;




            }
            else {
                Toast.makeText(getActivity(),"Make sure you have placed all markers correctly",Toast.LENGTH_SHORT).show(); ;
            }


        }




    }





    //Method to change fragment
    private void changeToSecondFragment(Fragment fragment){

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("offer") ;
        transaction.replace(R.id.layout,fragment);
        transaction.commit();

    }
}

