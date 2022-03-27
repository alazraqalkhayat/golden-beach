package com.alazraq.alkhayat.goldenbeach.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        String r=remoteMessage.getFrom();
        Toast.makeText(this, r, Toast.LENGTH_SHORT).show();



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            } else {
                // Handle message within 10 seconds
            }
        }

        if (remoteMessage.getNotification() != null) {
             String s=remoteMessage.getNotification().getBody();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }


    }
