package app.chaffer.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.chaffer.MainActivity;
import app.chaffer.R;
import app.chaffer.dialog.DialogMarkOrderComplete;

/**
 * Created by Mac on 03/03/2018.
 */

public class FragmentViewReceivingOrderDetails extends Fragment implements OnMapReadyCallback, RoutingListener,View.OnClickListener {
    private GoogleMap mMap;
    LatLng deliveryPerson;
    LatLng delivery;
    MarkerOptions deliveryPersonMarker;
    MarkerOptions deliveryMarker;
    Marker deliverPersonLocation ;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light,R.color.cardview_dark_background};

    private  TextView routeText,distanceText,timeText ;
    private LinearLayout details_layout ;
    private ProgressBar progressBar ;

      public static String deliveryPersonLat =null;
      public static String deliverPersonLng=null ;

      Button btnMarkOrderComplete ;

    BroadcastReceiver fragmentBroadcastReceiver ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view_receiving_orders, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btnMarkOrderComplete=(Button)view.findViewById(R.id.btn_mark_order_complete) ;
        btnMarkOrderComplete.setOnClickListener(this);



        //Receiving broadcast from Location service
        if(fragmentBroadcastReceiver==null){


            fragmentBroadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    deliverPersonLocation.setPosition(new LatLng(Double.parseDouble(deliveryPersonLat), Double.parseDouble(deliverPersonLng)));

                    Log.d("animate",""+deliveryPersonLat+deliverPersonLng) ;


                }
            };

        } getContext().registerReceiver(fragmentBroadcastReceiver,new IntentFilter("location_update")) ;




        routeText=(TextView) view.findViewById(R.id.route) ;
        distanceText=(TextView) view.findViewById(R.id.distance) ;
        timeText=(TextView) view.findViewById(R.id.time) ;
        details_layout=(LinearLayout) view.findViewById(R.id.details_layout) ;
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar) ;

        polylines = new ArrayList<>();



        //Getting lat lng from service
        Log.d("latlngMM",MainActivity.lat+" "+MainActivity.lng ) ;



        return view ;
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Requesting permissions
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 500);

            }
            mMap.setMyLocationEnabled(true);

            // delivery person lat long
            Double delivertPersonLat1 = Double.parseDouble(deliveryPersonLat);
            Double deliveryPersonLng1 = Double.parseDouble(deliverPersonLng);

            //Delivery lat long
            Double deliverylat = Double.parseDouble(MainActivity.selectedReceivingOrderFromList.getDesLat());
            Double deliverylon = Double.parseDouble(MainActivity.selectedReceivingOrderFromList.getDesLng());

            //    Delivery person lat and lng
            deliveryPerson = new LatLng(delivertPersonLat1, deliveryPersonLng1);
            deliveryPersonMarker = new MarkerOptions();
            deliverPersonLocation= mMap.addMarker(deliveryPersonMarker.position(deliveryPerson).title("Delivery person"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(delivertPersonLat1, deliveryPersonLng1), 15));

            //Delivery marker

            delivery = new LatLng(deliverylat, deliverylon);
            Log.d("deliver", delivery.latitude + " " + delivery.longitude);
            deliveryMarker = new MarkerOptions();
            deliveryMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(deliveryMarker.position(delivery).title("Drop off : " + MainActivity.selectedReceivingOrderFromList.getDesDes()));


            //Going to current location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MainActivity.lat, MainActivity.lng), 15));



            //getting route

              getRoutToMarker(deliveryPerson,delivery);



    }












    //method to draw route

    private void getRoutToMarker(LatLng start,LatLng end)  {


            Routing routing = new Routing.Builder().
                    travelMode(AbstractRouting.TravelMode.DRIVING).
                    withListener(this).alternativeRoutes(false).waypoints(start, end).build();

            routing.execute();






    }

    //Methods to handle routes success and failure etc

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {


        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getActivity(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();


            //disabling progress bar
            progressBar.setVisibility(View.GONE);

            //setting route details
            details_layout.setVisibility(View.VISIBLE) ;
            routeText.setText("Route " + (i + 1));
            distanceText.setText(""+route.get(i).getDistanceValue()/1000+"KM");
            timeText.setText(""+route.get(i).getDurationValue()/60+"Min");


        }
    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onClick(View view) {

        if (view.getId()==btnMarkOrderComplete.getId()){
            DialogMarkOrderComplete markOrderComplete =new DialogMarkOrderComplete(getContext()) ;
            markOrderComplete.show();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(fragmentBroadcastReceiver);
    }

    
}
