package id.clorus.bukalelang.presentation.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.auctions.Auction;
import id.clorus.bukalelang.presentation.ui.auction_detail.AuctionDetailFromNotifActivity;

/**
 * Created by mirza on 01/06/17.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    Auction data;
    Bundle bundle;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        remoteMessage.getData();
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Slug " + remoteMessage.getData().get("slug"));
        bundle = new Bundle();
        bundle.putString("slug",remoteMessage.getData().get("slug"));
        createNotification(remoteMessage.getNotification().getBody());
    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, AuctionDetailFromNotifActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bukalelang")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }

    /*
    private void createBigNotif(String messageBody) {
        Intent resultIntent = new Intent(this, HomeActivity.class);
//        resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE, msg);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent dismissIntent = new Intent(this, HomeActivity.class);
        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, HomeActivity.class);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("BukaLelang")
                        .setContentText(messageBody)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(messageBody))
                        .addAction(R.drawable.ic_stat_dismiss,
                                getString(R.string.dismiss), piDismiss)
                        .addAction(R.drawable.ic_stat_snooze,
                                getString(R.string.snooze), piSnooze);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        builder.setContentIntent(resultPendingIntent);

    }*/
}