package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.nio.channels.Channel;

public class My_notifications extends Application {

    public static final String channel_1_id="channel_1";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel(channel_1_id,"channel1", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("this is channel 1");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}
