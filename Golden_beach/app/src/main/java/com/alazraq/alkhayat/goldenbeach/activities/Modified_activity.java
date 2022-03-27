package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.NonNull;
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

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Modifies_service;
import com.github.zagum.switchicon.SwitchIconView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class Modified_activity extends AppCompatActivity {
    Toolbar modified_toolbar;
    Network_connection_broadcaster_receiver connection;



    SwitchIconView all_animations_switch_icon,all_documentaries_switch_icon,all_games_switch_icon,all_movies_switch_icon
            ,all_others_switch_icon,all_plays_switch_icon,all_serise_switch_icon,animations_movies_translated_switch_icon,
            animations_movies_dabbed_switch_icon,animations_series_translated_switch_icon,animations_series_dabbed_switch_icon
            ,movies_arabic_switch_icon,movies_chinese_switch_icon,movies_foreign_switch_icon,movies_indian_switch_icon,movies_indonesian_switch_icon
            ,movies_japanese_switch_icon,movies_korian_switch_icon,movies_thai_switch_icon,movies_turkish_switch_icon,series_arabic_switch_icon
            ,series_arabic_cartoons_switch_icon,series_chinese_switch_icon,series_foreign_switch_icon,series_indian_switch_icon
            ,series_indonesian_switch_icon,series_japanese_switch_icon,series_korian_switch_icon,series_thai_switch_icon,series_turkish_switch_icon;


    String all_animations,all_documentaries,all_games,all_movies
            ,all_others,all_plays,all_serise,animations_movies_translated,animations_movies_dabbed,
            animations_series_translated,animations_series_dabbed
            ,movies_arabic,movies_chinese,movies_foreign,movies_indian,movies_indonesian
            ,movies_japanese,movies_korian,movies_thai,movies_turkish,series_arabic
            ,series_arabic_cartoons,series_chinese,series_foreign,series_indian
            ,series_indonesian,series_japanese,series_korian,series_thai,series_turkish;

    DatabaseReference databaseReference;
    Map<String,Object> add_message_hash_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modified_activity);


        add_message_hash_map=new HashMap<>();
        initViews();

        startAddToolBarSettings();

        startGetModifiedFromSharedPreferences();

        startCheckModified();

        modified_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        all_movies_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_movies_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_movies","off");
                    all_movies_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_movies","on");
                    all_movies_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_foreign_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_foreign_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_foreign","off");
                    movies_foreign_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_foreign","on");
                    movies_foreign_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_arabic_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_arabic_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_arabic","off");
                    movies_arabic_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_arabic","on");
                    movies_arabic_switch_icon.setIconEnabled(true);
                }
            }
        });


        movies_indian_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_indian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_indian","off");
                    movies_indian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_indian","on");
                    movies_indian_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_turkish_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_turkish_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_turkish","off");
                    movies_turkish_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_turkish","on");
                    movies_turkish_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_korian_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_korian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_korian","off");
                    movies_korian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_korian","on");
                    movies_korian_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_japanese_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_japanese_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_japanese","off");
                    movies_japanese_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_japanese","on");
                    movies_japanese_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_chinese_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_chinese_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_chinese","off");
                    movies_chinese_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_chinese","on");
                    movies_chinese_switch_icon.setIconEnabled(true);
                }
            }
        });


        movies_thai_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_thai_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_thai","off");
                    movies_thai_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_thai","on");
                    movies_thai_switch_icon.setIconEnabled(true);
                }
            }
        });

        movies_indonesian_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movies_indonesian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("movies_indonesian","off");
                    movies_indonesian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("movies_indonesian","on");
                    movies_indonesian_switch_icon.setIconEnabled(true);
                }
            }
        });


        //series
        all_serise_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_serise_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_serise","off");
                    all_serise_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_serise","on");
                    all_serise_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_foreign_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_foreign_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_foreign","off");
                    series_foreign_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_foreign","on");
                    series_foreign_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_arabic_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_arabic_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_arabic","off");
                    series_arabic_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_arabic","on");
                    series_arabic_switch_icon.setIconEnabled(true);
                }

            }
        });


        series_indian_switch_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(series_indian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_indian","off");
                    series_indian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_indian","on");
                    series_indian_switch_icon.setIconEnabled(true);
                }
            }

        });

        series_turkish_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_turkish_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_turkish","off");
                    series_turkish_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_turkish","on");
                    series_turkish_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_korian_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_korian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_korian","off");
                    series_korian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_korian","on");
                    series_korian_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_japanese_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_japanese_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_japanese","off");
                    series_japanese_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_japanese","on");
                    series_japanese_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_chinese_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_chinese_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_chinese","off");
                    series_chinese_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_chinese","on");
                    series_chinese_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_thai_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_thai_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_thai","off");
                    series_thai_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_thai","on");
                    series_thai_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_indonesian_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_indonesian_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_indonesian","off");
                    series_indonesian_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_indonesian","on");
                    series_indonesian_switch_icon.setIconEnabled(true);
                }
            }
        });

        series_arabic_cartoons_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(series_arabic_cartoons_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("series_arabic_cartoons","off");
                    series_arabic_cartoons_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("series_arabic_cartoons","on");
                    series_arabic_cartoons_switch_icon.setIconEnabled(true);
                }
            }
        });


        //animations

        all_animations_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_animations_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_animations","off");
                    all_animations_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_animations","on");
                    all_animations_switch_icon.setIconEnabled(true);
                }
            }
        });

        animations_movies_translated_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animations_movies_translated_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("animation_movies_translated","off");
                    animations_movies_translated_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("animation_movies_translated","on");
                    animations_movies_translated_switch_icon.setIconEnabled(true);
                }
            }
        });


        animations_movies_dabbed_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animations_movies_dabbed_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("animation_movies_dabbed","off");
                    animations_movies_dabbed_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("animation_movies_dabbed","on");
                    animations_movies_dabbed_switch_icon.setIconEnabled(true);
                }
            }
        });


        animations_series_translated_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animations_series_translated_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("animations_series_translated","off");
                    animations_series_translated_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("animations_series_translated","on");
                    animations_series_translated_switch_icon.setIconEnabled(true);
                }
            }
        });

        animations_series_dabbed_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animations_series_dabbed_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("animation_series_dabbed","off");
                    animations_series_dabbed_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("animation_series_dabbed","on");
                    animations_series_dabbed_switch_icon.setIconEnabled(true);
                }
            }
        });
        //plays
        all_plays_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_plays_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_plays","off");
                    all_plays_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_plays","on");
                    all_plays_switch_icon.setIconEnabled(true);
                }
            }
        });

        //documentaries
        all_documentaries_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_documentaries_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_documentaries","off");
                    all_documentaries_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_documentaries","on");
                    all_documentaries_switch_icon.setIconEnabled(true);
                }
            }
        });

        //others

        all_others_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_others_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_others","off");
                    all_others_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_others","on");
                    all_others_switch_icon.setIconEnabled(true);
                }
            }
        });

        //games
        all_games_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_games_switch_icon.isIconEnabled()){
                    startChangAllSwitchValues("all_games","off");
                    all_games_switch_icon.setIconEnabled(false);
                }else{
                    startChangAllSwitchValues("all_games","on");
                    all_games_switch_icon.setIconEnabled(true);
                }
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
            finish();
            startActivity(new Intent(this, MainActivity.class));
            CustomIntent.customType(Modified_activity.this,"fadein-to-fadeout");

        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        modified_toolbar=(Toolbar)findViewById(R.id.modified_activity_tool_bar_id);


        //movies switch icons
        all_movies_switch_icon=(SwitchIconView)findViewById(R.id.all_movies_switch);
        movies_foreign_switch_icon=(SwitchIconView)findViewById(R.id.movies_foreign_switch);
        movies_arabic_switch_icon=(SwitchIconView)findViewById(R.id.movies_arabic_switch);
        movies_indian_switch_icon=(SwitchIconView)findViewById(R.id.movies_indian_switch);
        movies_turkish_switch_icon=(SwitchIconView)findViewById(R.id.movies_turkish_switch);
        movies_korian_switch_icon=(SwitchIconView)findViewById(R.id.movies_korean_switch);
        movies_japanese_switch_icon=(SwitchIconView)findViewById(R.id.movies_japanese_switch);
        movies_chinese_switch_icon=(SwitchIconView)findViewById(R.id.movies_chinese_switch);
        movies_thai_switch_icon=(SwitchIconView)findViewById(R.id.movies_thai_switch);
        movies_indonesian_switch_icon=(SwitchIconView)findViewById(R.id.movies_indonesian_switch);

        //series switch icons
        all_serise_switch_icon=(SwitchIconView)findViewById(R.id.all_series_switch);
        series_foreign_switch_icon=(SwitchIconView)findViewById(R.id.series_foreign_switch);
        series_arabic_switch_icon=(SwitchIconView)findViewById(R.id.series_arabic_switch);
        series_indian_switch_icon=(SwitchIconView)findViewById(R.id.series_indian_switch);
        series_turkish_switch_icon=(SwitchIconView)findViewById(R.id.series_turkish_switch);
        series_korian_switch_icon=(SwitchIconView)findViewById(R.id.series_korean_switch);
        series_japanese_switch_icon=(SwitchIconView)findViewById(R.id.series_japanese_switch);
        series_chinese_switch_icon=(SwitchIconView)findViewById(R.id.series_chinese_switch);
        series_thai_switch_icon=(SwitchIconView)findViewById(R.id.series_thai_switch);
        series_indonesian_switch_icon=(SwitchIconView)findViewById(R.id.series_indonesian_switch);
        series_arabic_cartoons_switch_icon=(SwitchIconView)findViewById(R.id.series_arabic_cartoons_switch);

        //animations switch icons
        all_animations_switch_icon=(SwitchIconView)findViewById(R.id.all_animations_switch);
        animations_movies_translated_switch_icon=(SwitchIconView)findViewById(R.id.animations_movies_translated_switch);
        animations_movies_dabbed_switch_icon=(SwitchIconView)findViewById(R.id.animations_movies_dabbed_switch);
        animations_series_translated_switch_icon=(SwitchIconView)findViewById(R.id.animations_series_translated_switch);
        animations_series_dabbed_switch_icon=(SwitchIconView)findViewById(R.id.animations_series_dabbed_switch);

        //plays switch icons
        all_plays_switch_icon=(SwitchIconView)findViewById(R.id.all_plays_switch);

        //documentaries switch icons
        all_documentaries_switch_icon=(SwitchIconView)findViewById(R.id.all_documentaries_switch);

        //others switch icons
        all_others_switch_icon=(SwitchIconView)findViewById(R.id.all_others_switch);

        //games switch icons
        all_games_switch_icon=(SwitchIconView)findViewById(R.id.all_games_switch);




    }

    private void startAddToolBarSettings(){
        setSupportActionBar(modified_toolbar);
        getSupportActionBar().setTitle(getString(R.string.modified_activity_label));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //admin_control_panel_toolbar.setTitle(getString(R.string.admin_control_panel_activity_label));

    }

    private void startGetModifiedFromSharedPreferences(){

        all_animations=Shared_Helper.getkey(this,"all_animations");
        all_documentaries=Shared_Helper.getkey(this,"all_documentaries");
        all_games=Shared_Helper.getkey(this,"all_games");
        all_movies=Shared_Helper.getkey(this,"all_movies");
        all_others=Shared_Helper.getkey(this,"all_others");
        all_plays=Shared_Helper.getkey(this,"all_plays");
        all_serise=Shared_Helper.getkey(this,"all_serise");
        animations_movies_translated=Shared_Helper.getkey(this,"animation_movies_translated");
        animations_movies_dabbed=Shared_Helper.getkey(this,"animation_movies_dabbed");
        animations_series_translated=Shared_Helper.getkey(this,"animations_series_translated");
        animations_series_dabbed=Shared_Helper.getkey(this,"animation_series_dabbed");
        movies_arabic=Shared_Helper.getkey(this,"movies_arabic");
        movies_chinese=Shared_Helper.getkey(this,"movies_chinese");
        movies_foreign=Shared_Helper.getkey(this,"movies_foreign");
        movies_indian=Shared_Helper.getkey(this,"movies_indian");
        movies_indonesian=Shared_Helper.getkey(this,"movies_indonesian");
        movies_japanese=Shared_Helper.getkey(this,"movies_japanese");
        movies_korian=Shared_Helper.getkey(this,"movies_korian");
        movies_thai=Shared_Helper.getkey(this,"movies_thai");
        movies_turkish=Shared_Helper.getkey(this,"movies_turkish");
        series_arabic=Shared_Helper.getkey(this,"series_arabic");
        series_arabic_cartoons=Shared_Helper.getkey(this,"series_arabic_cartoons");
        series_chinese=Shared_Helper.getkey(this,"series_chinese");
        series_foreign=Shared_Helper.getkey(this,"series_foreign");
        series_indian=Shared_Helper.getkey(this,"series_indian");
        series_indonesian=Shared_Helper.getkey(this,"series_indonesian");
        series_japanese=Shared_Helper.getkey(this,"series_japanese");
        series_korian=Shared_Helper.getkey(this,"series_korian");
        series_thai=Shared_Helper.getkey(this,"series_thai");
        series_turkish=Shared_Helper.getkey(this,"series_turkish");


    }

    private void startCheckModified(){
        if (all_movies.equalsIgnoreCase("off")){
           all_movies_switch_icon.setIconEnabled(false);
        }

        if (movies_foreign.equalsIgnoreCase("off")){
            movies_foreign_switch_icon.setIconEnabled(false);
        }

        if (movies_arabic.equalsIgnoreCase("off")){
            movies_arabic_switch_icon.setIconEnabled(false);
        }

        if (movies_indian.equalsIgnoreCase("off")){
            movies_indian_switch_icon.setIconEnabled(false);
        }

        if (movies_turkish.equalsIgnoreCase("off")){
            movies_turkish_switch_icon.setIconEnabled(false);
        }

        if (movies_korian.equalsIgnoreCase("off")){
            movies_korian_switch_icon.setIconEnabled(false);
        }

        if (movies_japanese.equalsIgnoreCase("off")){
            movies_japanese_switch_icon.setIconEnabled(false);
        }

        if (movies_chinese.equalsIgnoreCase("off")){
            movies_chinese_switch_icon.setIconEnabled(false);
        }

        if (movies_thai.equalsIgnoreCase("off")){
            movies_thai_switch_icon.setIconEnabled(false);
        }

        if (movies_indonesian.equalsIgnoreCase("off")){
            movies_indonesian_switch_icon.setIconEnabled(false);
        }

        if (all_serise.equalsIgnoreCase("off")){
            all_serise_switch_icon.setIconEnabled(false);
        }

        if (series_foreign.equalsIgnoreCase("off")){
            series_foreign_switch_icon.setIconEnabled(false);
        }

        if (series_arabic.equalsIgnoreCase("off")){
            series_arabic_switch_icon.setIconEnabled(false);
        }

        if (series_indian.equalsIgnoreCase("off")){
            series_indian_switch_icon.setIconEnabled(false);
        }

        if (series_turkish.equalsIgnoreCase("off")){
            series_turkish_switch_icon.setIconEnabled(false);
        }

        if (series_korian.equalsIgnoreCase("off")){
            series_korian_switch_icon.setIconEnabled(false);
        }

        if (series_japanese.equalsIgnoreCase("off")){
            series_japanese_switch_icon.setIconEnabled(false);
        }

        if (series_chinese.equalsIgnoreCase("off")){
            series_chinese_switch_icon.setIconEnabled(false);
        }

        if (series_thai.equalsIgnoreCase("off")){
            series_thai_switch_icon.setIconEnabled(false);
        }

        if (series_indonesian.equalsIgnoreCase("off")){
            series_indonesian_switch_icon.setIconEnabled(false);
        }

        if (all_animations.equalsIgnoreCase("off")){
            all_animations_switch_icon.setIconEnabled(false);
        }

        if (animations_movies_translated.equalsIgnoreCase("off")){
            animations_movies_translated_switch_icon.setIconEnabled(false);
        }

        if (animations_movies_dabbed.equalsIgnoreCase("off")){
            animations_movies_dabbed_switch_icon.setIconEnabled(false);
        }

        if (animations_series_translated.equalsIgnoreCase("off")){
            animations_series_translated_switch_icon.setIconEnabled(false);
        }

        if (animations_series_dabbed.equalsIgnoreCase("off")){
            animations_series_dabbed_switch_icon.setIconEnabled(false);
        }
        if (all_plays.equalsIgnoreCase("off")){
            all_plays_switch_icon.setIconEnabled(false);
        }

        if (all_documentaries.equalsIgnoreCase("off")){
            all_documentaries_switch_icon.setIconEnabled(false);
        }

        if (all_others.equalsIgnoreCase("off")){
            all_others_switch_icon.setIconEnabled(false);
        }

        if (all_games.equalsIgnoreCase("off")){
            all_games_switch_icon.setIconEnabled(false);
        }



    }

    private void startChangAllSwitchValues(final String position, final String value){

        databaseReference= FirebaseDatabase.getInstance().getReference().child("settings");
        add_message_hash_map.put(position,value);
        databaseReference.updateChildren(add_message_hash_map);

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Modified_activity.this,"right-to-left");

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
