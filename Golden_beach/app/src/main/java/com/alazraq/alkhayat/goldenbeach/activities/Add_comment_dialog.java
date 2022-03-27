package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;
import com.alazraq.alkhayat.goldenbeach.services.Chatting_service;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_comment_dialog extends AppCompatActivity {

    EditText comment_edit_text;
    Button comment_button,cancel_button;
    Network_connection_broadcaster_receiver connection;
    Login_alert_dialog login_alert_dialog;
    My_spot_progress_dialog add_new_comment_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;

    String id_of_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_dialog);

        initViews();

        initObjects();

        //get id of all from about all activity
        Bundle get_id_of_all_bundle=getIntent().getExtras();
        id_of_all=get_id_of_all_bundle.getString("id");

        comment_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(Add_comment_dialog.this, id_of_all, Toast.LENGTH_SHORT).show();

                if(comment_edit_text.getText().toString().equalsIgnoreCase("")){
                    Toasty.custom(Add_comment_dialog.this,getResources().getString(R.string.input_comment),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                }else{
                    if(Shared_Helper.getkey(Add_comment_dialog.this,"user_name").equalsIgnoreCase("")){
                        login_alert_dialog.startAlertDialogForLogin();
                    }else{
                        add_new_comment_dialog.start_dialog_for_adding_new_comment();
                        startRetrofitToAddNewComment(Integer.valueOf(Shared_Helper.getkey(Add_comment_dialog.this,"user_id")),Integer.valueOf(id_of_all),comment_edit_text.getText().toString());
                    }
                }


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

        comment_edit_text=(EditText)findViewById(R.id.add_comment_dialog_comment_edit_text);
        comment_button=(Button)findViewById(R.id.add_comment_dialog_comment_button);
        cancel_button=(Button)findViewById(R.id.add_comment_dialog_cancel_button);


    }

    private void initObjects(){
        add_new_comment_dialog=new My_spot_progress_dialog(this);
        login_alert_dialog=new Login_alert_dialog(this);

    }

    private void startRetrofitToAddNewComment(int id_of_user, int id_of_all, final String _comment) {


        Retrofit_connection.getRetrofit_connection_instance().insertComment(id_of_user,id_of_all,_comment).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(About_all_activity.this, response.body().getResponse_message(), Toast.LENGTH_SHORT).show();
                    if(response.body().getResponse_message().equalsIgnoreCase("ADDED COMMENT SUCCESSFULLY")){
                        Toasty.success(Add_comment_dialog.this,getResources().getString(R.string.added_comment_successfully), Toast.LENGTH_SHORT,true).show();
                        comment_edit_text.setText("");
                        finishActivity();


                    }else if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Add_comment_dialog.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                        Add_comment_dialog.this.finish();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Add_comment_dialog.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                        Add_comment_dialog.this.finish();
                    }



                    add_new_comment_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(Add_comment_dialog.this,t.getMessage());
                try {
                    add_new_comment_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    add_new_comment_dialog.dismiss();
                    Toast.makeText(Add_comment_dialog.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void finishActivity(){
        Intent ii=new Intent();
        setResult(Activity.RESULT_OK,ii);
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
    public void finish() {
        super.finish();
        CustomIntent.customType(Add_comment_dialog.this,"bottom-to-up");

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
