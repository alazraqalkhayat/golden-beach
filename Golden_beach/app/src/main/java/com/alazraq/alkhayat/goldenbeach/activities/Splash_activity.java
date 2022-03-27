package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Sqlite_Database_Connection;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.alazraq.alkhayat.goldenbeach.helper_classes.App.LOGIN_CHANNEL_ID;

public class Splash_activity extends AppCompatActivity {

    ImageView logo;
    TextView golden_beach,copy_right;

    Animation logo_animation,golden,copy;
    ConnectivityManager connectivityManager;

    NotificationManagerCompat notificationManager;
    Sqlite_Database_Connection db;

    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        notificationManager= NotificationManagerCompat.from(this);

        db=new Sqlite_Database_Connection(this);


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET);

        initViews();

        startPutAnimations();

        startPutSettingsSharedPreferences();



        copy.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            //    Intent ser=new Intent(Splash_activity.this, Splash_service.class);
              //  startService(ser);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Splash_activity.this.finish();
                //startActivity(new Intent(Splash_activity.this, MainActivity.class));


                startCheckTheInternetConnection();



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }



    private void initViews(){
        logo=(ImageView)findViewById(R.id.splash_activity_logo_image_view);
        golden_beach=(TextView) findViewById(R.id.splash_activity_golden_beach_text_view);
        copy_right=(TextView) findViewById(R.id.splash_activity_copy_right_text_view);
    }

    private void startPutAnimations(){
        logo_animation= AnimationUtils.loadAnimation(this,R.anim.top_to_down);
        logo.startAnimation(logo_animation);

        golden= AnimationUtils.loadAnimation(this,R.anim.down_to_top);
        golden_beach.startAnimation(golden);

        copy= AnimationUtils.loadAnimation(this,R.anim.down_to_top);
        copy_right.startAnimation(copy);
    }

    private void startPutSettingsSharedPreferences(){
        if(Shared_Helper.getkey(this,"notifications").equalsIgnoreCase("")){
            Shared_Helper.putKey(this,"notifications","on");
        }

        if(Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("")){
            Shared_Helper.putKey(this,"dark_mode","off");
        }

        //Shared_Helper.putKey(Splash_activity.this,"search_method",getString(R.string.search_methods_name));



    }

    private void startCheckTheInternetConnection(){

        //IntentFilter filter=new IntentFilter();
        //filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //registerReceiver(new Network_connection_broadcaster_receiver(),filter);

        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo !=null && networkInfo.isConnected()==true){
            //Toast.makeText(Splash_activity.this, "connected", Toast.LENGTH_SHORT).show();
            Splash_activity.this.finish();
            startActivity(new Intent(Splash_activity.this, MainActivity.class));

        }else{
            startAlertDialogForNoInternetConnection();
        }




    }

    public void startAlertDialogForNoInternetConnection(){
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.ic_no_wifi)
                .setContentText(this.getResources().getString(R.string.no_internet_connection))
                .hideConfirmButton()
                .setCancelButton(this.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Splash_activity.this.finish();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {

        //startChattingService();

        language=Shared_Helper.getkey(this,"language");
        if(!language.equalsIgnoreCase("")){

            if(language.equalsIgnoreCase("ar")){
                setLocal("ar");
            }else{
                setLocal("en");
            }
        }

        Shared_Helper.putKey(Splash_activity.this,"search_method",getString(R.string.search_methods_name));

        super.onResume();
        if (Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

    }

    @SuppressWarnings("deprecation")
    private void setLocal(String language){
        Locale locale=new Locale(language);
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        Configuration configuration=getResources().getConfiguration();
        configuration.locale=locale;
        getResources().updateConfiguration(configuration,displayMetrics);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //stopChattingService();
    }


    /*
    private void startPermissions(){
        String [] perms={Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this,perms)){

        }else{
            EasyPermissions.requestPermissions(this,"we nead permissions for this and that",123,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }
    }

     */




}
