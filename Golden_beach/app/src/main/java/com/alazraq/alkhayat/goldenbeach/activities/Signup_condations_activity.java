package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import maes.tech.intentanim.CustomIntent;

public class Signup_condations_activity extends AppCompatActivity {

    Button continue_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_condations_activity);

        if(Shared_Helper.getkey(this,"conditions_shown").equalsIgnoreCase("yes")){

            startActivity(new Intent(Signup_condations_activity.this, Signup_activity.class));
            CustomIntent.customType(Signup_condations_activity.this,"left-to-right");
            Signup_condations_activity.this.finish();
        }


        continue_button=(Button)findViewById(R.id.sign_up_conditions_activity_continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared_Helper.putKey(Signup_condations_activity.this,"conditions_shown","yes");
                startActivity(new Intent(Signup_condations_activity.this, Signup_activity.class));
                Signup_condations_activity.this.finish();
                CustomIntent.customType(Signup_condations_activity.this,"left-to-right");

            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Signup_condations_activity.this,"bottom-to-up");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();


        if (Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }


}
