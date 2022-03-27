package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.activities.Splash_activity;

public class Splash_service extends Service {

    int mode;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, MainActivity.class));


        return mode;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




}
