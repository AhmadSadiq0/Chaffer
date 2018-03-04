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

import java.io.InputStream;
import java.util.List;

import app.chaffer.Fragments.FragmentViewOfferDetails;
import app.chaffer.MainActivity;
import app.chaffer.Offer;
import app.chaffer.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mac on 12/02/2018.
 */

public class PostedOrderListAdapter extends RecyclerView.Adapter<PostedOrderListAdapter.MyViewHolder> {

    private List<Offer> offerList;
    private Context context ;
    FragmentTransaction fm ;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, orderDescription,pickUpLocation,deliveryLocation,time;
        public CircleImageView orderPlacerImage ;
        RelativeLayout parentlayout ;

        public MyViewHolder(View view) {
            super(view);

            parentlayout = (RelativeLayout) view.findViewById(R.id.parent_layout);

            userName = (TextView) view.findViewById(R.id.user_name);
            orderDescription = (TextView) view.findViewById(R.id.order_description);
            pickUpLocation = (TextView) view.findViewById(R.id.pick_up_location);
            deliveryLocation = (TextView) view.findViewById(R.id.delivery_location);
            time = (TextView) view.findViewById(R.id.time);

            orderPlacerImage = (CircleImageView) view.findViewById(R.id.user_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_posted_orders_list, parent, false);

        return new MyViewHolder(itemView);
    }



    public PostedOrderListAdapter(Context context, List<Offer> offerList, FragmentTransaction fm) {

        this.offerList = offerList;

        this.context=context ;

        this.fm=fm ;
    }






    @Override
    public void onBindViewHolder(PostedOrderListAdapter.MyViewHolder holder, final int position) {

                final Offer currentOffer = offerList.get(position) ;
                holder.userName.setText(""+currentOffer.getUserName());
                holder.orderDescription.setText(""+currentOffer.getOfferDescription());
                holder.pickUpLocation.setText(""+currentOffer.getPickUpLocationDescription());
                holder.deliveryLocation.setText(""+currentOffer.getDrofOffLocationDescription());
                holder.time.setText(""+currentOffer.getTimeToDeliver());

        new DownloadImageTask(holder.orderPlacerImage).execute("https://www.1plusx.com/app/mu-plugins/all-in-one-seo-pack-pro/images/default-user-image.png") ;

        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivity.selectedOfferFromOfferFeed=currentOffer ;


               // Toast.makeText(context,MainActivity.selectedOfferFromOfferFeed.getUserName(),Toast.LENGTH_LONG).show();

                //Channging fragment
                FragmentViewOfferDetails offerDetails = new FragmentViewOfferDetails();

                fm.replace(R.id.layout,offerDetails) ;
                fm.addToBackStack("offerList") ;
                fm.commit();

                //Create a bundle to pass data, add data, set the bundle to your fragment and:



            }
        });

    }


    @Override
    public int getItemCount() {
        return offerList.size();
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
