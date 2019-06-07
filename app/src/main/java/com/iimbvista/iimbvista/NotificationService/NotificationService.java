package com.iimbvista.iimbvista.NotificationService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.R;

import java.util.Random;

public class NotificationService extends FirebaseMessagingService {
    NotificationManager notificationManager;

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUpNotificationChannels();
        }

        int notificationId = new Random().nextInt(6000);

        Intent notificationIntent;
        if (MainActivity.isAppRunning) {
            notificationIntent = new Intent(this, MainActivity.class);
        } else {
            notificationIntent = new Intent(this, MainActivity.class);
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.app_logo_dark)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, notificationBuilder.build());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUpNotificationChannels() {
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription("default");
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
