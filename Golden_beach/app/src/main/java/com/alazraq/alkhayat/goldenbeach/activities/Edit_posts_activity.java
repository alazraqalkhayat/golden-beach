package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_posts_activity extends AppCompatActivity {

    ImageView back_image_view,home_image_view;
    CircleImageView post_image;
    CheckBox checkBox;
    Spinner name_of_section_spinner,brand_of_section_spinner,category_spinner;
    EditText name, storyEdit,trailer,year,session;
    Button update_post_button;

    Bitmap bitmap;

    String [] name_of_section_arr,brand_of_section_arr,category_arr;

    String name_of_section_string,brand_of_section_string,category_string;

    ArrayAdapter<String> name_of_section_adapter,brand_of_section_adapter,category_adapter;

    String name_of_image;



    int REQUEST_CODE;

    boolean imageIsNotNull;

    My_spot_progress_dialog add_new_post_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    Bundle get_data_bundle;
    String id_string,name_string,story_string,trailer_string,year_string,session_string;

    Network_connection_broadcaster_receiver connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_posts_activity);

        initObjects();

        startGetDataFromBundle();

        initViews();

        initArrays();




        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    post_image.setVisibility(View.VISIBLE);


                }else{
                    post_image.setVisibility(View.GONE);
                }
            }
        });

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
                startActivity(new Intent(Edit_posts_activity.this, MainActivity.class));
                CustomIntent.customType(Edit_posts_activity.this,"fadein-to-fadeout");

            }
        });


        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenGallery();
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

        update_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheckTheInputData();
            }
        });


    }


    private void startGetDataFromBundle(){
        get_data_bundle=getIntent().getExtras();

        id_string=get_data_bundle.getString("id");
        name_string=get_data_bundle.getString("name");
        story_string=get_data_bundle.getString("story");
        trailer_string=get_data_bundle.getString("trailer");
        year_string=get_data_bundle.getString("year");
        session_string=get_data_bundle.getString("session");

    }

    private void initViews(){
        back_image_view=(ImageView)findViewById(R.id.edit_post_activity_back_image_view);
        home_image_view=(ImageView)findViewById(R.id.edit_post_activity_home_image_view);

        post_image =(CircleImageView)findViewById(R.id.edit_post_activity_post_image_view);
        checkBox=(CheckBox)findViewById(R.id.edit_posts_activity_check_box);
        name_of_section_spinner=(Spinner)findViewById(R.id.edit_posts_activity_section_spinner);
        brand_of_section_spinner=(Spinner)findViewById(R.id.edit_posts_activity_brand_of_section_spinner);
        category_spinner=(Spinner)findViewById(R.id.edit_posts_activity_category_spinner);

        name=(EditText)findViewById(R.id.edit_posts_activity_name_edit_text);
        storyEdit =(EditText)findViewById(R.id.edit_posts_activity_story_edit_text);
        trailer=(EditText)findViewById(R.id.edit_posts_activity_trailer_edit_text);
        year=(EditText)findViewById(R.id.edit_posts_activity_year_edit_text);
        session=(EditText)findViewById(R.id.edit_posts_activity_session_edit_text);

        update_post_button =(Button)findViewById(R.id.add_new_post_add_button);

        name.setText(name_string);
        storyEdit.setText(story_string);
        trailer.setText(trailer_string);
        year.setText(year_string);
        session.setText(session_string);

    }

    private void initArrays(){
        name_of_section_arr=getResources().getStringArray(R.array.sections_array);

        name_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,name_of_section_arr);
        name_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_of_section_spinner.setAdapter(name_of_section_adapter);

    }

    private void initObjects(){
        imageIsNotNull=false;
        REQUEST_CODE=33;
        add_new_post_dialog =new My_spot_progress_dialog(this);
        get_data_bundle=getIntent().getExtras();

    }

    private void startScanningTheNameOfSection(String name_string){

        if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_movies))){

            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_movies);
            brand_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_series))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_series);
            brand_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.animations_text_view))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_animations);
            brand_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }
        else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_plays))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_plays);
            brand_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);


        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_documentaries))) {
            brand_of_section_arr = getResources().getStringArray(R.array.brand_of_sections_array_of_documentaries);
            brand_of_section_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_others))) {
            brand_of_section_arr = getResources().getStringArray(R.array.brand_of_sections_array_of_others);
            brand_of_section_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand_of_section_arr);
            brand_of_section_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brand_of_section_spinner.setAdapter(brand_of_section_adapter);

        }else if(name_string.equalsIgnoreCase(getResources().getString(R.string.section_games))){
            brand_of_section_arr=getResources().getStringArray(R.array.brand_of_sections_array_of_games);
            brand_of_section_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brand_of_section_arr);
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

    public void startOpenGallery(){

        Intent intent=new Intent().setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();

            //name_of_produce.setText(String.valueOf(path));
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                post_image.setImageBitmap(bitmap);

                imageIsNotNull=true;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public String startConvertBitmapToString(){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte [] byteArray=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray,Base64.DEFAULT);





    }

    private void startCheckTheInputData(){
        if(checkBox.isChecked()){
            if(name.getText().toString().equalsIgnoreCase("")
                    || storyEdit.getText().toString().equalsIgnoreCase("")
                    ||trailer.getText().toString().equalsIgnoreCase("")
                    ||year.getText().toString().equalsIgnoreCase("")
                    ||session.getText().toString().equalsIgnoreCase("")){

                Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();


            }else{
                if(imageIsNotNull==false){
                    Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.add_the_image),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

                }else{

                    startConvertToArabic(name_of_section_string,brand_of_section_string,category_string,true);
                    /*
                    name_of_image=startConvertBitmapToString();
                    add_new_post_dialog.start_dialog_for_uploading_the_post();
                    startRetrofitToUpdatePostWithImage(Integer.valueOf(id_string),name_of_section_string.toLowerCase(),brand_of_section_string.toLowerCase(),category_string.toLowerCase(),name.getText().toString().toUpperCase(),
                                                            Integer.valueOf(session.getText().toString()),
                                                            name_of_image,Integer.valueOf(year.getText().toString())
                                                            ,storyEdit.getText().toString().toUpperCase(),trailer.getText().toString());

                     */

                }
            }
        }else{
            if(name.getText().toString().equalsIgnoreCase("")
                    || storyEdit.getText().toString().equalsIgnoreCase("")
                    ||trailer.getText().toString().equalsIgnoreCase("")
                    ||year.getText().toString().equalsIgnoreCase("")
                    ||session.getText().toString().equalsIgnoreCase("")){

                Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

            }else{

                startConvertToArabic(name_of_section_string,brand_of_section_string,category_string,false);

                /*
                add_new_post_dialog.start_dialog_for_uploading_the_post();
                startRetrofitToUpdatePostWithOutImage(Integer.valueOf(id_string),name_of_section_string,brand_of_section_string,category_string,name.getText().toString(),
                                                        Integer.valueOf(session.getText().toString())
                                                        ,Integer.valueOf(year.getText().toString())
                                                        ,storyEdit.getText().toString(),trailer.getText().toString());

                 */

            }
        }


    }

    private void startConvertToArabic(String name_of_section_string,String brand_of_section_string,String category_string,boolean with_image){

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



        if(with_image==true){
            name_of_image=startConvertBitmapToString();
            add_new_post_dialog.start_dialog_for_uploading_the_post();
            startRetrofitToUpdatePostWithImage(Integer.valueOf(id_string),name_of_section_string.toLowerCase(),brand_of_section_string.toLowerCase(),category_string.toLowerCase(),name.getText().toString().toUpperCase(),
                    Integer.valueOf(session.getText().toString()),
                    name_of_image,Integer.valueOf(year.getText().toString())
                    ,storyEdit.getText().toString().toUpperCase(),trailer.getText().toString());

        }else{
            add_new_post_dialog.start_dialog_for_uploading_the_post();
            startRetrofitToUpdatePostWithOutImage(Integer.valueOf(id_string),name_of_section_string,brand_of_section_string,category_string,name.getText().toString(),
                    Integer.valueOf(session.getText().toString())
                    ,Integer.valueOf(year.getText().toString())
                    ,storyEdit.getText().toString(),trailer.getText().toString());

        }

    }

    private void startRetrofitToUpdatePostWithImage(int id,String name_of_section, String brand_of_section,
                                                    String name_of_category, String name, int session,
                                                    String name_of_image, int year, String story, String trailer){

        Retrofit_connection.getRetrofit_connection_instance().updatePostsWithImage(id,name_of_section,brand_of_section,name_of_category,name,session,name_of_image,year,story,trailer).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){


                    if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                        Toasty.success(Edit_posts_activity.this,getResources().getString(R.string.updating_successful), Toast.LENGTH_SHORT,true).show();
                        //startActivity(new Intent(Edit_posts_activity.this, Admin_control_panel_activity.class));
                        Edit_posts_activity.this.finish();
                    }

                    add_new_post_dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                try {
                    add_new_post_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(Edit_posts_activity.this,t.getMessage());

                    if(t.getMessage().equalsIgnoreCase("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $")){
                        Toasty.success(Edit_posts_activity.this,getResources().getString(R.string.uploading_successful),Toast.LENGTH_SHORT,true).show();
                        Edit_posts_activity.this.finish();

                    }else{
                        retrofit_on_failure_actions.showAction();
                    }

                }catch (Exception e){
                    add_new_post_dialog.dismiss();
                    Toast.makeText(Edit_posts_activity.this, "catch exception", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void startRetrofitToUpdatePostWithOutImage(int id,String name_of_section, String brand_of_section,
                                       String name_of_category, String name, int session,
                                       int year, String story, String trailer){
        Retrofit_connection.getRetrofit_connection_instance().updatePostsWithOutImage(id,name_of_section,brand_of_section,name_of_category,name,session,year,story,trailer).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){


                    if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Edit_posts_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                        Toasty.success(Edit_posts_activity.this,getResources().getString(R.string.updating_successful), Toast.LENGTH_SHORT,true).show();
                        //startActivity(new Intent(Edit_posts_activity.this, Admin_control_panel_activity.class));
                        Edit_posts_activity.this.finish();
                    }

                    add_new_post_dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                try {
                    add_new_post_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(Edit_posts_activity.this,t.getMessage());

                    retrofit_on_failure_actions.showAction();

                }catch (Exception e){
                    add_new_post_dialog.dismiss();
                    Toast.makeText(Edit_posts_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
