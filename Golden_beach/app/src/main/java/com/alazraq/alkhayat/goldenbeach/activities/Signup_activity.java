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
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup_activity extends AppCompatActivity {

    TextInputEditText full_name,email,user_name,password,confirm_password;
    TextView login;
    Button sign_up;
    CircleImageView user_image,chose_image;

    String name_of_image;

    int REQUEST_CODE=1;

    Bitmap bitmap;

    boolean imageIsNotNull;

    My_spot_progress_dialog sign_up_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebase_dataBase_reference;

    Map<String,Object> create_new_chat_room_hash_map;

    String chat_room_name;
    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup_activity);

        initViews();

        imageIsNotNull=false;
        REQUEST_CODE=10;
        sign_up_dialog=new My_spot_progress_dialog(this);

        firebase_dataBase_reference= FirebaseDatabase.getInstance().getReference().child("main_chat_rooms");
        create_new_chat_room_hash_map =new HashMap<>();

        chose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenGallery();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheckTheInputData();

            }
        });



    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            startActivity(new Intent(Signup_activity.this,Login_activity.class));
            CustomIntent.customType(Signup_activity.this,"right-to-left");

        }
    });



    }


    private void initViews(){
        user_image=(CircleImageView)findViewById(R.id.sign_up_activity_user_image);
        chose_image=(CircleImageView)findViewById(R.id.sign_up_activity_chose_image);

        full_name=(TextInputEditText)findViewById(R.id.sign_up_activity_full_name_edit_text);
        email=(TextInputEditText)findViewById(R.id.sign_up_activity_email_edit_text);
        user_name=(TextInputEditText)findViewById(R.id.sign_up_activity_user_name_edit_text);
        password=(TextInputEditText)findViewById(R.id.sign_up_activity_password_edit_text);
        confirm_password=(TextInputEditText)findViewById(R.id.sign_up_activity_confirm_password_edit_text);

        sign_up=(Button)findViewById(R.id.sign_up_activity_sign_up_button);

        login=(TextView)findViewById(R.id.sign_up_activity_login_text_view);

    }

    private void startCheckTheInputData(){
        if(full_name.getText().toString().equalsIgnoreCase("")
                ||email.getText().toString().equalsIgnoreCase("")
                ||user_name.getText().toString().equalsIgnoreCase("")
                ||password.getText().toString().equalsIgnoreCase("")
                ||confirm_password.getText().toString().equalsIgnoreCase("")){

            Toasty.custom(Signup_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();

        }else{

            if(user_name.getText().toString().length()<8){
                Toasty.custom(Signup_activity.this,getResources().getString(R.string.sign_up_activity_user_name_eight_car),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

            }else{

                if(password.getText().length()<8){
                    Toasty.custom(Signup_activity.this,getResources().getString(R.string.sign_up_activity_password_eight_car),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                }else{

                    if(!password.getText().toString().equalsIgnoreCase(confirm_password.getText().toString())){
                        Toasty.custom(Signup_activity.this,getResources().getString(R.string.sign_up_activity_password_are_not_same),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                    }else{
                        if(imageIsNotNull==false){
                            Toasty.custom(Signup_activity.this,getResources().getString(R.string.add_the_image),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                        }else{
                                name_of_image=startConvertBitmapToString();
                                sign_up_dialog.start_dialog_for_sign_up();
                                startRetrofitToSignUp(full_name.getText().toString(),email.getText().toString(),
                                                        user_name.getText().toString(),password.getText().toString(),
                                                           name_of_image);

                        }
                    }
                }
            }

        }
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
                user_image.setImageBitmap(bitmap);

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

    private void  startRetrofitToSignUp(String user_full_name, String user_email,
                                        final String user_name, String user_password,
                                        String user_name_of_image){

        Retrofit_connection.getRetrofit_connection_instance().userSignUp(user_full_name,user_email,user_name,user_password,user_name_of_image).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){

                    //Toasty.success(Signup_activity.this,getResources().getString(R.string.registration_successful),Toast.LENGTH_SHORT,true).show();
                    //startRemoveAllData();
                    //startActivity(new Intent(Signup_activity.this,Login_activity.class));

                    if(response.body().getResponse_message().equalsIgnoreCase("Email Already Exists")){
                        Toasty.custom(Signup_activity.this,getResources().getString(R.string.email_already_exist),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                    }else if(response.body().getResponse_message().equalsIgnoreCase("Name Already Exists")){
                        Toasty.custom(Signup_activity.this,getResources().getString(R.string.name_already_exist),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Signup_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                    }else if(response.body().getResponse_message().equalsIgnoreCase("Registration successful")){
                        Toasty.success(Signup_activity.this,getResources().getString(R.string.registration_successful),Toast.LENGTH_SHORT,true).show();
                        startRemoveAllData();

                        //createNewChatRoom(user_name);


                        startActivity(new Intent(Signup_activity.this,Login_activity.class));
                        CustomIntent.customType(Signup_activity.this,"right-to-left");
                        Signup_activity.this.finish();
                    }


                    sign_up_dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {

                Toast.makeText(Signup_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                sign_up_dialog.dismiss();
                try {
                    sign_up_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(Signup_activity.this,t.getMessage());

                    if(t.getMessage().equalsIgnoreCase("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $")){
                        Toasty.success(Signup_activity.this,getResources().getString(R.string.uploading_successful),Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(Signup_activity.this,Login_activity.class));
                        CustomIntent.customType(Signup_activity.this,"right-to-left");
                        Signup_activity.this.finish();

                    }else{
                        retrofit_on_failure_actions.showAction();
                    }

                }catch (Exception e){
                    sign_up_dialog.dismiss();
                    Toast.makeText(Signup_activity.this, "catch exception", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void startRemoveAllData(){
        full_name.setText("");
        user_name.setText("");
        email.setText("");
        password.setText("");
        confirm_password.setText("");
    }

    private void createNewChatRoom(String user_name){

        create_new_chat_room_hash_map.put("chat_room_name",user_name);
        firebase_dataBase_reference.updateChildren(create_new_chat_room_hash_map);




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
