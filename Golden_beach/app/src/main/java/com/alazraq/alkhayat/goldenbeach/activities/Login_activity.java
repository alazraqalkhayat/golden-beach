package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.User_login_items;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alazraq.alkhayat.goldenbeach.helper_classes.App.LOGIN_CHANNEL_ID;

public class Login_activity extends AppCompatActivity {


    TextInputEditText user_name,password;
    TextView forget,create;
    Button login;
    My_spot_progress_dialog login_progress_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;
    DatabaseReference firebase_dataBase_reference;
    Map<String,Object> chat_first_message_hash_map;

    NotificationManagerCompat notificationManager;

    Intent go_to_base_chatting_activity_intent;
    PendingIntent go_to_base_chatting_activity_pending_intent;

    Network_connection_broadcaster_receiver connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initViews();

        initObjects();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login_activity.this, "forget", Toast.LENGTH_SHORT).show();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_name.getText().toString().equalsIgnoreCase("")
                        ||password.getText().toString().equalsIgnoreCase("")){
                    Toasty.custom(Login_activity.this,getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();

                }else{

                    login_progress_dialog.start_dialog_for_login();
                    startRetrofitForUserLogin(user_name.getText().toString(),password.getText().toString());
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Shared_Helper.getkey(Login_activity.this,"conditions_shown").equalsIgnoreCase("yes")){

                    finish();
                    startActivity(new Intent(Login_activity.this, Signup_activity.class));
                    CustomIntent.customType(Login_activity.this,"left-to-right");

                }else{
                    startActivity(new Intent(Login_activity.this,Signup_condations_activity.class));
                    CustomIntent.customType(Login_activity.this,"up-to-bottom");
                }

            }
        });


    }

    private void initViews() {

        user_name=(TextInputEditText)findViewById(R.id.login_activity_user_name_edit_text);
        password=(TextInputEditText)findViewById(R.id.login_activity_password_edit_text);

        forget=(TextView)findViewById(R.id.login_activity_forget_text_view);
        forget.setVisibility(View.GONE);
        create=(TextView)findViewById(R.id.login_activity_create_text_view);

        login=(Button)findViewById(R.id.login_activity_login_button);

    }

    private void initObjects() {
        login_progress_dialog=new My_spot_progress_dialog(this);
        chat_first_message_hash_map=new HashMap<>();

        notificationManager=NotificationManagerCompat.from(this);

        go_to_base_chatting_activity_intent=new Intent(this,Base_chatting_activity.class);
        go_to_base_chatting_activity_intent.putExtra("user_name",Shared_Helper.getkey(this,"user_name"));
        go_to_base_chatting_activity_pending_intent=PendingIntent.getActivity(this,1,go_to_base_chatting_activity_intent,0);


    }

    private void startRetrofitForUserLogin(final String user_name,final String user_password){

        Retrofit_connection.getRetrofit_connection_instance().getUserLoginItems(user_name).enqueue(new Callback<User_login_items>() {

            @Override
            public void onResponse(final Call<User_login_items> call, Response<User_login_items> response) {

                if(response.isSuccessful()){
                    login_progress_dialog.dismiss();
                    if(response.body().getUser_name().equalsIgnoreCase(user_name)
                            &&response.body().getUser_password().equalsIgnoreCase(user_password)){

                        Toasty.success(Login_activity.this,getResources().getString(R.string.login_activity_login_successful),Toast.LENGTH_SHORT,true).show();

                        putSharedPreferences(response.body().getUser_name_of_image(),response.body().getUser_full_name()
                              ,response.body().getUser_email(),response.body().getUser_name(),String.valueOf(response.body().getUser_id()));

                        startActivity(new Intent(Login_activity.this, MainActivity.class));

                        }else{
                            Toasty.custom(Login_activity.this,getResources().getString(R.string.login_activity_wrong_user_name_or_password),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                        }
                    Login_activity.this.finish();



                }


            }

            @Override
            public void onFailure(Call<User_login_items> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(Login_activity.this,t.getMessage());
                try {
                    login_progress_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    login_progress_dialog.dismiss();
                    Toast.makeText(Login_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
    }



    private void putSharedPreferences(String user_name_of_image,String user_full_name,String user_email,String user_name,String user_id){

        Shared_Helper.putKey(Login_activity.this,"user_name_of_image",user_name_of_image);
        Shared_Helper.putKey(Login_activity.this,"user_full_name",user_full_name);
        Shared_Helper.putKey(Login_activity.this,"user_email",user_email);
        Shared_Helper.putKey(Login_activity.this,"user_name",user_name);
        Shared_Helper.putKey(Login_activity.this,"user_id",user_id);


    }
/*
    private void checkTheFirstMessageInChatRoom(final String name_of_chat_room){

        firebase_dataBase_reference= FirebaseDatabase.getInstance().getReference().child("main_chat_rooms");
        firebase_dataBase_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Long childrenCount= snapshot.getChildrenCount();
               //snapshot.getKey();
                //Toast.makeText(Login_activity.this, String.valueOf(l), Toast.LENGTH_SHORT).show();
                if(childrenCount==0){

                    String message=getResources().getString(R.string.login_activity_notification_message)+" "+name_of_chat_room;
                    Map<String,Object> map=new HashMap<>();
                    String temp_key=firebase_dataBase_reference.push().getKey();
                    firebase_dataBase_reference.updateChildren(map);

                    DatabaseReference reference=firebase_dataBase_reference.child(temp_key);
                    chat_first_message_hash_map.put("sender","admin");
                    chat_first_message_hash_map.put("message",message);
                    reference.updateChildren(chat_first_message_hash_map);

                    if(Shared_Helper.getkey(Login_activity.this,"notifications").equalsIgnoreCase("on")){
                        startLoginNotification(message);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void startLoginNotification(String message){

        Notification notification= new NotificationCompat.Builder(this, LOGIN_CHANNEL_ID)

                .setSmallIcon(R.drawable.email_icon_golden)
                .setContentText(message)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(getResources().getColor(R.color.light_golden))
                .setContentIntent(go_to_base_chatting_activity_pending_intent)
                .build();

        notificationManager.notify(1,notification);


    }



 */

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
