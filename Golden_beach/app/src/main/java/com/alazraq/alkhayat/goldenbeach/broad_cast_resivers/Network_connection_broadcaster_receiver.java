package com.alazraq.alkhayat.goldenbeach.broad_cast_resivers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.Testing_activity;
import com.alazraq.alkhayat.goldenbeach.activities.All_list_activity;
import com.alazraq.alkhayat.goldenbeach.activities.No_internet_connection_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Splash_activity;
import com.google.android.material.snackbar.Snackbar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import maes.tech.intentanim.CustomIntent;

public class Network_connection_broadcaster_receiver extends BroadcastReceiver {

    SweetAlertDialog ss;
    @Override
    public void onReceive(final Context context, Intent intent) {
        final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);



        if(cm.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean not_connected=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
            if (not_connected){

               ss=new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                        ss.setCustomImage(R.drawable.ic_no_wifi);
                        ss.setContentText(context.getResources().getString(R.string.no_internet_connection));
                        ss.hideConfirmButton();
                        ss.setCancelable(false);
                        ss.setCancelButton(context.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                context.startActivity(new Intent(context, No_internet_connection_activity.class));
                                CustomIntent.customType(context,"fadein-to-fadeout");


                            }
                        });
                        ss.show();

            }else{
                //context.startActivity(new Intent(context, context.getClass()));
                //Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
            }
        }



    }




}
