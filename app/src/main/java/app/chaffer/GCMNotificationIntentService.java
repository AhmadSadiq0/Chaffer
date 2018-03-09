package app.chaffer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Faisal on 06/03/2018.
 */

public class GCMNotificationIntentService extends FirebaseMessagingService {
// Sets an ID for the notification, so it can be updated
    NotificationCompat.Builder builder;
    NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteView;
    private Context context;

    String ADMIN_CHANNEL_ID = "123";


    public GCMNotificationIntentService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {

        Log.d("FirebaseMsgService",message.getFrom());

        if(message.getNotification() != null){
            Log.d("Notification","Title: "+ message.getNotification().getTitle());
            Log.d("Notification","Body: "+ message.getNotification().getBody());
        }

        if(message.getData().size() > 0){
            Log.d("Data","Title: "+ message.getData().get("account"));
        }

        context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        notification_id = (int) System.currentTimeMillis();

//        remoteView = new RemoteViews(getPackageName(),R.layout.custom_notification);
//        remoteView.setImageViewResource(R.id.notif_icon,R.mipmap.chaffer);
//        remoteView.setTextViewText(R.id.notif_title,message.getNotification().getTitle());
//        remoteView.setProgressBar(R.id.progressBar,100,50,true);

        builder.setSmallIcon(R.mipmap.chaffer)
                .setAutoCancel(true)
                .setContentTitle(message.getNotification().getTitle());

        notificationManager.notify(notification_id,builder.build());

    }
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //Setting up Notification channels for android O and above
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////            setupChannels();
////        }
//        int notificationId = new Random().nextInt(60000);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle(message.getData().get("title"))
//                .setContentText(message.getData().get("message"))
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setupChannels() {
//        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
//        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);
//        NotificationChannel adminChannel;
//        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
//        adminChannel.setDescription(adminChannelDescription);
//        adminChannel.enableLights(true);
//        adminChannel.setLightColor(Color.RED);
//        adminChannel.enableVibration(true);
//        if (notificationManager != null) {
//            notificationManager.createNotificationChannel(adminChannel);
//        }
//    }
}
