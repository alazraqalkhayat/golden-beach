package com.alazraq.alkhayat.goldenbeach.ui.categories;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.All_list_activity;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;

import maes.tech.intentanim.CustomIntent;


public class Categories_fragment extends Fragment {

    String all_animations,all_documentaries,all_games,all_movies
            ,all_others,all_plays,all_serise,animations_movies_translated,animations_movies_dabbed
            ,animations_series_translated,animations_series_dabbed
            ,movies_arabic,movies_chinese,movies_foreign,movies_indian,movies_indonesian
            ,movies_japanese,movies_korian,movies_thai,movies_turkish,series_arabic
            ,series_arabic_cartoons,series_chinese,series_foreign,series_indian
            ,series_indonesian,series_japanese,series_korian,series_thai,series_turkish;


    CardView all_animations_card_view,all_documentaries_card_view,all_games_card_view,all_movies_card_view
            ,all_others_card_view,all_plays_card_view,all_serise_card_view,animations_movies_translated_card_view,animations_movies_dabbed_card_view
            ,animations_series_translated_card_view,animations_series_dabbed_card_view
            ,movies_arabic_card_view,movies_chinese_card_view,movies_foreign_card_view,movies_indian_card_view,movies_indonesian_card_view
            ,movies_japanese_card_view,movies_korian_card_view,movies_thai_card_view,movies_turkish_card_view,series_arabic_card_view
            ,series_arabic_cartoons_card_view,series_chinese_card_view,series_foreign_card_view,series_indian_card_view
            ,series_indonesian_card_view,series_japanese_card_view,series_korian_card_view,series_thai_card_view,series_turkish_card_view;


    TextView movies_text_view,series_text_view,
                animations_text_view,plays_text_view,
                    documentaries_text_view,others_text_view,
                        games_text_view;

    String name_of_section,brand_of_section;

    Intent move_to_movies_list_activity_intent;
    Bundle move_to_movies_list_activity_bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.categories_fragment, container, false);

        initViews(v);

        InitObjects();

        startGetModifiedFromSharedPreferences();

        startCheckModified();

        startCheckIfAllIsGone();


        all_movies_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section="MOVIES";
                startAllListActivityForParentSections(name_of_section);
            }
        });

        movies_foreign_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_foreign);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_arabic_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_arabic);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_indian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_indian);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_turkish_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_turkish);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_korian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_korean);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_japanese_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_japanese);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_chinese_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_chinese);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_thai_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_thai);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        movies_indonesian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_movies);
                brand_of_section=getString(R.string.brand_of_section_Indonesian);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });


        //series
        all_serise_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section="SERIES";
                startAllListActivityForParentSections(name_of_section);
            }
        });

        series_foreign_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_foreign);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_arabic_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_arabic);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_indian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_indian);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_turkish_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_turkish);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_korian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_korean);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_japanese_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_japanese);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_chinese_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_chinese);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_thai_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_thai);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_indonesian_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_Indonesian);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        series_arabic_cartoons_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.section_series);
                brand_of_section=getString(R.string.brand_of_section_arabic_cartons);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        all_animations_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section="ANIMATIONS";
                startAllListActivityForParentSections(name_of_section);

            }
        });

        animations_movies_translated_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.animations_text_view);

                brand_of_section=getString(R.string.brand_of_section_animations_movies_translated);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        animations_movies_dabbed_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.animations_text_view);

                brand_of_section=getString(R.string.brand_of_section_animations_movies_dabbed);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        animations_series_translated_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.animations_text_view);

                brand_of_section=getString(R.string.brand_of_section_animations_series_translated);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        animations_series_translated_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section=getString(R.string.animations_text_view);
                brand_of_section=getString(R.string.brand_of_section_animations_series_dabbed);
                startAllListActivityForSubSections(name_of_section,brand_of_section);

            }
        });

        all_plays_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name_of_section="PLAYS";
                startAllListActivityForParentSections(name_of_section);

            }
        });

        all_documentaries_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name_of_section="DOCUMENTARIES";
                startAllListActivityForParentSections(name_of_section);

            }
        });

        all_others_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section="OTHERS";
                startAllListActivityForParentSections(name_of_section);
            }
        });

        all_games_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_section="GAMES";
                startAllListActivityForParentSections(name_of_section);

            }
        });

        return v;


    }


    private void initViews(View v){

        //text views

        movies_text_view=(TextView)v.findViewById(R.id.categories_fragment_movies_text_view);
        series_text_view=(TextView)v.findViewById(R.id.categories_fragment_series_text_view);
        animations_text_view=(TextView)v.findViewById(R.id.categories_fragment_animation_text_view);
        plays_text_view=(TextView)v.findViewById(R.id.categories_fragment_plays_text_view);
        documentaries_text_view=(TextView)v.findViewById(R.id.categories_fragment_documentaries_text_view);
        others_text_view=(TextView)v.findViewById(R.id.categories_fragment_others_text_view);
        games_text_view=(TextView)v.findViewById(R.id.categories_fragment_games_text_view);


        //movies card views
        all_movies_card_view=(CardView)v.findViewById(R.id.all_movies_card_view);
        movies_foreign_card_view=(CardView)v.findViewById(R.id.movies_foreign_card_view);
        movies_arabic_card_view=(CardView)v.findViewById(R.id.movies_arabic_card_view);
        movies_indian_card_view=(CardView)v.findViewById(R.id.movies_indian_card_view);
        movies_turkish_card_view=(CardView)v.findViewById(R.id.movies_turkish_card_view);
        movies_korian_card_view=(CardView)v.findViewById(R.id.movies_korian_card_view);
        movies_japanese_card_view=(CardView)v.findViewById(R.id.movies_japanese_card_view);
        movies_chinese_card_view=(CardView)v.findViewById(R.id.movies_chinese_card_view);
        movies_thai_card_view=(CardView)v.findViewById(R.id.movies_thai_card_view);
        movies_indonesian_card_view=(CardView)v.findViewById(R.id.movies_indonesian_card_view);


        all_serise_card_view=(CardView)v.findViewById(R.id.all_series_card_view);
        series_foreign_card_view=(CardView)v.findViewById(R.id.series_foreign_card_view);
        series_arabic_card_view=(CardView)v.findViewById(R.id.series_arabic_card_view);
        series_indian_card_view=(CardView)v.findViewById(R.id.series_indian_card_view);
        series_turkish_card_view=(CardView)v.findViewById(R.id.series_turkish_card_view);
        series_korian_card_view=(CardView)v.findViewById(R.id.series_korean_card_view);
        series_japanese_card_view=(CardView)v.findViewById(R.id.series_japanese_card_view);
        series_chinese_card_view=(CardView)v.findViewById(R.id.series_chinese_card_view);
        series_thai_card_view=(CardView)v.findViewById(R.id.series_thai_card_view);
        series_indonesian_card_view=(CardView)v.findViewById(R.id.series_indonesian_card_view);
        series_arabic_cartoons_card_view=(CardView)v.findViewById(R.id.series_arabic_cartoons_card_view);

        //animations card views
        all_animations_card_view=(CardView)v.findViewById(R.id.all_animations_card_view);
        animations_movies_translated_card_view=(CardView)v.findViewById(R.id.animations_movies_translated_card_view);
        animations_movies_dabbed_card_view=(CardView)v.findViewById(R.id.animations_movies_dabbed_card_view);
        animations_series_translated_card_view=(CardView)v.findViewById(R.id.animations_series_translated_card_view);
        animations_series_dabbed_card_view=(CardView)v.findViewById(R.id.animations_series_dabbed_card_view);

        //plays card view
        all_plays_card_view=(CardView)v.findViewById(R.id.all_plays_card_view);

        //documentaries card view
        all_documentaries_card_view=(CardView)v.findViewById(R.id.all_documentaries_card_view);

        //others card view
        all_others_card_view=(CardView)v.findViewById(R.id.all_others_card_view);

        //games card view
        all_games_card_view=(CardView)v.findViewById(R.id.all_games_card_view);





    }

    private void InitObjects() {
        move_to_movies_list_activity_intent=new Intent(getContext(), All_list_activity.class);
        move_to_movies_list_activity_bundle=new Bundle();
    }

    private void startGetModifiedFromSharedPreferences(){

        all_animations=Shared_Helper.getkey(getContext(),"all_animations");
        all_documentaries=Shared_Helper.getkey(getContext(),"all_documentaries");
        all_games=Shared_Helper.getkey(getContext(),"all_games");
        all_movies=Shared_Helper.getkey(getContext(),"all_movies");
        all_others=Shared_Helper.getkey(getContext(),"all_others");
        all_plays=Shared_Helper.getkey(getContext(),"all_plays");
        all_serise=Shared_Helper.getkey(getContext(),"all_serise");
        animations_movies_translated=Shared_Helper.getkey(getContext(),"animation_movies_translated");
        animations_movies_dabbed=Shared_Helper.getkey(getContext(),"animation_movies_dabbed");
        animations_series_translated=Shared_Helper.getkey(getContext(),"animations_series_translated");
        animations_series_dabbed=Shared_Helper.getkey(getContext(),"animation_series_dabbed");
        movies_arabic=Shared_Helper.getkey(getContext(),"movies_arabic");
        movies_chinese=Shared_Helper.getkey(getContext(),"movies_chinese");
        movies_foreign=Shared_Helper.getkey(getContext(),"movies_foreign");
        movies_indian=Shared_Helper.getkey(getContext(),"movies_indian");
        movies_indonesian=Shared_Helper.getkey(getContext(),"movies_indonesian");
        movies_japanese=Shared_Helper.getkey(getContext(),"movies_japanese");
        movies_korian=Shared_Helper.getkey(getContext(),"movies_korian");
        movies_thai=Shared_Helper.getkey(getContext(),"movies_thai");
        movies_turkish=Shared_Helper.getkey(getContext(),"movies_turkish");
        series_arabic=Shared_Helper.getkey(getContext(),"series_arabic");
        series_arabic_cartoons=Shared_Helper.getkey(getContext(),"series_arabic_cartoons");
        series_chinese=Shared_Helper.getkey(getContext(),"series_chinese");
        series_foreign=Shared_Helper.getkey(getContext(),"series_foreign");
        series_indian=Shared_Helper.getkey(getContext(),"series_indian");
        series_indonesian=Shared_Helper.getkey(getContext(),"series_indonesian");
        series_japanese=Shared_Helper.getkey(getContext(),"series_japanese");
        series_korian=Shared_Helper.getkey(getContext(),"series_korian");
        series_thai=Shared_Helper.getkey(getContext(),"series_thai");
        series_turkish=Shared_Helper.getkey(getContext(),"series_turkish");


    }

    private void startCheckModified(){

        if(!Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("admin")
                ||Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("")){

            if (all_movies.equalsIgnoreCase("off")){
                all_movies_card_view.setVisibility(View.GONE);
            }

            if (movies_foreign.equalsIgnoreCase("off")){
                movies_foreign_card_view.setVisibility(View.GONE);
            }

            if (movies_arabic.equalsIgnoreCase("off")){
                movies_arabic_card_view.setVisibility(View.GONE);
            }

            if (movies_indian.equalsIgnoreCase("off")){
                movies_indian_card_view.setVisibility(View.GONE);
            }

            if (movies_turkish.equalsIgnoreCase("off")){
                movies_turkish_card_view.setVisibility(View.GONE);
            }

            if (movies_korian.equalsIgnoreCase("off")){
                movies_korian_card_view.setVisibility(View.GONE);
            }

            if (movies_japanese.equalsIgnoreCase("off")){
                movies_japanese_card_view.setVisibility(View.GONE);
            }

            if (movies_chinese.equalsIgnoreCase("off")){
                movies_chinese_card_view.setVisibility(View.GONE);
            }

            if (movies_thai.equalsIgnoreCase("off")){
                movies_thai_card_view.setVisibility(View.GONE);
            }

            if (movies_indonesian.equalsIgnoreCase("off")){
                movies_indonesian_card_view.setVisibility(View.GONE);
            }

            if (all_serise.equalsIgnoreCase("off")){
                all_serise_card_view.setVisibility(View.GONE);
            }

            if (series_foreign.equalsIgnoreCase("off")){
                series_foreign_card_view.setVisibility(View.GONE);
            }

            if (series_arabic.equalsIgnoreCase("off")){
                series_arabic_card_view.setVisibility(View.GONE);
            }

            if (series_indian.equalsIgnoreCase("off")){
                series_indian_card_view.setVisibility(View.GONE);
            }

            if (series_turkish.equalsIgnoreCase("off")){
                series_turkish_card_view.setVisibility(View.GONE);
            }

            if (series_korian.equalsIgnoreCase("off")){
                series_korian_card_view.setVisibility(View.GONE);
            }

            if (series_japanese.equalsIgnoreCase("off")){
                series_japanese_card_view.setVisibility(View.GONE);
            }

            if (series_chinese.equalsIgnoreCase("off")){
                series_chinese_card_view.setVisibility(View.GONE);
            }

            if (series_thai.equalsIgnoreCase("off")){
                series_thai_card_view.setVisibility(View.GONE);
            }

            if (series_indonesian.equalsIgnoreCase("off")){
                series_indonesian_card_view.setVisibility(View.GONE);
            }

            if(series_arabic_cartoons.equalsIgnoreCase("off")){
                series_arabic_cartoons_card_view.setVisibility(View.GONE);
            }

            if (all_animations.equalsIgnoreCase("off")){
                all_animations_card_view.setVisibility(View.GONE);
            }

            if (animations_movies_translated.equalsIgnoreCase("off")){
                animations_movies_translated_card_view.setVisibility(View.GONE);
            }

            if (animations_movies_dabbed.equalsIgnoreCase("off")){
                animations_movies_dabbed_card_view.setVisibility(View.GONE);
            }

            if (animations_series_translated.equalsIgnoreCase("off")){
                animations_series_translated_card_view.setVisibility(View.GONE);
            }

            if (animations_series_dabbed.equalsIgnoreCase("off")){
                animations_series_dabbed_card_view.setVisibility(View.GONE);
            }
            if (all_plays.equalsIgnoreCase("off")){
                all_plays_card_view.setVisibility(View.GONE);
            }

            if (all_documentaries.equalsIgnoreCase("off")){
                all_documentaries_card_view.setVisibility(View.GONE);
            }

            if (all_others.equalsIgnoreCase("off")){
                all_others_card_view.setVisibility(View.GONE);
            }

            if (all_games.equalsIgnoreCase("off")){
                all_games_card_view.setVisibility(View.GONE);
            }






        }



    }

    private void startCheckIfAllIsGone(){


        if(all_movies_card_view.getVisibility()==View.GONE
                &&movies_foreign_card_view.getVisibility()==View.GONE
                &&movies_arabic_card_view.getVisibility()==View.GONE
                &&movies_indian_card_view.getVisibility()==View.GONE
                &&movies_turkish_card_view.getVisibility()==View.GONE
                &&movies_korian_card_view.getVisibility()==View.GONE
                &&movies_japanese_card_view.getVisibility()==View.GONE
                &&movies_chinese_card_view.getVisibility()==View.GONE
                &&movies_thai_card_view.getVisibility()==View.GONE
                &&movies_indonesian_card_view.getVisibility()==View.GONE){

            movies_text_view.setVisibility(View.GONE);

        }
        if(all_serise_card_view.getVisibility()==View.GONE
                &&series_foreign_card_view.getVisibility()==View.GONE
                &&series_arabic_card_view.getVisibility()==View.GONE
                &&series_indian_card_view.getVisibility()==View.GONE
                &&series_turkish_card_view.getVisibility()==View.GONE
                &&series_korian_card_view.getVisibility()==View.GONE
                &&series_japanese_card_view.getVisibility()==View.GONE
                &&series_chinese_card_view.getVisibility()==View.GONE
                &&series_thai_card_view.getVisibility()==View.GONE
                &&series_indonesian_card_view.getVisibility()==View.GONE
                &&series_arabic_cartoons_card_view.getVisibility()==View.GONE){

                 series_text_view.setVisibility(View.GONE);

        }

        if(all_animations_card_view.getVisibility()==View.GONE
                &&animations_movies_translated_card_view.getVisibility()==View.GONE
                    &&animations_movies_dabbed_card_view.getVisibility()==View.GONE
                        &&animations_series_translated_card_view.getVisibility()==View.GONE
                        &&animations_series_dabbed_card_view.getVisibility()==View.GONE){
            animations_text_view.setVisibility(View.GONE);
        }

        if (all_plays_card_view.getVisibility()==View.GONE){
            plays_text_view.setVisibility(View.GONE);
        }

        if (all_documentaries_card_view.getVisibility()==View.GONE){
            documentaries_text_view.setVisibility(View.GONE);
        }

        if (all_others_card_view.getVisibility()==View.GONE){
            others_text_view.setVisibility(View.GONE);
        }

        if (all_games_card_view.getVisibility()==View.GONE){
            games_text_view.setVisibility(View.GONE);
        }
    }

    private void startAllListActivityForSubSections(String section, String brand){
        move_to_movies_list_activity_bundle.putString("name_of_section",section);
        move_to_movies_list_activity_bundle.putString("brand_of_section",brand);
        move_to_movies_list_activity_bundle.putString("specification","sub_section");
        move_to_movies_list_activity_intent.putExtras(move_to_movies_list_activity_bundle);
        startActivity(move_to_movies_list_activity_intent);
        CustomIntent.customType(getContext(),"left-to-right");

    }

    private void startAllListActivityForParentSections(String section){
        move_to_movies_list_activity_bundle.putString("name_of_section",section);
        move_to_movies_list_activity_bundle.putString("specification","parent_section");
        move_to_movies_list_activity_intent.putExtras(move_to_movies_list_activity_bundle);
        startActivity(move_to_movies_list_activity_intent);
        CustomIntent.customType(getContext(),"left-to-right");

    }

}
