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
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import app.chaffer.Fragments.FragmentViewOfferDetails;
import app.chaffer.Fragments.FragmentViewRequestDetails;
import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.Offer;
import app.chaffer.R;
import app.chaffer.Request;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mac on 12/02/2018.
 */

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewHolder> {


    private List<Offer> offerList;
    private Context context ;
    FragmentTransaction fm ;


    public class MyViewHolder extends RecyclerView.ViewHolder {

         TextView  offerDescription,requestDescription,amount,time,senderName ;
         RelativeLayout parentlayout ;

        public MyViewHolder(View view) {
            super(view);

            parentlayout = (RelativeLayout) view.findViewById(R.id.parent_layout);

            senderName=(TextView) view.findViewById(R.id.offer_sender_name);
            requestDescription=(TextView) view.findViewById(R.id.request_description);
            offerDescription=(TextView) view.findViewById(R.id.offer_description);
            amount=(TextView) view.findViewById(R.id.offer_amount);
            time=(TextView) view.findViewById(R.id.offer_time);
            amount=(TextView) view.findViewById(R.id.offer_amount);




        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_offer_list, parent, false);

        return new MyViewHolder(itemView);
    }



    public OfferListAdapter(Context context, List<Offer> offerList, FragmentTransaction fm) {

        this.offerList = offerList;

        this.context=context ;

        this.fm=fm ;
    }






    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

                final Offer currentOffer = offerList.get(position) ;


               MainActivity.selectedOfferFromOffersList = currentOffer;

                holder.senderName.setText(""+ currentOffer.getSenderName());
                holder.amount.setText(""+ currentOffer.getAmount());
                holder.offerDescription.setText(""+ currentOffer.getOfferDescription());
                holder.time.setText(""+ currentOffer.getTime());
                //geting request description
                holder.requestDescription.setText(""+currentOffer.getRequest().getOfferDescription());

             //new DownloadImageTask(holder.orderPlacerImage).execute("https://www.1plusx.com/app/mu-plugins/all-in-one-seo-pack-pro/images/default-user-image.png") ;
        //changing image of user's own request to red


        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivity.selectedOfferFromOffersList = currentOffer;

                //check wethter it's users own request or not

                    //Channging fragment
                    FragmentViewOfferDetails offerDetails = new FragmentViewOfferDetails();

                    fm.replace(R.id.layout, offerDetails);
                    fm.addToBackStack("offerList");
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
