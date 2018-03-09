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

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Faisal on 06/03/2018.
 */

public class GCMNotificationIntentService extends FirebaseMessagingService {
// Sets an ID for the notification, so it can be updated
    NotificationManager notificationManager;
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
    }
//



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
