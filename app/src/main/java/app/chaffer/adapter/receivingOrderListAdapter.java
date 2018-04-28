package app.chaffer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import app.chaffer.DeliveryOrder;
import app.chaffer.Fragments.FragmentViewDeliveryOrderDetails;
import app.chaffer.Fragments.FragmentViewReceivingOrderDetails;
import app.chaffer.MainActivity;
import app.chaffer.R;
import app.chaffer.ReceivingOrder;

import static app.chaffer.Fragments.FragmentViewReceivingOrderDetails.deliverPersonLng;
import static app.chaffer.Fragments.FragmentViewReceivingOrderDetails.deliveryPersonLat;

/**
 * Created by Mac on 12/02/2018.
 */

public class receivingOrderListAdapter extends RecyclerView.Adapter<receivingOrderListAdapter.MyViewHolder> {


    private List<ReceivingOrder> orderList;
    private Context context ;
    FragmentTransaction fm ;

    int count=0 ;

    public class MyViewHolder extends RecyclerView.ViewHolder {

         TextView  orderDescription,reciverPersonName ;
         RelativeLayout parentlayout ;

        public MyViewHolder(View view) {
            super(view);

            parentlayout = (RelativeLayout) view.findViewById(R.id.parent_layout);

            orderDescription=(TextView) view.findViewById(R.id.order_description);
            reciverPersonName=(TextView) view.findViewById(R.id.receiver_user_name);




        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_order_list, parent, false);

        return new MyViewHolder(itemView);
    }



    public receivingOrderListAdapter(Context context, List<ReceivingOrder> orderList, FragmentTransaction fm) {

        this.orderList = orderList;

        this.context=context ;

        this.fm=fm ;
    }






    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

                final ReceivingOrder currentDeliveryOrder = orderList.get(position) ;


               MainActivity.selectedReceivingOrderFromList = currentDeliveryOrder;

                holder.reciverPersonName.setText(""+ currentDeliveryOrder.getDeliveryPersonName());
                holder.orderDescription.setText(""+ currentDeliveryOrder.getDescription());


        //geting request description

             //new DownloadImageTask(holder.orderPlacerImage).execute("https://www.1plusx.com/app/mu-plugins/all-in-one-seo-pack-pro/images/default-user-image.png") ;
        //changing image of user's own request to red


        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivity.selectedReceivingOrderFromList = currentDeliveryOrder;

                //check wethter it's users own request or not

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance() ;
                DatabaseReference ref= firebaseDatabase.getReference().child(currentDeliveryOrder.getDeliveryPersonId()) ;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("data",dataSnapshot.getValue()+"") ;
                        try {
                            JSONObject latlng=new JSONObject(dataSnapshot.getValue()+"") ;
                            deliveryPersonLat=latlng.getString("lat") ;
                            deliverPersonLng=latlng.getString("lng") ;
                            Log.d("latlng11",deliveryPersonLat+deliverPersonLng) ;


                            //We are changing fragment after values are of latlng of delivery persona are fetched
                            if (count==0) {
                                //Channging fragment
                                FragmentViewReceivingOrderDetails receivingOrderDetails = new FragmentViewReceivingOrderDetails();

                                fm.replace(R.id.layout, receivingOrderDetails);
                                fm.addToBackStack("orderList");
                                fm.commit();
                                count++ ;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }) ;





                    //Create a bundle to pass data, add data, set the bundle to your fragment and:



            }
        });


    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }






    //Code to download and load image from url
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}
