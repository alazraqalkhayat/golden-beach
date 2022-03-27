package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.All_recycle_view_list_adapter;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.EndlessRecyclerViewScrollListener;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_list_activity extends AppCompatActivity  {


    Toolbar all_list_tool_bar;
    SwipeRefreshLayout swipeRefreshLayout;

    Spinner categories_spinner;
    String [] categories_array;
    ArrayAdapter<String>spinner_array_adapter;

    All_recycle_view_list_adapter recycle_view_list_adapter;
    ArrayList<Items_of_all_list> items_of_all_lists=new ArrayList<>();
    RecyclerView recyclerView;


    All_recycle_view_list_adapter recycle_view_list_adapter2;
    ArrayList<Items_of_all_list> items_of_last_20_of_all_lists;
    RecyclerView recyclerView2;

    My_spot_progress_dialog delete_the_post_dialog;
    My_Flower_progress_dialog get_all_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;

    ProgressBar load_more_progress_bar;
    ImageView reload_image_view;

    Bundle get_data_from_sections_fragment;

    Intent move_to_about_all_activity_intent;
    Bundle move_to_about_all_activity_bundle;

    String get_name_of_section_from_bundle,get_brand_of_section_from_bundle,name_of_category_from_spinner, specification;

    TextView modern,other,header_text_view;

    TextView name_of_section, brand_of_section, name_of_category,
            name, name_of_image, story, trailer,id, session, year;


    View spinner_view;


    Network_connection_broadcaster_receiver connection;

    private int LIMIT =0;

    GridLayoutManager manager;


   // @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_list_activity);

        initViews();

        startAddToolBarSettings();

        initObjects();

        startCheckIfParentOrSubSection();

        initRecycleView();

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_golden));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshActivity();

            }
        });


        all_list_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                items_of_all_lists.clear();

                recycle_view_list_adapter.clearItems();
                recycle_view_list_adapter.notifyDataSetChanged();

                LIMIT=0;

                get_all_dialog.start_dialog();
                name_of_category_from_spinner=categories_array[position];
                startConvertToArabic(get_name_of_section_from_bundle,get_brand_of_section_from_bundle,name_of_category_from_spinner);




            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //get_all_dialog.start_dialog();
                LIMIT +=10;
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);

                if(specification.equalsIgnoreCase("sub_section")){
                    startConvertToArabic(get_name_of_section_from_bundle,get_brand_of_section_from_bundle,name_of_category_from_spinner);

                }else{
                    startRetrofitForGetAllJustByNameOfSection(get_name_of_section_from_bundle, LIMIT);

                }


            }
        });


        reload_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);
                reload_image_view.setVisibility(View.GONE);

                if(specification.equalsIgnoreCase("sub_section")){
                    startConvertToArabic(get_name_of_section_from_bundle,get_brand_of_section_from_bundle,name_of_category_from_spinner);

                }else{
                    startRetrofitForGetAllJustByNameOfSection(get_name_of_section_from_bundle, LIMIT);

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
            CustomIntent.customType(All_list_activity.this,"fadein-to-fadeout");

        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        all_list_tool_bar=(Toolbar)findViewById(R.id.all_list_activity_tool_bar_id);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.all_list_activity_swipe_refresh_layout);
        categories_spinner=(Spinner)findViewById(R.id.movies_list_activity_movie_category_spinner);
        spinner_view=(View)findViewById(R.id.all_list_activity_spinner_line_view);

        header_text_view=(TextView)findViewById(R.id.movies_list_activity_movie_category_text_view);
        modern=(TextView)findViewById(R.id.movies_list_activity_modern_text_view);
        other=(TextView)findViewById(R.id.movies_list_activity_other_text_view);
        other.setText(getString(R.string.movies_list_others_text_view));



        recyclerView2=(RecyclerView)findViewById(R.id.all_list_recycle_view2);
        load_more_progress_bar=(ProgressBar)findViewById(R.id.all_list_activity_load_more_progress_bar);
        reload_image_view=(ImageView) findViewById(R.id.all_list_activity_reload_image_view);



    }

    private void initRecycleView(){

        recyclerView=(RecyclerView) findViewById(R.id.all_list_recycle_view);
        //recyclerView.setHasFixedSize(true);
        manager=(GridLayoutManager)recyclerView.getLayoutManager();
        recyclerView.setLayoutManager(manager);
        recycle_view_list_adapter=new All_recycle_view_list_adapter(All_list_activity.this, items_of_all_lists);
        recyclerView.setAdapter(recycle_view_list_adapter);
    }

    private void initObjects(){
        get_data_from_sections_fragment=getIntent().getExtras();
        specification =get_data_from_sections_fragment.getString("specification");

        if(specification.equalsIgnoreCase("sub_section")){
            get_name_of_section_from_bundle=get_data_from_sections_fragment.getString("name_of_section");
            get_brand_of_section_from_bundle=get_data_from_sections_fragment.getString("brand_of_section");

        }else{
            get_name_of_section_from_bundle=get_data_from_sections_fragment.getString("name_of_section");
        }

        move_to_about_all_activity_intent=new Intent(this, About_all_activity.class);
        move_to_about_all_activity_bundle=new Bundle();

        items_of_last_20_of_all_lists =new ArrayList<>();

        get_all_dialog=new My_Flower_progress_dialog(this);
        delete_the_post_dialog=new My_spot_progress_dialog(this);


    }

    private void startCheckIfParentOrSubSection(){


        if(specification.equalsIgnoreCase("sub_section")){
            startFilterTheNameOfSection(get_name_of_section_from_bundle);

            spinner_array_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories_array);
            spinner_array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categories_spinner.setAdapter(spinner_array_adapter);

            if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_movies))){
                modern.setText(getResources().getString(R.string.movies_list_modern_movies_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.movies_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_series))){
                modern.setText(getResources().getString(R.string.movies_list_modern_series_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.series_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.animations_text_view))){
                modern.setText(getResources().getString(R.string.movies_list_modern_anime_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.animations_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_plays))){
                modern.setText(getResources().getString(R.string.movies_list_modern_plays_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.plays_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_documentaries))){
                modern.setText(getResources().getString(R.string.movies_list_modern_documentaries_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.documentaries_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_others))){
                modern.setText(getResources().getString(R.string.new_new));
                getSupportActionBar().setTitle(getResources().getString(R.string.others_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.section_games))){
                modern.setText(getResources().getString(R.string.movies_list_modern_games_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.games_list));
            }


        }else{

            if(get_name_of_section_from_bundle.equalsIgnoreCase("MOVIES")){
                modern.setText(getResources().getString(R.string.movies_list_modern_movies_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.movies_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("SERIES")){
                modern.setText(getResources().getString(R.string.movies_list_modern_series_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.series_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("ANIMATIONS")){
                modern.setText(getResources().getString(R.string.movies_list_modern_anime_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.animations_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("PLAYS")){
                modern.setText(getResources().getString(R.string.movies_list_modern_plays_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.plays_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("DOCUMENTARIES")){
                modern.setText(getResources().getString(R.string.movies_list_modern_documentaries_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.documentaries_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("OTHERS")){
                modern.setText(getResources().getString(R.string.new_new));
                getSupportActionBar().setTitle(getResources().getString(R.string.others_list));

            }else if(get_name_of_section_from_bundle.equalsIgnoreCase("GAMES")){
                modern.setText(getResources().getString(R.string.movies_list_modern_games_text_view));
                getSupportActionBar().setTitle(getResources().getString(R.string.games_list));
            }

            categories_spinner.setVisibility(View.GONE);
            spinner_view.setVisibility(View.GONE);

            get_all_dialog.start_dialog();
            startRetrofitForGetAllJustByNameOfSection(get_name_of_section_from_bundle, LIMIT);
            startRetrofitForGetLast20OfAllJustByNameOfSection(get_name_of_section_from_bundle);

        }

    }

    private void startAddToolBarSettings(){
        setSupportActionBar(all_list_tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void startFilterTheNameOfSection(String get_name_of_section_from_bundle){
        if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_movies_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_movies_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.movies_list));

            //categories_array=getResources().getStringArray(R.array.categories_series_and_movies_array);


        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_series_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_series_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.series_list));
            //categories_array=getResources().getStringArray(R.array.categories_series_and_movies_array);


        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_animation_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_anime_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.animations_list));

            /*
            if(get_brand_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_animation_body_series))){
                categories_array=getResources().getStringArray(R.array.categories_animation_series_array);
            }else if(get_brand_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_animation_body_movies))){
                categories_array=getResources().getStringArray(R.array.categories_animation_movies_array);
            }else if(get_brand_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_animation_body_movies))){
                categories_array=getResources().getStringArray(R.array.categories_animation_movies_array);
            }else if(get_brand_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_animation_body_movies))){
                categories_array=getResources().getStringArray(R.array.categories_animation_movies_array);
            }

             */


        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_play_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_plays_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.plays_list));
            //categories_array=getResources().getStringArray(R.array.categories_plays_array);

        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_documentaries_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_documentaries_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.documentaries_list));
            //categories_array=getResources().getStringArray(R.array.categories_documentaries_array);

        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_others_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_just_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.others_list));
            //categories_array=getResources().getStringArray(R.array.categories_others_array);

        }else if(get_name_of_section_from_bundle.equalsIgnoreCase(getString(R.string.sections_fragment__expandable_list_view_games_title))){

            modern.setText(getResources().getString(R.string.movies_list_modern_games_text_view));
            getSupportActionBar().setTitle(getResources().getString(R.string.games_list));
            //categories_array=getResources().getStringArray(R.array.categories_games_array);

        }

        categories_array=getResources().getStringArray(R.array.categories_series_and_movies_array);

    }

    private void startConvertToArabic(String name_of_section_string,String brand_of_section_string,String category_string){

        if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_movies))){
            name_of_section_string="MOVIES";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_series))){
            name_of_section_string="SERIES";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.animations_text_view))){
            name_of_section_string="ANIMATIONS";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_plays))){
            name_of_section_string="PLAYS";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_documentaries))){
            name_of_section_string="DOCUMENTARIES";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_others))){
            name_of_section_string="OTHERS";
        }else if(name_of_section_string.equalsIgnoreCase(getString(R.string.section_games))){
            name_of_section_string="GAMES";
        }

        if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_foreign))){
            brand_of_section_string="FOREIGN";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_arabic))){
            brand_of_section_string="ARABIC";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_indian))){
            brand_of_section_string="INDIAN";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_turkish))){
            brand_of_section_string="TURKISH";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_korean))){
            brand_of_section_string="KOREAN";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_japanese))){
            brand_of_section_string="JAPANESE";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_chinese))){
            brand_of_section_string="CHINESE";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_thai))){
            brand_of_section_string="THAI";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_Indonesian))){
            brand_of_section_string="INDONESIAN";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_arabic_cartons))){
            brand_of_section_string="ARABIC CARTONS";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_animations_movies_translated))){
            brand_of_section_string="MOVIES TRANSLATED";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_animations_movies_dabbed))){
            brand_of_section_string="MOVIES DABBED";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_animations_series_translated))){
            brand_of_section_string="SERIES TRANSLATED";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_animations_series_dabbed))){
            brand_of_section_string="SERIES DABBED";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_plays))){
            brand_of_section_string="PLAYS";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_documentaries))){
            brand_of_section_string="DOCUMENTARIES";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_others))){
            brand_of_section_string="OTHERS";
        }else if(brand_of_section_string.equalsIgnoreCase(getString(R.string.brand_of_section_games))){
            brand_of_section_string="GAMES";
        }



        if(category_string.equalsIgnoreCase(getString(R.string.categories_action))){
            category_string="ACTION";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_war))){
            category_string="WAR";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_crime))){
            category_string="CRIME";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_mystery))){
            category_string="MYSTERY";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_adventure))){
            category_string="ADVENTURE";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_drama))){
            category_string="DRAMA";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_romantic))){
            category_string="ROMANTIC";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_comedian))){
            category_string="COMEDIAN";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_horror))){
            category_string="HORROR";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_science_fiction_imogen))){
            category_string="SCIENCE FICTION IMOGEN";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_fantasy))){
            category_string="FANTASY";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_family))){
            category_string="FAMILY";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_history))){
            category_string="HISTORY";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_musical))){
            category_string="MUSICAL";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_sport))){
            category_string="SPORT";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_western))){
            category_string="WESTERN";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_thrilling))){
            category_string="THRILLING";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_dabbed))){
            category_string="DABBED";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_translated))){
            category_string="TRANSLATED";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_plays))){
            category_string="PLAYS";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_documentaries))){
            category_string="DOCUMENTARIES";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_others))){
            category_string="OTHERS";
        }else if(category_string.equalsIgnoreCase(getString(R.string.categories_games))){
            category_string="GAMES";
        }

        startRetrofitForGetAll(name_of_section_string,brand_of_section_string,category_string,LIMIT);
        startRetrofitForGetLast20OfAll(name_of_section_string,brand_of_section_string,category_string);

    }

    private void startRetrofitForGetAll(String name_of_section,String brand_of_section,String name_of_category,int limit){

        Retrofit_connection.getRetrofit_connection_instance().getAll(name_of_section,brand_of_section,name_of_category,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if (response.isSuccessful()) {


                    if (response.body().size() < 1) {
                        Toasty.custom(All_list_activity.this, getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (get_all_dialog.isShoen()) {
                        get_all_dialog.dismiss();
                    }


                    items_of_all_lists.addAll(response.body());


                    if (items_of_all_lists.size() == 1) {
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size());
                    } else if (items_of_all_lists.size() > 1) {
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() - 1);
                    }



                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(All_list_activity.this,t.getMessage());
                    get_all_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_all_dialog.dismiss();
                    Toast.makeText(All_list_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startRetrofitForGetLast20OfAll(String name_of_section, String brand_of_section, String name_of_category){


        Retrofit_connection.getRetrofit_connection_instance().getLast20OfAll(name_of_section,brand_of_section,name_of_category).enqueue(new Callback<List<Items_of_all_list>>() {


            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){

                    items_of_last_20_of_all_lists = (ArrayList<Items_of_all_list>) response.body();
                    recycle_view_list_adapter2=new All_recycle_view_list_adapter(All_list_activity.this, items_of_last_20_of_all_lists);
                    recyclerView2.setAdapter(recycle_view_list_adapter2);
                }

                //get_all_suggestions_progress_dialog.dismisProgressDialog();

            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(All_list_activity.this,t.getMessage());
                try {
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    Toast.makeText(All_list_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startRetrofitForGetAllJustByNameOfSection(String name_of_section,int limit){


        Retrofit_connection.getRetrofit_connection_instance().getAllJustByNameOfSection(name_of_section,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {

                if (response.isSuccessful()) {


                    if (response.body().size() < 1) {
                        Toasty.custom(All_list_activity.this, getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (get_all_dialog.isShoen()) {
                        get_all_dialog.dismiss();
                    }


                    items_of_all_lists.addAll(response.body());


                    if (items_of_all_lists.size() == 1) {
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size());
                    } else if (items_of_all_lists.size() > 1) {
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() - 1);
                    }



                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(All_list_activity.this,t.getMessage());
                    get_all_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_all_dialog.dismiss();
                    Toast.makeText(All_list_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startRetrofitForGetLast20OfAllJustByNameOfSection(String name_of_section){

        Retrofit_connection.getRetrofit_connection_instance().getLast20OfAllJustByNameOfSection(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {


            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_last_20_of_all_lists = (ArrayList<Items_of_all_list>) response.body();
                    recycle_view_list_adapter2=new All_recycle_view_list_adapter(All_list_activity.this, items_of_last_20_of_all_lists);
                    recyclerView2.setAdapter(recycle_view_list_adapter2);
                }

                //get_all_suggestions_progress_dialog.dismisProgressDialog();

            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {
                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(All_list_activity.this,t.getMessage());
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    Toast.makeText(All_list_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void refreshActivity(){
        if(specification.equalsIgnoreCase("sub_section")) {
            Intent i=new Intent(All_list_activity.this,All_list_activity.class);
            Bundle b=new Bundle();
            b.putString("name_of_section",get_name_of_section_from_bundle);
            b.putString("brand_of_section",get_brand_of_section_from_bundle);
            b.putString("specification","sub_section");

            i.putExtras(b);
            startActivity(i);
            this.finish();

        }else{
            Intent i=new Intent(All_list_activity.this,All_list_activity.class);
            Bundle b=new Bundle();
            b.putString("name_of_section",get_name_of_section_from_bundle);
            b.putString("specification","parent_section");
            i.putExtras(b);
            startActivity(i);
            this.finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"right-to-left");

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
