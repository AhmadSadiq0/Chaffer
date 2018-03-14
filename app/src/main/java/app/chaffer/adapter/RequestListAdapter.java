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

import app.chaffer.Fragments.FragmentViewRequestDetails;
import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.Request;
import app.chaffer.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mac on 12/02/2018.
 */

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.MyViewHolder> {

    private List<Request> requestList;
    private Context context ;
    FragmentTransaction fm ;


    public class MyViewHolder extends RecyclerView.ViewHolder {

         TextView userName, orderDescription,pickUpLocation,deliveryLocation,time;
         CircleImageView orderPlacerImage ;
         RelativeLayout parentlayout ;
         ImageView userRequest ;

        public MyViewHolder(View view) {
            super(view);

            parentlayout = (RelativeLayout) view.findViewById(R.id.parent_layout);

            userName = (TextView) view.findViewById(R.id.user_name);
            orderDescription = (TextView) view.findViewById(R.id.order_description);
            pickUpLocation = (TextView) view.findViewById(R.id.pick_up_location);
            deliveryLocation = (TextView) view.findViewById(R.id.delivery_location);
            time = (TextView) view.findViewById(R.id.time);
            userRequest=(ImageView)view.findViewById(R.id.isUser_request) ;
            orderPlacerImage = (CircleImageView) view.findViewById(R.id.user_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_request_list, parent, false);

        return new MyViewHolder(itemView);
    }



    public RequestListAdapter(Context context, List<Request> requestList, FragmentTransaction fm) {

        this.requestList = requestList;

        this.context=context ;

        this.fm=fm ;
    }






    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

                final Request currentRequest = requestList.get(position) ;


                MainActivity.selectedOfferFromRequestFeed = currentRequest;



                        holder.userName.setText("" + currentRequest.getUserName());
                        holder.orderDescription.setText("" + currentRequest.getOfferDescription());
                        holder.pickUpLocation.setText("" + currentRequest.getPickUpLocationDescription());
                        holder.deliveryLocation.setText("" + currentRequest.getDrofOffLocationDescription());
                        holder.time.setText("" + currentRequest.getTimeToDeliver());

                        //changing image of user's own request to red
                        new DownloadImageTask(holder.orderPlacerImage).execute("https://www.1plusx.com/app/mu-plugins/all-in-one-seo-pack-pro/images/default-user-image.png");




        if (currentRequest.getUserId().equals(LoginActivity.userId)){
                    holder.userRequest.setBackgroundResource(R.mipmap.red);

                }


        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivity.selectedOfferFromRequestFeed = currentRequest;

                //check wethter it's users own request or not
                if (currentRequest.getUserId().equals(LoginActivity.userId
                ))
                {

                    Toast.makeText(context,"This is your offer",Toast.LENGTH_LONG).show();

                }else{
                    //Channging fragment
                    FragmentViewRequestDetails offerDetails = new FragmentViewRequestDetails();

                    fm.replace(R.id.layout, offerDetails);
                    fm.addToBackStack("requestList");
                    fm.commit();

                    //Create a bundle to pass data, add data, set the bundle to your fragment and:
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return requestList.size();
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
