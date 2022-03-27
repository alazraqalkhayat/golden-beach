package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import maes.tech.intentanim.CustomIntent;

public class Search_method_dialog extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton name,year,evaluation;

    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_method_dialog);

        startInitViews();

        if(Shared_Helper.getkey(this,"search_method").equalsIgnoreCase(getString(R.string.search_methods_name))){
            name.setChecked(true);
        }else if(Shared_Helper.getkey(this,"search_method").equalsIgnoreCase(getString(R.string.search_methods_year))){
            year.setChecked(true);
        }else{
            evaluation.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(name.isChecked()){
                    Shared_Helper.putKey(Search_method_dialog.this,"search_method",name.getText().toString());
                    finishActivity();
                }else if(year.isChecked()){
                    Shared_Helper.putKey(Search_method_dialog.this,"search_method",year.getText().toString());
                    finishActivity();
                }else if(evaluation.isChecked()){
                    Shared_Helper.putKey(Search_method_dialog.this,"search_method",evaluation.getText().toString());
                    finishActivity();
                }
            }
        });
    }

    private void startInitViews(){
        radioGroup=(RadioGroup)findViewById(R.id.search_method_dialog_radio_group);
        name=(RadioButton) findViewById(R.id.search_method_dialog_name_radio_button);
        year=(RadioButton) findViewById(R.id.search_method_dialog_year_radio_button);
        evaluation=(RadioButton) findViewById(R.id.search_method_dialog_evaluation_radio_button);
    }

    private void finishActivity(){
        Intent ii=new Intent();
        setResult(Activity.RESULT_OK,ii);
        finish();
    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Search_method_dialog.this,"bottom-to-up");

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
