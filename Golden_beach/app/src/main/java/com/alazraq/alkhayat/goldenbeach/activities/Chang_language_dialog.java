package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class Chang_language_dialog extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton arabic,english;
    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chang_language_dialog);

        startInitViews();

        if(Shared_Helper.getkey(this,"language").equalsIgnoreCase("ar")){
            arabic.setChecked(true);
        }else{
            english.setChecked(true);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(arabic.isChecked()){
                   setLocal("ar");
                   Shared_Helper.putKey(Chang_language_dialog.this,"language","ar");
                }else if(english.isChecked()){
                    setLocal("en");
                    Shared_Helper.putKey(Chang_language_dialog.this,"language","en");

                }
            }
        });
    }



    private void startInitViews(){
        radioGroup=(RadioGroup)findViewById(R.id.search_method_dialog_radio_group);

        arabic=(RadioButton) findViewById(R.id.change_language_dialog_arabic_radio_button);
        english=(RadioButton) findViewById(R.id.change_language_dialog_english_radio_button);
    }

    @SuppressWarnings("deprecation")
    private void setLocal(String language){
        Locale locale=new Locale(language);
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        Configuration configuration=getResources().getConfiguration();
        configuration.locale=locale;
        getResources().updateConfiguration(configuration,displayMetrics);
        finish();
        startActivity(new Intent(this, MainActivity.class));

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Chang_language_dialog.this,"bottom-to-up");

    }

    @Override
    protected void onResume() {
        super.onResume();

        //startChattingService();

        //register internet broadcast receiver
        connection=new Network_connection_broadcaster_receiver();
        registerReceiver(connection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //stopChattingService();

        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
