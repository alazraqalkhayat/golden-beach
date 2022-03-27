package com.alazraq.alkhayat.goldenbeach.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_chatting;
import com.alazraq.alkhayat.goldenbeach.lists_items.Modified_items;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Modifies_service extends Service {

    DatabaseReference databaseReference;
    List<Modified_items> modified_items_list = new ArrayList<>();

    int number;
    Modified_items current_item;
    public String all_animations,all_documentaries,all_games,all_movies
            ,all_others,all_plays,all_serise,animations_movies_translated,animation_movies_dabbed
            ,animations_series_translated,animations_series_dabbed
            ,movies_arabic,movies_chinese,movies_foreign,movies_indian,movies_indonesian
            ,movies_japanese,movies_korian,movies_thai,movies_turkish,series_arabic
            ,series_arabic_cartoons,series_chinese,series_foreign,series_indian
            ,series_indonesian,series_japanese,series_korian,series_thai,series_turkish;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("settings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                all_animations=(String)snapshot.child("all_animations").getValue();
                all_documentaries=(String)snapshot.child("all_documentaries").getValue();
                all_games=(String)snapshot.child("all_games").getValue();
                all_movies=(String)snapshot.child("all_movies").getValue();
                all_others=(String)snapshot.child("all_others").getValue();
                all_plays=(String)snapshot.child("all_plays").getValue();
                all_serise=(String)snapshot.child("all_serise").getValue();
                animations_movies_translated=(String)snapshot.child("animation_movies_translated").getValue();
                animation_movies_dabbed=(String)snapshot.child("animation_movies_dabbed").getValue();
                animations_series_translated=(String)snapshot.child("animations_series_translated").getValue();
                animations_series_dabbed=(String)snapshot.child("animation_series_dabbed").getValue();
                movies_arabic=(String)snapshot.child("movies_arabic").getValue();
                movies_chinese=(String)snapshot.child("movies_chinese").getValue();
                movies_foreign=(String)snapshot.child("movies_foreign").getValue();
                movies_indian=(String)snapshot.child("movies_indian").getValue();
                movies_indonesian=(String)snapshot.child("movies_indonesian").getValue();
                movies_japanese=(String)snapshot.child("movies_japanese").getValue();
                movies_korian=(String)snapshot.child("movies_korian").getValue();
                movies_thai=(String)snapshot.child("movies_thai").getValue();
                movies_turkish=(String)snapshot.child("movies_turkish").getValue();
                series_arabic=(String)snapshot.child("series_arabic").getValue();
                series_arabic_cartoons=(String)snapshot.child("series_arabic_cartoons").getValue();
                series_chinese=(String)snapshot.child("series_chinese").getValue();
                series_foreign=(String)snapshot.child("series_foreign").getValue();
                series_indian=(String)snapshot.child("series_indian").getValue();
                series_indonesian=(String)snapshot.child("series_indonesian").getValue();
                series_japanese=(String)snapshot.child("series_japanese").getValue();
                series_korian=(String)snapshot.child("series_korian").getValue();
                series_thai=(String)snapshot.child("series_thai").getValue();
                series_turkish=(String)snapshot.child("series_turkish").getValue();

                //Shared_Helper.putKey(Modifies_service.this,"all_animations",all_animations);
                //Toast.makeText(Modifies_service.this, all_animations, Toast.LENGTH_SHORT).show();


                startPutModifiedSharedPreferences( all_animations, all_documentaries
                        , all_games, all_movies, all_others, all_plays, all_serise
                        , animations_movies_translated,animation_movies_dabbed,animations_series_translated,animations_series_dabbed, movies_arabic, movies_chinese
                        , movies_foreign, movies_indian, movies_indonesian, movies_japanese
                        , movies_korian, movies_thai, movies_turkish, series_arabic, series_arabic_cartoons
                        , series_chinese, series_foreign, series_indian, series_indonesian
                        , series_japanese, series_korian, series_thai, series_turkish);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return number;
    }


    private void startPutModifiedSharedPreferences(String all_animations,String all_documentaries
            ,String all_games,String all_movies,String all_others,String all_plays,String all_serise
            ,String animations_movies_translated,String animations_movies_dabbed,String animations_series_translated,String animations_series_dabbed,String movies_arabic,String movies_chinese
            ,String movies_foreign,String movies_indian,String movies_indonesian,String movies_japanese
            ,String movies_korian,String movies_thai,String movies_turkish,String series_arabic,String series_arabic_cartoons
            ,String series_chinese,String series_foreign,String series_indian,String series_indonesian
            ,String series_japanese,String series_korian,String series_thai,String series_turkish){

        Shared_Helper.putKey(Modifies_service.this,"all_animations",all_animations);
        Shared_Helper.putKey(Modifies_service.this,"all_documentaries",all_documentaries);
        Shared_Helper.putKey(Modifies_service.this,"all_games",all_games);
        Shared_Helper.putKey(Modifies_service.this,"all_movies",all_movies);
        Shared_Helper.putKey(Modifies_service.this,"all_others",all_others);
        Shared_Helper.putKey(Modifies_service.this,"all_plays",all_plays);
        Shared_Helper.putKey(Modifies_service.this,"all_serise",all_serise);
        Shared_Helper.putKey(Modifies_service.this,"animation_movies_translated",animations_movies_translated);
        Shared_Helper.putKey(Modifies_service.this,"animation_movies_dabbed",animations_movies_dabbed);
        Shared_Helper.putKey(Modifies_service.this,"animations_series_translated",animations_series_translated);
        Shared_Helper.putKey(Modifies_service.this,"animation_series_dabbed",animations_series_dabbed);
        Shared_Helper.putKey(Modifies_service.this,"movies_arabic",movies_arabic);
        Shared_Helper.putKey(Modifies_service.this,"movies_chinese",movies_chinese);
        Shared_Helper.putKey(Modifies_service.this,"movies_foreign",movies_foreign);
        Shared_Helper.putKey(Modifies_service.this,"movies_indian",movies_indian);
        Shared_Helper.putKey(Modifies_service.this,"movies_indonesian",movies_indonesian);
        Shared_Helper.putKey(Modifies_service.this,"movies_japanese",movies_japanese);
        Shared_Helper.putKey(Modifies_service.this,"movies_korian",movies_korian);
        Shared_Helper.putKey(Modifies_service.this,"movies_thai",movies_thai);
        Shared_Helper.putKey(Modifies_service.this,"movies_turkish",movies_turkish);
        Shared_Helper.putKey(Modifies_service.this,"series_arabic",series_arabic);
        Shared_Helper.putKey(Modifies_service.this,"series_arabic_cartoons",series_arabic_cartoons);
        Shared_Helper.putKey(Modifies_service.this,"series_chinese",series_chinese);
        Shared_Helper.putKey(Modifies_service.this,"series_foreign",series_foreign);
        Shared_Helper.putKey(Modifies_service.this,"series_indian",series_indian);
        Shared_Helper.putKey(Modifies_service.this,"series_indonesian",series_indonesian);
        Shared_Helper.putKey(Modifies_service.this,"series_japanese",series_japanese);
        Shared_Helper.putKey(Modifies_service.this,"series_korian",series_korian);
        Shared_Helper.putKey(Modifies_service.this,"series_thai",series_thai);
        Shared_Helper.putKey(Modifies_service.this,"series_turkish",series_turkish);



    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

