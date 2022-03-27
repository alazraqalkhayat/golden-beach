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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Evaluation_dialog extends AppCompatActivity {

    TextView header_text_view;
    RatingBar ratingBar;
    Button evaluate,cancel;


    Bundle get_name_of_all_and_id_of_all_from_about_all_activity_bundle;
    String name_of_all,id_of_all;
    int get_id_of_user_from_shared_preference;

    My_spot_progress_dialog dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;

    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_dialog);



        startIniViews();

        startInitObjects();

        startPutHeaderTitle();

        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ratingBar.getRating()==0.0f){
                    Toasty.custom(Evaluation_dialog.this,getResources().getString(R.string.you_have_to_input_evaluation_value),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                }else{
                    dialog.start_dialog_for_adding_new_evaluat();
                    startRetrofitToEvaluationAll(get_id_of_user_from_shared_preference,Integer.valueOf(id_of_all),ratingBar.getRating());
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void startIniViews(){
        header_text_view=(TextView)findViewById(R.id.evaluation_dialog_title_text_view);
        ratingBar=(RatingBar)findViewById(R.id.evaluation_dialog_rating_bar);
        evaluate=(Button)findViewById(R.id.evaluation_dialog_evaluate_button);
        cancel=(Button)findViewById(R.id.evaluation_dialog_cancle_button);

    }

    private void startInitObjects(){
        get_id_of_user_from_shared_preference=Integer.valueOf(Shared_Helper.getkey(this,"user_id"));
        get_name_of_all_and_id_of_all_from_about_all_activity_bundle =getIntent().getExtras();
        dialog=new My_spot_progress_dialog(this);
    }

    private void startPutHeaderTitle(){
        name_of_all= get_name_of_all_and_id_of_all_from_about_all_activity_bundle.getString("name_of_all");
        id_of_all= get_name_of_all_and_id_of_all_from_about_all_activity_bundle.getString("id_of_all");

        header_text_view.setText(getResources().getString(R.string.about_all_activity_evaluate_button)+" "+name_of_all);

    }

    private void startRetrofitToEvaluationAll(int id_of_user,int id_of_all,float value_of_evaluation) {
        Retrofit_connection.getRetrofit_connection_instance().insertAndUpdateEvaluation(id_of_user,id_of_all,value_of_evaluation).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if (response.isSuccessful()){

                    if(response.body().getResponse_message().equalsIgnoreCase("The evaluation process was successful")){
                        Toasty.success(Evaluation_dialog.this,getResources().getString(R.string.input_evaluation_insert_successful_message),Toast.LENGTH_LONG,true).show();
                        finishActivity();
                    }else if(response.body().getResponse_message().equalsIgnoreCase("your evaluation has been changed successfully")){
                        Toasty.success(Evaluation_dialog.this,getResources().getString(R.string.input_evaluation_update_successful_message),Toast.LENGTH_LONG,true).show();
                        Evaluation_dialog.this.finish();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Evaluation_dialog.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                        Toasty.custom(Evaluation_dialog.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(Evaluation_dialog.this,t.getMessage());
                try {
                    dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    dialog.dismiss();
                    Toast.makeText(Evaluation_dialog.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void finishActivity(){
        Intent ii=new Intent();
        setResult(Activity.RESULT_OK,ii);
        finish();
    }



    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Evaluation_dialog.this,"bottom-to-up");

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
