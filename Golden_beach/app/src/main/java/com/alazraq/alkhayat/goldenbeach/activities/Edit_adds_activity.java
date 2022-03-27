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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_adds_activity extends AppCompatActivity {

    ImageView add_image;
    EditText name_of_owner,description;
    Button update_add_button;
    CheckBox checkBox;

    Bitmap bitmap;

    My_spot_progress_dialog update_add_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    int REQUEST_CODE;
    boolean imageIsNotNull;

    String name_of_image,date,name_of_owner_string,description_string,id_string;

    Bundle get_data_bundle;

    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_adds_activity);


        startGetDataFromBundle();

        startInitViews();

        startInitObjects();

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenGallery();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    add_image.setVisibility(View.VISIBLE);


                }else{
                    add_image.setVisibility(View.GONE);
                }
            }
        });

        update_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheckTheInputData();
            }
        });


    }

    private void startInitViews(){
        add_image=(ImageView)findViewById(R.id.edit_adds_activity_add_image_view);
        checkBox=(CheckBox)findViewById(R.id.edit_adds_activity_check_box);
        name_of_owner=(EditText)findViewById(R.id.edit_adds_activity_name_of_owner_edit_text);
        description=(EditText)findViewById(R.id.edit_adds_activity_description_edit_text);
        update_add_button =(Button)findViewById(R.id.edit_adds_activity_update_button);

        name_of_owner.setText(name_of_owner_string);
        description.setText(description_string);


    }

    private void startInitObjects(){
        REQUEST_CODE=22;
        imageIsNotNull=false;
        update_add_dialog =new My_spot_progress_dialog(this);

    }

    private void startGetDataFromBundle(){
        get_data_bundle=getIntent().getExtras();
        name_of_owner_string=get_data_bundle.getString("name_of_owner");
        description_string=get_data_bundle.getString("description");
        id_string=get_data_bundle.getString("id");
    }

    private void startCheckTheInputData(){
        if(checkBox.isChecked()){
            if(name_of_owner.getText().toString().equalsIgnoreCase("")
                    ||description.getText().toString().equalsIgnoreCase("")){
                Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();

            }else{
                if(imageIsNotNull==false){
                    Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.add_the_image),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();
                }else{
                    update_add_dialog.start_dialog_for_uploading_the_add();
                    date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    name_of_image=startConvertBitmapToString();
                    startRetrofitToUpdateAddWithImage(Integer.valueOf(id_string),name_of_owner.getText().toString(),name_of_image,description.getText().toString(),date);
                }
            }

        }else{

            if(name_of_owner.getText().toString().equalsIgnoreCase("")
                    ||description.getText().toString().equalsIgnoreCase("")){
                Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();

            }else{
                date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                update_add_dialog.start_dialog_for_uploading_the_add();
                startRetrofitToUpdateAddWithOutImage(Integer.valueOf(id_string),name_of_owner.getText().toString().toUpperCase(),description.getText().toString().toUpperCase(),date);
                //Toast.makeText(Edit_adds_activity.this, id_string+"  "+date, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startOpenGallery(){

        Intent intent=new Intent().setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();

            //name_of_produce.setText(String.valueOf(path));
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                add_image.setImageBitmap(bitmap);

                imageIsNotNull=true;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private String startConvertBitmapToString(){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte [] byteArray=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray,Base64.DEFAULT);





    }

    private void startRetrofitToUpdateAddWithImage(int add_id, String name_of_owner, String name_of_image_of_add,
                                                   String description, String date_of_add){

        Retrofit_connection.getRetrofit_connection_instance().updateAddWithImage(add_id,name_of_owner,name_of_image_of_add,description,date_of_add).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){


                   if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                       Toasty.success(Edit_adds_activity.this,getResources().getString(R.string.updating_successful),Toast.LENGTH_SHORT,true).show();
                       startActivity(new Intent(Edit_adds_activity.this,Adds_management_activity.class));
                        Edit_adds_activity.this.finish();
                   }


                    update_add_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {

                try {
                    update_add_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(Edit_adds_activity.this,t.getMessage());

                    if(t.getMessage().equalsIgnoreCase("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $")){
                        Toasty.success(Edit_adds_activity.this,getResources().getString(R.string.uploading_successful),Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(Edit_adds_activity.this, Adds_management_activity.class));
                        Edit_adds_activity.this.finish();

                    }else{
                        retrofit_on_failure_actions.showAction();
                    }


                }catch (Exception e){
                    update_add_dialog.dismiss();
                    Toast.makeText(Edit_adds_activity.this, "catch exception", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }




    private void startRetrofitToUpdateAddWithOutImage(int add_id, String name_of_owner,
                                                   String description, String date_of_add){

        Retrofit_connection.getRetrofit_connection_instance().updateAddWithOutImage(add_id,name_of_owner,description,date_of_add).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){


                   if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Edit_adds_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                       Toasty.success(Edit_adds_activity.this,getResources().getString(R.string.updating_successful),Toast.LENGTH_SHORT,true).show();
                       startActivity(new Intent(Edit_adds_activity.this,Adds_management_activity.class));
                       Edit_adds_activity.this.finish();

                   }


                    update_add_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                try {
                    update_add_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(Edit_adds_activity.this,t.getMessage());

                    retrofit_on_failure_actions.showAction();

                }catch (Exception e){
                    update_add_dialog.dismiss();
                    Toast.makeText(Edit_adds_activity.this, "catch exception", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

        /*
    public void startAlertDialogForBadInternetConnection(){
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable-v21.network_icon)
                .setContentText(this.getResources().getString(R.string.bad_internet_connection))
                .hideConfirmButton()
                .setCancelButton(this.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
 */

    private void startChattingService(){
        Intent startService=new Intent(this, Chatting_service.class);
        startService(startService);
    }

    private void stopChattingService(){
        Intent stopService=new Intent(this,Chatting_service.class);
        stopService(stopService);
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
