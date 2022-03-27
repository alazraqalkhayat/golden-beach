package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.alazraq.alkhayat.goldenbeach.services.Modifies_service;

import java.util.Locale;

public class App extends Application {

    public static final String LOGIN_CHANNEL_ID ="login_notification_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        try{
            createNotification();

            startChattingService();

            startModifiedService();

        }catch (Exception e){}



    }


    private void createNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    LOGIN_CHANNEL_ID,
                    "chanel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("channel");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);


        }
    }

    private void startChattingService(){
        Intent startService=new Intent(this, Chatting_service.class);
        startService(startService);
    }

    private void startModifiedService(){
        Intent startModifiedServiceIntent=new Intent(this, Modifies_service.class);
        startService(startModifiedServiceIntent);
    }


    private void startStopChattingService(){
        Intent stopService=new Intent(this,Chatting_service.class);
        stopService(stopService);
    }







}
