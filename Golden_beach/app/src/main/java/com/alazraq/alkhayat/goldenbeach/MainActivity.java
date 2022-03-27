package com.alazraq.alkhayat.goldenbeach;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.alazraq.alkhayat.goldenbeach.activities.Admin_control_panel_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Check_administrator_dialog;
import com.alazraq.alkhayat.goldenbeach.activities.Login_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Signup_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Signup_condations_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Splash_activity;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private TextView small,large;
    private CircleImageView user_image_view;
    Network_connection_broadcaster_receiver connection;

    //enc strings
    String v1="co";
    String v2="m.ala";
    String v3="zraq.al";
    String v4="khay";
    String v5="at.";
    String v6="gold";
    String v7="enbe";
    String v8="ach";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //enc
        if(getPackageName().compareTo(v1+v2+v3+v4+v5+v6+v7+v8)!=0){
            String error=null;
            error.getBytes();
        }


        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        if(!Shared_Helper.getkey(this,"user_name").equalsIgnoreCase("admin")){
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Shared_Helper.getkey(MainActivity.this,"administrator").equals("true")){
                    startActivity(new Intent(MainActivity.this, Admin_control_panel_activity.class));
                    CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");

                }else{
                    startActivity(new Intent(MainActivity.this, Check_administrator_dialog.class));
                    CustomIntent.customType(MainActivity.this,"up-to-bottom");

                }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   //     .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View v=navigationView.inflateHeaderView(R.layout.nav_header_main);

        small=(TextView)v.findViewById(R.id.nav_header_small_text_view);
        large=(TextView)v.findViewById(R.id.nav_header_large_text_view);

        user_image_view=(CircleImageView) v.findViewById(R.id.nave_header_user_image_view);

        if(Shared_Helper.getkey(this,"user_name").equalsIgnoreCase("")){
            small.setText("GOLDEN BEACH");
            large.setText("GOLDEN_BEACH@GMAIL.COM");
        }else{
            small.setText(Shared_Helper.getkey(this,"user_name"));
            large.setText(Shared_Helper.getkey(this,"user_email"));
            Picasso.get().load("http://goldenbeachye.com/users_images/"+small.getText().toString()+".jpg").into(user_image_view);


        }

                // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sections,R.id.nav_categories,R.id.nav_watching_list,R.id.nav_search,R.id.nav_maintenance,R.id.nav_accessories,R.id.nav_offers,R.id.nav_gallery, R.id.nav_slideshow
                ,R.id.nav_settings,R.id.nav_suggestions,R.id.nav_contact_us,R.id.about_app,R.id.nav_about_developer,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(Shared_Helper.getkey(this,"user_name").equalsIgnoreCase("")){
            menu.removeItem(R.id.main_menu_logout);
        }else{
            menu.removeItem(R.id.main_menu_login);
            menu.removeItem(R.id.main_menu_sign_up);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i=item.getItemId();

        if(i==R.id.main_menu_login){
            startActivity(new Intent(this, Login_activity.class));
            CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");

        }else if(i==R.id.main_menu_sign_up){
            if(Shared_Helper.getkey(MainActivity.this,"conditions_shown").equalsIgnoreCase("yes")){

                startActivity(new Intent(MainActivity.this, Signup_activity.class));
                CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");

            }else{
                startActivity(new Intent(MainActivity.this,Signup_condations_activity.class));
                CustomIntent.customType(MainActivity.this,"up-to-bottom");
            }
        }else if(i==R.id.main_menu_logout){
            //startActivity(new Intent(this, Login_activity.class));
            Shared_Helper.putKey(this,"user_name_of_image","");
            Shared_Helper.putKey(this,"user_full_name","");
            Shared_Helper.putKey(this,"user_email","");
            Shared_Helper.putKey(this,"user_name","");
            Shared_Helper.putKey(this,"user_id","");
            MainActivity.this.finish();
            startActivity(new Intent(this, Splash_activity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id=destination.getId();
                if(id==R.id.about_app){

                }

            }
        });
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==RESULT_CANCELED){
            recreate();
        }

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


        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }




}

