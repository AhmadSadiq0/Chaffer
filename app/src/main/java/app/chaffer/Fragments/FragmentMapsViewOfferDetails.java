package app.chaffer.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import app.chaffer.MainActivity;
import app.chaffer.R;

/**
 * Created by Mac on 03/03/2018.
 */

public class FragmentMapsViewOfferDetails extends Fragment implements OnMapReadyCallback, RoutingListener {
    private GoogleMap mMap;
    LatLng pickUP;
    LatLng delivery;
    MarkerOptions pickUpMarker;
    MarkerOptions deliveryMarker;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light,R.color.cardview_dark_background};

    private  TextView routeText,distanceText,timeText ;
    private LinearLayout details_layout ;
    private ProgressBar progressBar ;
//    double currentLat;
//    double currentLng;
//    LatLng current;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view_request_details, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},500);

        }
        mMap.setMyLocationEnabled(true);

        //Pickup lat long
        Double pickUplat=Double.parseDouble(MainActivity.selectedOfferFromRequestFeed.getPickUplatitude()) ;
        Double pickUplon=Double.parseDouble(MainActivity.selectedOfferFromRequestFeed.getPickUplongitude()) ;

        //Delivery lat long
        Double deliverylat=Double.parseDouble(MainActivity.selectedOfferFromRequestFeed.getDeliverLatitude()) ;
        Double deliverylon=Double.parseDouble(MainActivity.selectedOfferFromRequestFeed.getDeliverLongitude()) ;
//
         Log.d("latln",""+MainActivity.selectedOfferFromRequestFeed.getDeliverLatitude()+" "+MainActivity.selectedOfferFromRequestFeed.getDeliverLongitude()) ;
//        // Add a marker in Sydney, Australia, and move the camera.
        pickUP = new LatLng(pickUplat,pickUplon);
         pickUpMarker=new MarkerOptions() ;
        mMap.addMarker(pickUpMarker.position(pickUP).title("Pick up: "+MainActivity.selectedOfferFromRequestFeed.getPickUpLocationDescription() ));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pickUplat,pickUplon), 15));

        //Delivery marker

        delivery= new LatLng(deliverylat,deliverylon);
        Log.d("deliver",delivery.latitude +" "+delivery.longitude) ;
        deliveryMarker=new MarkerOptions() ;
        deliveryMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(deliveryMarker.position(delivery).title("Drop off : "+MainActivity.selectedOfferFromRequestFeed.getDrofOffLocationDescription() ));


        //Going to current location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MainActivity.lat, MainActivity.lng), 15));



            //getting route

            getRoutToMarker(new LatLng(MainActivity.lat,MainActivity.lng),pickUP,delivery);



    }






    //method to draw route

    private void getRoutToMarker(LatLng start,LatLng mid,LatLng end)  {


            Routing routing = new Routing.Builder().
                    travelMode(AbstractRouting.TravelMode.DRIVING).
                    withListener(this).alternativeRoutes(false).waypoints(start,mid, end).build();

            routing.execute();






    }

    //Methods to handle routes success and failure etc

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(getActivity(),"Sorry could not draw the route,Please try again",Toast.LENGTH_SHORT).show();

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



}
