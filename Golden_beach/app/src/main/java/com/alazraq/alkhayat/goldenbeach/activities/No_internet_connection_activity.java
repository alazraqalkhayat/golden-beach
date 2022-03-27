package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.Testing_activity;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class No_internet_connection_activity extends AppCompatActivity {

    ConnectivityManager connectivityManager;
    SweetAlertDialog ss;

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection_activity);

        logo=(ImageView)findViewById(R.id.no_internet_connection_activity_logo_image);
        startCheckTheInternetConnection();


    }

    private void startCheckTheInternetConnection(){


        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo !=null && networkInfo.isConnected()==true){
            Toasty.success(No_internet_connection_activity.this,getResources().getString(R.string.internet_re_connected),Toast.LENGTH_SHORT,true).show();
            No_internet_connection_activity.this.finish();


            //startActivity(new Intent(Testing_activity.this, MainActivity.class));

        }else{
            startAlertDialogForNoInternetConnection();
        }




    }


    public void startAlertDialogForNoInternetConnection(){
        ss= new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        ss.setCustomImage(R.drawable.ic_no_wifi);
        ss.setContentText(this.getResources().getString(R.string.no_internet_connection_2));
        ss.hideConfirmButton();
        ss.setCancelable(false);
        ss.setCancelButton(this.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
                //Testing_activity.this.finish();
                //startActivity(Testing_activity.this,Testing_activity.class);
                recreate();
            }
        });
        ss.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            logo.setImageDrawable(getDrawable(R.drawable.final_logo_dark_mode));


        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            logo.setImageDrawable(getDrawable(R.drawable.final_logo));

        }
    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(No_internet_connection_activity.this,"fadein-to-fadeout");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        No_internet_connection_activity.this.recreate();
    }

}
