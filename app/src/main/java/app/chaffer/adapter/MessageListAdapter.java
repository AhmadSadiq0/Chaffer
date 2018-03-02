package app.chaffer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import app.chaffer.Messages;
import app.chaffer.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mac on 12/02/2018.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    private List<Messages> messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, orderDescription;
        public CircleImageView senderImage ;

        public MyViewHolder(View view) {
            super(view);

            userName = (TextView) view.findViewById(R.id.user_name_message);
            orderDescription = (TextView) view.findViewById(R.id.order_description_message);
            senderImage = (CircleImageView) view.findViewById(R.id.user_image_message);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_messages, parent, false);

        return new MyViewHolder(itemView);
    }



    public MessageListAdapter(List<Messages> messageList) {
        this.messageList = messageList;
    }






    @Override
    public void onBindViewHolder(MessageListAdapter.MyViewHolder holder, int position) {

                Messages currentMessage=messageList.get(position) ;
                holder.userName.setText(currentMessage.getUserName());
                holder.orderDescription.setText(currentMessage.getmessageDescription());
                new DownloadImageTask(holder.senderImage).execute(currentMessage.getsenderImage()) ;


    }

    @Override
    public int getItemCount() {
        return messageList.size();
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
