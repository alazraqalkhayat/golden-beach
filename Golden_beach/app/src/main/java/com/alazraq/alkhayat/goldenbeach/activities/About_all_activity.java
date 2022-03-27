package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.Commentes_adapter;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.EndlessRecyclerViewScrollListener;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Sqlite_Database_Connection;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_comments;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Sum_of_evaluations;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class About_all_activity extends AppCompatActivity {

    int EVALUATION_DIALOG_REQUEST_CODE =55;
    int ADD_COMMENT_DIALOG_REQUEST_CODE =65;
    Bundle get_data_from_all_list_activity;
    String name_of_section,brand_of_section,name_of_category,name,id
            ,name_of_image,session,year,story,trailer;



    Commentes_adapter commentes_adapter;
    ArrayList<Items_of_comments> items_of_comments=new ArrayList<>();


    ImageView imageView;
    ImageView show_hide_comments_image_view,back_image_view,home_image_view;
    TextView name_Text_view, seasons_no_text_view,story_text_view,show_hide_comments_text_view,category_text_view,num_of_rating_text_view;
    TextInputEditText comment;
    RatingBar about_all_rating_bar;
    RecyclerView comment_recycle_view;

    ProgressBar load_more_progress_bar;
    ImageView reload_image_view;

    LinearLayout linearLayout;
    View view;

    My_spot_progress_dialog add_new_comment_dialog;
    My_Flower_progress_dialog get_all_comments_dialog;
    Login_alert_dialog login_alert_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    Sqlite_Database_Connection db;
    Network_connection_broadcaster_receiver connection;

    CardView add_to_watcuing_list_cardview,watch_trailer_card_view,ratting_all_card_view,add_comment_card_view;

    int LIMIT=0;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_all_activity);

        startGetAllDataFromBundle();

        initRecycleView();

        startInitViews();

        startConvertToArabic(name_of_section,brand_of_section,name_of_category);

        startSetAllDetails();

        startInitObjects();



        if(trailer.equalsIgnoreCase("null")||trailer.equalsIgnoreCase("")){
            watch_trailer_card_view.setVisibility(View.GONE);
        }

        back_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(About_all_activity.this, MainActivity.class));
                CustomIntent.customType(About_all_activity.this,"fadein-to-fadeout");

            }
        });




        //comment_recycle_view.setLayoutManager(new LinearLayoutManager(this));

        startRetrofitForGetAllComments(Integer.valueOf(id),LIMIT);
        get_all_comments_dialog.start_dialog();

        startRetrofitToGetSumOfEvaluationValues(Integer.valueOf(id));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_image();
            }
        });

        add_to_watcuing_list_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name_Text_view.getText().toString().equalsIgnoreCase("null")){
                    startAddNewWatchingList(name,Integer.valueOf(session));
                }
            }
        });

        watch_trailer_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenYoutube(trailer);

            }
        });

        ratting_all_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Shared_Helper.getkey(About_all_activity.this,"user_name").equalsIgnoreCase("")){
                    login_alert_dialog.startAlertDialogForLogin();
                }else{
                    startMoveToEvaluationDialog();
                }
            }
        });



        add_comment_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMoveToAddCommentDialog();
            }
        });


        show_hide_comments_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startShowAndHideRecycleView();
            }
        });





        show_hide_comments_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startShowAndHideRecycleView();
            }
        });



        comment_recycle_view.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //get_all_dialog.start_dialog();
                LIMIT +=5;
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);

                startRetrofitForGetAllComments(Integer.valueOf(id),LIMIT);


            }
        });

        reload_image_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);
                reload_image_view.setVisibility(View.GONE);

                startRetrofitForGetAllComments(Integer.valueOf(id),LIMIT);

            }
        });



    }


    private void startGetAllDataFromBundle(){

        get_data_from_all_list_activity=getIntent().getExtras();

        name_of_section=get_data_from_all_list_activity.getString("name_of_section");
        brand_of_section=get_data_from_all_list_activity.getString("brand_of_section");
        name_of_category=get_data_from_all_list_activity.getString("name_of_category");
        id=get_data_from_all_list_activity.getString("id");
        name=get_data_from_all_list_activity.getString("name");
        name_of_image=get_data_from_all_list_activity.getString("name_of_image");
        session=get_data_from_all_list_activity.getString("session");
        year=get_data_from_all_list_activity.getString("year");
        story=get_data_from_all_list_activity.getString("story");
        trailer=get_data_from_all_list_activity.getString("trailer");





    }

    private void startPutTheSeasonsNo(int seasons,String name_of_section,String brand_of_section){

        if(seasons!=0){
            if(name_of_section.equalsIgnoreCase(getString(R.string.section_series))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_series_translated))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_series_dabbed))){

                seasons_no_text_view.setText(getString(R.string.about_all_activity_seasons_no)+" "+String.valueOf(seasons));

            }else if(name_of_section.equalsIgnoreCase(getString(R.string.section_movies))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_movies_translated))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_movies_dabbed))) {

                seasons_no_text_view.setText(getString(R.string.about_all_activity_parts_no)+" "+String.valueOf(seasons));
            }else {
                seasons_no_text_view.setVisibility(View.GONE);
            }

        }else{

            if(name_of_section.equalsIgnoreCase(getString(R.string.section_series))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_series_translated))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_series_dabbed))){

                seasons_no_text_view.setText(getString(R.string.about_all_activity_seasons_no)+" "+getString(R.string.not_defined));

            }else if(name_of_section.equalsIgnoreCase(getString(R.string.section_movies))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_movies_translated))
                    ||brand_of_section.equalsIgnoreCase(getString(R.string.section_animation_movies_dabbed))) {

                seasons_no_text_view.setText(getString(R.string.about_all_activity_parts_no)+" "+getString(R.string.not_defined));
            }else {
                seasons_no_text_view.setVisibility(View.GONE);

            }

        }


    }

    private void startInitViews(){

        back_image_view=(ImageView)findViewById(R.id.about_all_activity_back_image_view);
        home_image_view=(ImageView)findViewById(R.id.about_all_activity_home_image_view);

        imageView =(ImageView)findViewById(R.id.about_movie_activity_movie_image_image_view);
        name_Text_view =(TextView)findViewById(R.id.about_movie_activity_movie_name_text_view);
        seasons_no_text_view =(TextView)findViewById(R.id.about_all_activity_season_no_text_view);
        category_text_view=(TextView)findViewById(R.id.about_movie_activity_movie_name_category);
        //category_text_view.setText(name_of_section+);
        story_text_view=(TextView)findViewById(R.id.about_movie_activity_movie_story_text_view);

        linearLayout=(LinearLayout)findViewById(R.id.about_all_activity_linear_layout);
        view=(View)findViewById(R.id.about_all_activity_view);

        about_all_rating_bar=(RatingBar)findViewById(R.id.about_all_activity_rating_bar);
        about_all_rating_bar.setEnabled(false);

        num_of_rating_text_view=(TextView)findViewById(R.id.about_all_activity_rating_bar_text_view);

        add_to_watcuing_list_cardview=(CardView)findViewById(R.id.about_all_activity_add_to_watching_list_card_view);
        watch_trailer_card_view=(CardView)findViewById(R.id.about_all_activity_watch_trailer_card_view);
        ratting_all_card_view=(CardView)findViewById(R.id.about_all_activity_ratting_card_view);
        add_comment_card_view=(CardView)findViewById(R.id.about_all_activity_comment_card_view);

        show_hide_comments_image_view=(ImageView)findViewById(R.id.about_all_activity_show_hide_comments_image_view);
        show_hide_comments_text_view=(TextView)findViewById(R.id.about_all_activity_show_hide_comments_text_view);


        load_more_progress_bar=(ProgressBar)findViewById(R.id.about_all_activity_load_more_progress_bar);
        reload_image_view=(ImageView)findViewById(R.id.about_all_activity_reload_image_view);





    }

    private void startInitObjects(){
        get_all_comments_dialog=new My_Flower_progress_dialog(this);
        add_new_comment_dialog=new My_spot_progress_dialog(this);

        login_alert_dialog=new Login_alert_dialog(this);

    }

    private void initRecycleView(){
        comment_recycle_view =(RecyclerView)findViewById(R.id.about_all_activity_recycle_view);
        //comment_recycle_view.setNestedScrollingEnabled(false);
        manager=(LinearLayoutManager)comment_recycle_view.getLayoutManager();
        comment_recycle_view.setLayoutManager(manager);
        commentes_adapter=new Commentes_adapter(About_all_activity.this, items_of_comments);
        comment_recycle_view.setAdapter(commentes_adapter);

    }

    private void startConvertToArabic(String name_of_section_string,String brand_of_section_string,String category_string){

        if(name_of_section_string.equalsIgnoreCase("MOVIES")){
            name_of_section_string=getString(R.string.section_movies);
        }else if(name_of_section_string.equalsIgnoreCase("SERIES")){
            name_of_section_string=getString(R.string.section_series);
        }else if(name_of_section_string.equalsIgnoreCase("ANIMATIONS")){
            name_of_section_string=getString(R.string.animations_text_view);
        }else if(name_of_section_string.equalsIgnoreCase("PLAYS")){
            name_of_section_string=getString(R.string.section_plays);
        }else if(name_of_section_string.equalsIgnoreCase("DOCUMENTARIES")){
            name_of_section_string=getString(R.string.section_documentaries);
        }else if(name_of_section_string.equalsIgnoreCase("OTHERS")){
            name_of_section_string=getString(R.string.section_others);
        }else if(name_of_section_string.equalsIgnoreCase("GAMES")){
            name_of_section_string=getString(R.string.section_games);
        }

        if(brand_of_section_string.equalsIgnoreCase("FOREIGN")){
            brand_of_section_string=getString(R.string.brand_of_section_foreign);
        }else if(brand_of_section_string.equalsIgnoreCase("ARABIC")){
            brand_of_section_string=getString(R.string.brand_of_section_arabic);
        }else if(brand_of_section_string.equalsIgnoreCase("INDIAN")){
            brand_of_section_string=getString(R.string.brand_of_section_indian);
        }else if(brand_of_section_string.equalsIgnoreCase("TURKISH")){
            brand_of_section_string=getString(R.string.brand_of_section_turkish);
        }else if(brand_of_section_string.equalsIgnoreCase("KOREAN")){
            brand_of_section_string=getString(R.string.brand_of_section_korean);
        }else if(brand_of_section_string.equalsIgnoreCase("JAPANESE")){
            brand_of_section_string=getString(R.string.brand_of_section_japanese);
        }else if(brand_of_section_string.equalsIgnoreCase("CHINESE")){
            brand_of_section_string=getString(R.string.brand_of_section_chinese);
        }else if(brand_of_section_string.equalsIgnoreCase("THAI")){
            brand_of_section_string=getString(R.string.brand_of_section_thai);
        }else if(brand_of_section_string.equalsIgnoreCase("INDONESIAN")){
            brand_of_section_string=getString(R.string.brand_of_section_Indonesian);
        }else if(brand_of_section_string.equalsIgnoreCase("ARABIC CARTONS")){
            brand_of_section_string=getString(R.string.brand_of_section_arabic_cartons);
        }else if(brand_of_section_string.equalsIgnoreCase("MOVIES TRANSLATED")){
            brand_of_section_string=getString(R.string.brand_of_section_animations_movies_translated);
        }else if(brand_of_section_string.equalsIgnoreCase("MOVIES DABBED")){
            brand_of_section_string=getString(R.string.brand_of_section_animations_movies_dabbed);
        }else if(brand_of_section_string.equalsIgnoreCase("SERIES TRANSLATED")){
            brand_of_section_string=getString(R.string.brand_of_section_animations_series_translated);
        }else if(brand_of_section_string.equalsIgnoreCase("SERIES DABBED")){
            brand_of_section_string=getString(R.string.brand_of_section_animations_series_dabbed);
        }else if(brand_of_section_string.equalsIgnoreCase("PLAYS")){
            brand_of_section_string=getString(R.string.brand_of_section_plays);
        }else if(brand_of_section_string.equalsIgnoreCase("DOCUMENTARIES")){
            brand_of_section_string=getString(R.string.brand_of_section_documentaries);
        }else if(brand_of_section_string.equalsIgnoreCase("OTHERS")){
            brand_of_section_string=getString(R.string.brand_of_section_others);
        }else if(brand_of_section_string.equalsIgnoreCase("GAMES")){
            brand_of_section_string=getString(R.string.brand_of_section_games);
        }

        if(category_string.equalsIgnoreCase("ACTION")){
            category_string=getString(R.string.categories_action);
        }else if(category_string.equalsIgnoreCase("WAR")){
            category_string=getString(R.string.categories_war);
        }else if(category_string.equalsIgnoreCase("CRIME")){
            category_string=getString(R.string.categories_crime);
        }else if(category_string.equalsIgnoreCase("MYSTERY")){
            category_string=getString(R.string.categories_mystery);
        }else if(category_string.equalsIgnoreCase("ADVENTURE")){
            category_string=getString(R.string.categories_adventure);
        }else if(category_string.equalsIgnoreCase("DRAMA")){
            category_string=getString(R.string.categories_drama);
        }else if(category_string.equalsIgnoreCase("ROMANTIC")){
            category_string=getString(R.string.categories_romantic);
        }else if(category_string.equalsIgnoreCase("COMEDIAN")){
            category_string=getString(R.string.categories_comedian);
        }else if(category_string.equalsIgnoreCase("HORROR")){
            category_string=getString(R.string.categories_horror);
        }else if(category_string.equalsIgnoreCase("SCIENCE FICTION IMOGEN")){
            category_string=getString(R.string.categories_science_fiction_imogen);
        }else if(category_string.equalsIgnoreCase("FANTASY")){
            category_string=getString(R.string.categories_fantasy);
        }else if(category_string.equalsIgnoreCase("FAMILY")){
            category_string=getString(R.string.categories_family);
        }else if(category_string.equalsIgnoreCase("HISTORY")){
            category_string=getString(R.string.categories_history);
        }else if(category_string.equalsIgnoreCase("MUSICAL")){
            category_string=getString(R.string.categories_musical);
        }else if(category_string.equalsIgnoreCase("SPORT")){
            category_string=getString(R.string.categories_sport);
        }else if(category_string.equalsIgnoreCase("WESTERN")){
            category_string=getString(R.string.categories_western);
        }else if(category_string.equalsIgnoreCase("THRILLING")){
            category_string=getString(R.string.categories_thrilling);
        }else if(category_string.equalsIgnoreCase("DABBED")){
            category_string=getString(R.string.categories_dabbed);
        }else if(category_string.equalsIgnoreCase("TRANSLATED")){
            category_string=getString(R.string.categories_translated);
        }else if(category_string.equalsIgnoreCase("PLAYS")){
            category_string=getString(R.string.categories_plays);
        }else if(category_string.equalsIgnoreCase("DOCUMENTARIES")){
            category_string=getString(R.string.categories_documentaries);
        }else if(category_string.equalsIgnoreCase("OTHERS")){
            category_string=getString(R.string.categories_others);
        }else if(category_string.equalsIgnoreCase("GAMES")){
            category_string=getString(R.string.categories_games);
        }

        //make the category text view with games ,documentaries ,plays, games with name of section just
        if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_plays))
            ||name_of_section_string.equalsIgnoreCase(getString(R.string.section_documentaries))
                ||name_of_section_string.equalsIgnoreCase(getString(R.string.section_documentaries))
                    ||name_of_section_string.equalsIgnoreCase(getString(R.string.section_games))
                        ||name_of_section_string.equalsIgnoreCase(getString(R.string.section_others))){
            category_text_view.setText(name_of_section_string);

        }else{
            category_text_view.setText(name_of_section_string+" , "+brand_of_section_string+" , "+category_string);

        }

        startPutTheSeasonsNo(Integer.valueOf(session),name_of_section_string,brand_of_section_string);


    }

    private void startRetrofitForGetAllComments(int id,int limit) {
        Retrofit_connection.getRetrofit_connection_instance().getAllComments(id,limit).enqueue(new Callback<List<Items_of_comments>>() {
            @Override
            public void onResponse(Call<List<Items_of_comments>> call, Response<List<Items_of_comments>> response) {

                if(response.isSuccessful()){

                    if(response.body().size()<1){
                        Toasty.custom(About_all_activity.this,getString(R.string.no_more_comments),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                        call.cancel();
                    }



                    items_of_comments.addAll(response.body());
                    commentes_adapter.notifyItemRangeChanged(commentes_adapter.getItemCount(),items_of_comments.size()-1);
                    if(get_all_comments_dialog.isShoen()){
                        get_all_comments_dialog.dismiss();
                    }

                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);


                }


            }

            @Override
            public void onFailure(Call<List<Items_of_comments>> call, Throwable t) {
                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                retrofit_on_failure_actions =new Retrofit_on_failure_actions(About_all_activity.this,t.getMessage());
                try {
                    get_all_comments_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_all_comments_dialog.dismiss();
                    Toast.makeText(About_all_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startSetAllDetails(){

        Picasso.get().load("http://goldenbeachye.com/"+name_of_image).fit().centerCrop().into(imageView);
        name_Text_view.setText(name);
        story_text_view.setText(story);



    }

    private void startOpenYoutube(String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    private void startRetrofitToGetSumOfEvaluationValues(int id_of_all){
        Retrofit_connection.getRetrofit_connection_instance().getSumOfEvaluationValues(id_of_all).enqueue(new Callback<Sum_of_evaluations>() {
            @Override
            public void onResponse(Call<Sum_of_evaluations> call, Response<Sum_of_evaluations> response) {
                    if(response.isSuccessful()){
                        startFilterTheBasicSumOfEvaluationValues(response.body().getSum_of_evaluation());
                        //Toast.makeText(About_all_activity.this, String.valueOf(response.body().getSum_of_evaluation()), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<Sum_of_evaluations> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(About_all_activity.this,t.getMessage());
                try {
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    Toast.makeText(About_all_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startFilterTheBasicSumOfEvaluationValues(float sum_of_evaluation){

        if(sum_of_evaluation>0 && sum_of_evaluation<=25){
            about_all_rating_bar.setRating(0.5f);
            num_of_rating_text_view.setText("0.5");
        }else if(sum_of_evaluation>25 && sum_of_evaluation<=50){
            about_all_rating_bar.setRating(1.0f);
            num_of_rating_text_view.setText("1.0");
        }else if(sum_of_evaluation>50 && sum_of_evaluation<=75){
            about_all_rating_bar.setRating(1.5f);
            num_of_rating_text_view.setText("1.5");
        }else if(sum_of_evaluation>75 && sum_of_evaluation<=100){
            about_all_rating_bar.setRating(1.5f);
            num_of_rating_text_view.setText("1.5");
        }else if(sum_of_evaluation>100 && sum_of_evaluation<=125){
            about_all_rating_bar.setRating(2.0f);
            num_of_rating_text_view.setText("2.0");
        }else if(sum_of_evaluation>125 && sum_of_evaluation<=150){
            about_all_rating_bar.setRating(2.5f);
            num_of_rating_text_view.setText("2.5");
        }else if(sum_of_evaluation>150 && sum_of_evaluation<=175){
            about_all_rating_bar.setRating(3.0f);
            num_of_rating_text_view.setText("3.0");
        }else if(sum_of_evaluation>175 && sum_of_evaluation<=200){
            about_all_rating_bar.setRating(3.5f);
            num_of_rating_text_view.setText("3.5");
        }else if(sum_of_evaluation>200 && sum_of_evaluation<=225){
            about_all_rating_bar.setRating(4.0f);
            num_of_rating_text_view.setText("4.0");
        }else if(sum_of_evaluation>225 && sum_of_evaluation<=250){
            about_all_rating_bar.setRating(4.5f);
            num_of_rating_text_view.setText("4.5");
        }else if(sum_of_evaluation>225){
            about_all_rating_bar.setRating(5.0f);
            num_of_rating_text_view.setText("5.0");
        }else{
            num_of_rating_text_view.setText("0.0");
        }


    }

    private void startShowAndHideRecycleView(){
        if(show_hide_comments_text_view.getText().toString().equalsIgnoreCase(getResources().getString(R.string.about_all_activity_show_comments_text_views))){
            linearLayout.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
            show_hide_comments_text_view.setText(getResources().getString(R.string.about_all_activity_hide_comments_text_views));
            show_hide_comments_image_view.setImageDrawable(getResources().getDrawable(R.drawable.row_up_icon));

        }else{
            linearLayout.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            show_hide_comments_text_view.setText(getResources().getString(R.string.about_all_activity_show_comments_text_views));
            show_hide_comments_image_view.setImageDrawable(getResources().getDrawable(R.drawable.row_down_icon));

        }
    }

    private void startAddNewWatchingList(String name, int session){
        db=new Sqlite_Database_Connection(this);
        boolean result=db.insertNewWatchingList(name,session);
        if(result==true){
            Toasty.success(About_all_activity.this,getResources().getString(R.string.about_all_activity_added_to_watching_list_successfully),Toast.LENGTH_SHORT,true).show();
        }else{
            Toasty.custom(About_all_activity.this,name_Text_view.getText().toString()+" "+getResources().getString(R.string.about_all_activity_added_to_watching_list_already_found),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
        }

    }

    private void startMoveToEvaluationDialog() {
        Intent intent=new Intent(this,Evaluation_dialog.class);
        Bundle bundle=new Bundle();
        bundle.putString("name_of_all",name_Text_view.getText().toString());
        bundle.putString("id_of_all",String.valueOf(id));
        intent.putExtras(bundle);
        startActivityForResult(intent, EVALUATION_DIALOG_REQUEST_CODE);
        CustomIntent.customType(About_all_activity.this,"up-to-bottom");

    }

    private void startMoveToAddCommentDialog(){
        Intent go_to_add_comment_intent=new Intent(About_all_activity.this,Add_comment_dialog.class);
        Bundle go_to_add_comment_bundle=new Bundle();
        go_to_add_comment_bundle.putString("id",id);
        go_to_add_comment_intent.putExtras(go_to_add_comment_bundle);
        startActivityForResult(go_to_add_comment_intent,ADD_COMMENT_DIALOG_REQUEST_CODE);
        CustomIntent.customType(About_all_activity.this,"up-to-bottom");

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(About_all_activity.this,"fadein-to-fadeout");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== EVALUATION_DIALOG_REQUEST_CODE && resultCode==RESULT_OK){
            recreate();
        }else if(requestCode==ADD_COMMENT_DIALOG_REQUEST_CODE&&resultCode==RESULT_OK){
            recreate();
        }
    }

    private void show_image(){
        Intent openImageIntent=new Intent(About_all_activity.this, Show_image_activity.class);
        Bundle openImageBundle=new Bundle();
        openImageBundle.putString("name_of_image",name_of_image);
        openImageBundle.putString("from","about_all_activity");
        openImageIntent.putExtras(openImageBundle);
        startActivity(openImageIntent);
        CustomIntent.customType(About_all_activity.this,"fadein-to-fadeout");

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
