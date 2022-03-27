package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import maes.tech.intentanim.CustomIntent;

public class Admin_control_panel_activity extends AppCompatActivity {

    Toolbar admin_control_panel_toolbar;

    TextView posts_management,adds_managements,modified_management;

    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_control_panel_activity);

        initViews();

        startAddToolBarSettings();

        admin_control_panel_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        posts_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_control_panel_activity.this,Add_new_post_activity.class));
                CustomIntent.customType(Admin_control_panel_activity.this,"left-to-right");

            }
        });

        adds_managements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_control_panel_activity.this,Adds_management_activity.class));
                CustomIntent.customType(Admin_control_panel_activity.this,"left-to-right");

            }
        });

        modified_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_control_panel_activity.this,Modified_activity.class));
                CustomIntent.customType(Admin_control_panel_activity.this,"left-to-right");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.just_home, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i=item.getItemId();

        if(i==R.id.just_home_menu_home_id){
            startActivity(new Intent(this, MainActivity.class));
            CustomIntent.customType(Admin_control_panel_activity.this,"fadein-to-fadeout");

        }

        return super.onOptionsItemSelected(item);
    }


    private void initViews(){
        admin_control_panel_toolbar =(Toolbar)findViewById(R.id.admin_control_panel_activity_tool_bar_id);

        posts_management=(TextView)findViewById(R.id.admin_control_panel_posts_management_text_view);
        adds_managements=(TextView)findViewById(R.id.admin_control_panel_adds_management_text_view);
        modified_management=(TextView)findViewById(R.id.admin_control_panel_modified_management_text_view);
    }

    private void startAddToolBarSettings(){
        setSupportActionBar(admin_control_panel_toolbar);
        getSupportActionBar().setTitle(getString(R.string.admin_control_panel_activity_label));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //admin_control_panel_toolbar.setTitle(getString(R.string.admin_control_panel_activity_label));

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Admin_control_panel_activity.this,"fadein-to-fadeout");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();



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
