package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class Check_administrator_dialog extends AppCompatActivity {

    EditText password_edit_text;
    CheckBox checkBox;
    Button check_button,cancel_button;

    DatabaseReference databaseReference;
    Network_connection_broadcaster_receiver connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_administrator_dialog);


        initViews();

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFireBaseForCheckingThePassword(password_edit_text.getText().toString());
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initViews(){
        password_edit_text=(EditText)findViewById(R.id.check_administrator_activity_password_edit_text);
        check_button=(Button)findViewById(R.id.check_administrator_activity_check_button);
        cancel_button=(Button)findViewById(R.id.check_administrator_activity_cancel_button);

    }

    private void startFireBaseForCheckingThePassword(final String password){
        databaseReference= FirebaseDatabase.getInstance().getReference().child("security_password");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String base_password=(String)snapshot.child("password").getValue();
                if(password.equals(base_password)){
                    Shared_Helper.putKey(Check_administrator_dialog.this,"administrator","true");
                    password_edit_text.setText("");
                    finish();
                }else{
                    Toasty.custom(Check_administrator_dialog.this,getResources().getString(R.string.login_activity_wrong_user_name_or_password),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Check_administrator_dialog.this,"bottom-to-up");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

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
