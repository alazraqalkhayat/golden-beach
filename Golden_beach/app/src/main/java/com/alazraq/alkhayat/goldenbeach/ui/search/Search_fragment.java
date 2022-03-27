package com.alazraq.alkhayat.goldenbeach.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Search_method_dialog;
import com.alazraq.alkhayat.goldenbeach.adapters.All_recycle_view_list_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.EndlessRecyclerViewScrollListener;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_fragment extends Fragment {

    CardView search_card_view;
    FloatingActionButton fab;
    TextView search_by,search_method_text_view;
    EditText search_edit_text;
    RatingBar search_rating_bar;
    CheckBox search_check_box;
    LinearLayout spinners_linear_layout;
    Spinner name_of_section_spinner,brand_of_section_spinner,category_spinner;
    Button search_button;

    ImageView back_image_view;
    String [] name_of_section_arr,brand_of_section_arr,category_arr;

    String name_of_section_string,brand_of_section_string,category_string;
    String search_method_string,my_search_method_in_search;

    float evaluation_value;

    ArrayAdapter<String> name_of_section_adapter,brand_of_section_adapter,category_adapter;

    int REQUEST_CODE;

    My_spot_progress_dialog search_dialog;

    Retrofit_on_failure_actions retrofit_on_failure_actions;

    All_recycle_view_list_adapter recycle_view_list_adapter;
    ArrayList<Items_of_all_list> items_of_all_lists=new ArrayList<>();
    RecyclerView recyclerView;

    ProgressBar load_more_progress_bar;
    ImageView reload_image_view;

    int LIMIT;
    GridLayoutManager manager;


    My_Flower_progress_dialog dialog;

    static String test;

    public Search_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initObjects();
        LIMIT=0;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.search_fragment, container, false);;


        initViews(v);

        initRecycleView(v);

        put_the_value_of_search_method_text_view();
        evaluation_value=search_rating_bar.getRating();



        // animation of fab

        Animation instagram_animation= AnimationUtils.loadAnimation(getContext(),R.anim.right_to_left);
        fab.startAnimation(instagram_animation);

        //______________________ all views events _____________________

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), Search_method_dialog.class),REQUEST_CODE);
                CustomIntent.customType(getContext(),"up-to-bottom");

            }
        });


        search_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    whenCheck();
                }else{
                    whenUnCheck();
                }
            }
        });

        name_of_section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                name_of_section_string=name_of_section_arr[position];
                startScanningTheNameOfSection(name_of_section_string);
                // brand_of_section_spinner.setSelection(1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        brand_of_section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                brand_of_section_string=brand_of_section_arr[position];
                startScanningTheBrandOfSection(brand_of_section_string);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                category_string=category_arr[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //items_of_all_lists.clear();


                LIMIT=0;

                if(search_edit_text.getVisibility()!=View.GONE){
                    if(search_edit_text.getText().toString().equalsIgnoreCase("")){
                        Toasty.custom(getContext(),getResources().getString(R.string.input_the_key_word),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

                    }else {
                        dialog.start_dialog();
                        startCheckTheInputData();
                        //startRetrofitToSearchByName(search_edit_text.getText().toString(),LIMIT);
                    }
                }else{
                    startCheckTheInputData();
                }


            }
        });

        back_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_edit_text.setText("");

                startShowOthersViewsAndHideRecycleView();

                LIMIT =0;

                items_of_all_lists.clear();
                recycle_view_list_adapter.clearItems();
                //recycle_view_list_adapter.notifyDataSetChanged();

            }
        });


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                LIMIT +=6;
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);

                reloadAndLoadMoreMethod(LIMIT);
                //startCheckTheInputData();
                //startRetrofitToSearchByName(search_edit_text.getText().toString(),LIMIT);
            }
        });



        reload_image_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);
                reload_image_view.setVisibility(View.GONE);

                reloadAndLoadMoreMethod(LIMIT);

                //startCheckTheInputData();


            }
        });

        initArrays();


        return v;
    }


    private void initViews(View v){
        search_card_view=(CardView)v.findViewById(R.id.search_fragment_card_view);
        fab=(FloatingActionButton)v.findViewById(R.id.search_fragment_fab);

        search_method_text_view =(TextView)v.findViewById(R.id.search_fragment_search_method_text_view);
        search_method_text_view.setVisibility(View.GONE);
        search_by =(TextView)v.findViewById(R.id.search_fragment_search_by_text_view);
        search_edit_text=(EditText)v.findViewById(R.id.search_fragment_search_box_edit_text);
        search_rating_bar=(RatingBar)v.findViewById(R.id.search_fragment_rating_bar);

        search_check_box=(CheckBox) v.findViewById(R.id.search_fragment_check_box);

        spinners_linear_layout=(LinearLayout)v.findViewById(R.id.search_fragment_spinners_linear_layout);
        name_of_section_spinner=(Spinner)v.findViewById(R.id.search_fragment_section_spinner);
        brand_of_section_spinner=(Spinner)v.findViewById(R.id.search_fragment_brand_of_section_spinner);
        category_spinner=(Spinner)v.findViewById(R.id.search_fragment_category_spinner);

        search_button=(Button)v.findViewById(R.id.search_fragment_search_button);

        back_image_view=(ImageView)v.findViewById(R.id.search_fragment_back_image_view);

        load_more_progress_bar=(ProgressBar)v.findViewById(R.id.search_fragment_load_more_progress_bar);
        reload_image_view=(ImageView)v.findViewById(R.id.search_fragment_reload_image_view);
    }

    private void initRecycleView(View v){
        recyclerView=(RecyclerView)v.findViewById(R.id.search_recycle_view);
        recyclerView.setNestedScrollingEnabled(false);
        manager=(GridLayoutManager)recyclerView.getLayoutManager();
        recyclerView.setLayoutManager(manager);
        recycle_view_list_adapter=new All_recycle_view_list_adapter(getContext(), items_of_all_lists);
        recyclerView.setAdapter(recycle_view_list_adapter);

    }

    private void put_the_value_of_search_method_text_view(){
        search_method_text_view.setText(search_method_string);

        if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name)) || search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name_with_details))){
            search_edit_text.setVisibility(View.VISIBLE);
            search_rating_bar.setVisibility(View.GONE);
            search_edit_text.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year)) || search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year_with_details))){
            search_edit_text.setVisibility(View.VISIBLE);
            search_rating_bar.setVisibility(View.GONE);
            search_edit_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation)) || search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation_with_details)) ){
            search_edit_text.setVisibility(View.GONE);
            search_rating_bar.setVisibility(View.VISIBLE);
        }
    }

    private void initArrays(){

        name_of_section_arr=getResources().getStringArray(R.array.sections_array);
        name_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,name_of_section_arr);
        name_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_of_section_spinner.setAdapter(name_of_section_adapter);

    }

    private void initObjects(){
        REQUEST_CODE=101;
        search_method_string=Shared_Helper.getkey(getContext(),"search_method");

        search_dialog =new My_spot_progress_dialog(getContext());

        dialog=new My_Flower_progress_dialog(getContext());

        name_of_section_string="name_of_section";
        brand_of_section_string="brand_of_section";
        category_string="name_of_category";

    }

    private void whenCheck(){
        spinners_linear_layout.setVisibility(View.VISIBLE);
        if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name))){
            search_method_string=getString(R.string.search_methods_name_with_details);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year))){
            search_method_string=getString(R.string.search_methods_year_with_details);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation))){
            search_method_string=getString(R.string.search_methods_evaluation_with_details);
        }

        search_method_text_view.setText(search_method_string);

    }

    private void whenUnCheck(){
        spinners_linear_layout.setVisibility(View.GONE);
        if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name_with_details))){
            search_method_string=getString(R.string.search_methods_name);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year_with_details))){
            search_method_string=getString(R.string.search_methods_year);
        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation_with_details))){
            search_method_string=getString(R.string.search_methods_evaluation);
        }

        search_method_text_view.setText(search_method_string);

    }

    private void startScanningTheNameOfSection(String name_string){

        if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_movies))){

            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_movies);
            brand_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_series))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_series);
            brand_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.animations_text_view))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_animations);
            brand_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_plays))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_plays);
            brand_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_documentaries))) {
            brand_of_section_arr = getResources().getStringArray(R.array.brand_of_sections_array_of_documentaries);
            brand_of_section_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_others))) {
            brand_of_section_arr = getResources().getStringArray(R.array.brand_of_sections_array_of_others);
            brand_of_section_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_games))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_games);
            brand_of_section_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);



        }else{
            brand_of_section_spinner.setEnabled(true);
            category_spinner.setEnabled(true);
        }
    }

    private void startScanningTheBrandOfSection(String name_string){

        if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_plays))){
            category_arr=getResources().getStringArray(R.array.categories_plays_array);
            category_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_documentaries))){
            category_arr=getResources().getStringArray(R.array.categories_documentaries_array);
            category_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.categories_others))) {
            category_arr = getResources().getStringArray(R.array.categories_others_array);
            category_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.categories_games))) {
            category_arr = getResources().getStringArray(R.array.categories_games_array);
            category_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else{
            category_arr=getResources().getStringArray(R.array.categories_series_and_movies_array);
            category_adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);
        }
        /*
         if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_animations_series))){
            category_arr=getResources().getStringArray(R.array.categories_animation_series_array);
            category_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_animations_movies))){
            category_arr=getResources().getStringArray(R.array.categories_animation_movies_array);
            category_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_arr);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(category_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_plays))){
             category_arr=getResources().getStringArray(R.array.categories_plays_array);
             category_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_arr);
             category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             category_spinner.setAdapter(category_adapter);

         }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.brand_of_section_documentaries))){
             category_arr=getResources().getStringArray(R.array.categories_documentaries_array);
             category_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_arr);
             category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             category_spinner.setAdapter(category_adapter);

         }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.categories_others))) {
             category_arr = getResources().getStringArray(R.array.categories_others_array);
             category_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category_arr);
             category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             category_spinner.setAdapter(category_adapter);

         }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.categories_games))) {
             category_arr = getResources().getStringArray(R.array.categories_games_array);
             category_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category_arr);
             category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             category_spinner.setAdapter(category_adapter);

         }else{
             category_arr=getResources().getStringArray(R.array.categories_series_and_movies_array);
             category_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_arr);
             category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             category_spinner.setAdapter(category_adapter);
         }

         */
    }

    private void startCheckTheInputData(){

        String []arr=startConvertToArabic(name_of_section_string,brand_of_section_string,category_string);
        name_of_section_string=arr[0];
        brand_of_section_string=arr[1];
        category_string=arr[2];


        if(!search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation))
                || search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation_with_details)) ){

                if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name))){
                    startRetrofitToSearchByName(search_edit_text.getText().toString(),LIMIT);

                }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name_with_details))){
                    startRetrofitToSearchByNameWithDetails(search_edit_text.getText().toString(),name_of_section_string
                                                            ,brand_of_section_string,category_string,LIMIT);

                }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year))){
                    startRetrofitToSearchByYear(Integer.valueOf(search_edit_text.getText().toString()),LIMIT);
                }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year_with_details))){
                    startRetrofitToSearchByYearWithDetails(Integer.valueOf(search_edit_text.getText().toString()),name_of_section_string
                                                            ,brand_of_section_string,category_string,LIMIT);
                }




        }else {
            if(search_rating_bar.getRating()==0.0){
                Toasty.custom(getContext(),getResources().getString(R.string.you_have_to_input_evaluation_value),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
            }else{
                if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation))){
                    //my_search_method_in_search="evaluation";
                    //startRetrofitToSearchByName(name_of_section_string,brand_of_section_string,category_string,search_edit_text.getText().toString(),0,0.0f,my_search_method_in_search);
                    Toasty.custom(getContext(),getResources().getString(R.string.this_feature_is_not_available_now),R.drawable.sad_icon,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

                    //Toast.makeText(getContext(), getString(R.string.this_feature_is_not_available_now), Toast.LENGTH_SHORT).show();
                }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_evaluation_with_details))){
                    //my_search_method_in_search="evaluation_with_details";
                    //startRetrofitToSearchByName(name_of_section_string,brand_of_section_string,category_string,search_edit_text.getText().toString(),0,0.0f,my_search_method_in_search);
                    Toasty.custom(getContext(),getResources().getString(R.string.this_feature_is_not_available_now),R.drawable.sad_icon,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

                    //Toast.makeText(getContext(), getString(R.string.this_feature_is_not_available_now), Toast.LENGTH_SHORT).show();

                }


            }
        }



    }

    private void reloadAndLoadMoreMethod(int limit){
        if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name))){

            startRetrofitToSearchByName(search_edit_text.getText().toString(),limit);

        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_name_with_details))){

            startRetrofitToSearchByNameWithDetails(search_edit_text.getText().toString(),name_of_section_string
                    ,brand_of_section_string,category_string,limit);

        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year))){

            startRetrofitToSearchByYear(Integer.valueOf(search_edit_text.getText().toString()),limit);

        }else if(search_method_string.equalsIgnoreCase(getString(R.string.search_methods_year_with_details))){

            startRetrofitToSearchByYearWithDetails(Integer.valueOf(search_edit_text.getText().toString()),name_of_section_string
                    ,brand_of_section_string,category_string,limit);
        }
    }
    private String[] startConvertToArabic(String name_of_section_string,String brand_of_section_string,String category_string){


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

        String [] arr={name_of_section_string,brand_of_section_string,category_string};
        return arr;

    }

    private void startRetrofitToSearchByName(String name,int limit){

        startShowRecycleViewAndHideOthers();
        Retrofit_connection.getRetrofit_connection_instance().searchByName(name,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {

                if (response.isSuccessful()) {



                    if (response.body().size() < 1) {
                        Toasty.custom(getContext(), getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (dialog.isShoen()) {
                        dialog.dismiss();
                    }


                        items_of_all_lists.addAll(response.body());


                        if(items_of_all_lists.size()==1){
                            recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() );
                        }else if(items_of_all_lists.size()>1){
                            recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() -1);
                        }

                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);



                }

                }


            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                    dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startRetrofitToSearchByNameWithDetails(String name,String name_of_section,String brand_of_section,String name_of_category,int limit){

        startShowRecycleViewAndHideOthers();
        Retrofit_connection.getRetrofit_connection_instance().searchByNameWithDetails(name,name_of_section,brand_of_section,name_of_category,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {

                if (response.isSuccessful()) {



                    if (response.body().size() < 1) {
                        Toasty.custom(getContext(), getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (dialog.isShoen()) {
                        dialog.dismiss();
                    }


                    items_of_all_lists.addAll(response.body());


                    if(items_of_all_lists.size()==1){
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() );
                    }else if(items_of_all_lists.size()>1){
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() -1);
                    }






                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);



                }


            }


            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                    dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startRetrofitToSearchByYear(int year,int limit){

        startShowRecycleViewAndHideOthers();
        Retrofit_connection.getRetrofit_connection_instance().searchByYear(year,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {

                if (response.isSuccessful()) {


                    if (response.body().size() < 1) {
                        Toasty.custom(getContext(), getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (dialog.isShoen()) {
                        dialog.dismiss();
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
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                    dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startRetrofitToSearchByYearWithDetails(int year,String name_of_section,String brand_of_section,String name_of_category,int limit){

        startShowRecycleViewAndHideOthers();
        Retrofit_connection.getRetrofit_connection_instance().searchByYearWithDetails(year,name_of_section,brand_of_section,name_of_category,limit).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {

                if (response.isSuccessful()) {



                    if (response.body().size() < 1) {
                        Toasty.custom(getContext(), getString(R.string.no_more_items), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();
                        call.cancel();
                    }

                    if (dialog.isShoen()) {
                        dialog.dismiss();
                    }


                    items_of_all_lists.addAll(response.body());


                    if(items_of_all_lists.size()==1){
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() );
                    }else if(items_of_all_lists.size()>1){
                        recycle_view_list_adapter.notifyItemRangeChanged(recycle_view_list_adapter.getItemCount(), items_of_all_lists.size() -1);
                    }






                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);



                }


            }


            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                    dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startShowRecycleViewAndHideOthers(){
        search_by.setVisibility(View.GONE);
        search_method_text_view.setVisibility(View.GONE);
        search_card_view.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        back_image_view.setVisibility(View.VISIBLE);

    }

    private void startShowOthersViewsAndHideRecycleView(){
        search_by.setVisibility(View.GONE);
        search_method_text_view.setVisibility(View.GONE);
        search_card_view.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        back_image_view.setVisibility(View.GONE);
        load_more_progress_bar.setIndeterminate(false);
        load_more_progress_bar.setVisibility(View.GONE);
        //LIMIT-=2;
        reload_image_view.setVisibility(View.GONE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==getActivity().RESULT_OK){

            search_check_box.setChecked(false);
            search_method_string=Shared_Helper.getkey(getContext(),"search_method");
            search_edit_text.setText("");
            search_rating_bar.setRating(0.0f);
            put_the_value_of_search_method_text_view();

        }

    }


}
