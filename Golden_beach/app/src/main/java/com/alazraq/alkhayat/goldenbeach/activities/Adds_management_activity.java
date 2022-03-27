package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.Viewpageradapter;
import com.alazraq.alkhayat.goldenbeach.adds_management_fragments.All_adds_fragment;
import com.alazraq.alkhayat.goldenbeach.adds_management_fragments.New_add_fragment;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.google.android.material.tabs.TabLayout;

import maes.tech.intentanim.CustomIntent;

public class Adds_management_activity extends AppCompatActivity {

    Toolbar adds_toolbar;
    TabLayout adds_tab_layout;
    ViewPager adds_view_pager;



    private New_add_fragment new_add_fragment;
    private All_adds_fragment all_adds_fragment;

    Network_connection_broadcaster_receiver connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adds_management_activity);


        initObjects();

        initViews();

        startAddToolBarSettings();

        adds_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        adds_tab_layout.setupWithViewPager(adds_view_pager);
        Viewpageradapter viewpageradapter=new Viewpageradapter(getSupportFragmentManager(),0);


        viewpageradapter.addFragment(all_adds_fragment,getResources().getString(R.string.adds_management_activity_all_adds_title));
        viewpageradapter.addFragment(new_add_fragment,getResources().getString(R.string.adds_management_activity_new_add_title));


        adds_view_pager.setAdapter(viewpageradapter);
        adds_tab_layout.getTabAt(0);
        adds_tab_layout.getTabAt(1);


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
            finish();
            startActivity(new Intent(this,MainActivity.class));
            CustomIntent.customType(Adds_management_activity.this,"fadein-to-fadeout");

        }

        return super.onOptionsItemSelected(item);
    }

    private void initObjects(){
        new_add_fragment=new New_add_fragment();
        all_adds_fragment =new All_adds_fragment();
    }

    private void initViews(){
        adds_toolbar=(Toolbar)findViewById(R.id.adds_management_activity_tool_bar_id);

        adds_tab_layout=(TabLayout)findViewById(R.id.adds_management_tab_layout);
        adds_view_pager=(ViewPager)findViewById(R.id.adds_management_view_pager);

    }

    private void startAddToolBarSettings(){
        setSupportActionBar(adds_toolbar);
        getSupportActionBar().setTitle(getString(R.string.adds_management_activity_label));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //admin_control_panel_toolbar.setTitle(getString(R.string.admin_control_panel_activity_label));

    }



    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Adds_management_activity.this,"right-to-left");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
