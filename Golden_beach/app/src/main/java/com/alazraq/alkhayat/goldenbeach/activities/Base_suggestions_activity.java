package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.Base_suggestions_adapter;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.EndlessRecyclerViewScrollListener;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_suggestions;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Base_suggestions_activity extends AppCompatActivity {
    Base_suggestions_adapter suggestions_adapter;
    ArrayList<Items_of_base_suggestions> items_of_base_suggestions;
    RecyclerView suggestions_recyle_view;

    CardView suggestions_card_view;
    EditText suggestions_edit_text;
    Button suggestions_send_button;
    Toolbar suggestions_tool_bar;

    ProgressBar load_more_progress_bar;
    ImageView reload_image_view;

    My_spot_progress_dialog insert_suggestion_progrss_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;
    My_Flower_progress_dialog get_all_suggestions_progress_dialog;

    Login_alert_dialog login_alert_dialog;

    Network_connection_broadcaster_receiver connection;

    int LIMIT=0;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_suggestions_activity);

        startInitObjects();

        startInitViews();

        initRecycleView();

        startAddToolBarSettings();


        startCheckTheUserName();

        suggestions_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        suggestions_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(suggestions_edit_text.getText().toString().equalsIgnoreCase("")){
                    Toasty.custom(Base_suggestions_activity.this,getResources().getString(R.string.write_your_suggestion_to_send),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                }else{

                    if(Shared_Helper.getkey(Base_suggestions_activity.this,"user_name").equalsIgnoreCase("")){
                        login_alert_dialog.startAlertDialogForLogin();
                    }else{
                        insert_suggestion_progrss_dialog.start_dialog_for_send();

                        String suggestion_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").format(new Date());
                        startRetrofitToInsertSuggestion(Integer.valueOf(Shared_Helper.getkey(Base_suggestions_activity.this,"user_id")),suggestions_edit_text.getText().toString(),suggestion_date);

                    }
                }

            }
        });



        suggestions_recyle_view.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //get_all_dialog.start_dialog();
                LIMIT +=5;
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);

                startRetrofitForGetAllSuggestions(LIMIT);


            }
        });
        reload_image_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                load_more_progress_bar.setVisibility(View.VISIBLE);
                load_more_progress_bar.setIndeterminate(true);
                reload_image_view.setVisibility(View.GONE);

                startRetrofitForGetAllSuggestions(LIMIT);

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
            CustomIntent.customType(this,"fadein-to-fadeout");

        }

        return super.onOptionsItemSelected(item);
    }



    private void startInitObjects(){
        insert_suggestion_progrss_dialog =new My_spot_progress_dialog(this);
        get_all_suggestions_progress_dialog =new My_Flower_progress_dialog(this);

        login_alert_dialog=new Login_alert_dialog(this);
        items_of_base_suggestions=new ArrayList<>();


    }

    private void startInitViews(){

        suggestions_tool_bar=(Toolbar)findViewById(R.id.suggestions_activity_tool_bar_id);
        suggestions_card_view=(CardView)findViewById(R.id.suggestions_card_view);
        suggestions_edit_text=(EditText)findViewById(R.id.suggestions_edit_text);
        suggestions_send_button=(Button)findViewById(R.id.suggestions_button);

        load_more_progress_bar=(ProgressBar)findViewById(R.id.base_suggestions_activity_load_more_progress_bar);
        reload_image_view=(ImageView)findViewById(R.id.base_suggestions_activity_reload_image_view);


    }

    private void initRecycleView(){
        suggestions_recyle_view =(RecyclerView)findViewById(R.id.suggestions_recycle_view);
        manager=new LinearLayoutManager(this);
        suggestions_recyle_view.setLayoutManager(manager);
        suggestions_adapter=new Base_suggestions_adapter(Base_suggestions_activity.this, items_of_base_suggestions);
        suggestions_recyle_view.setAdapter(suggestions_adapter);

    }

    private void startAddToolBarSettings(){
        setSupportActionBar(suggestions_tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.suggestions_title));
    }

    private void startCheckTheUserName(){
        if(!Shared_Helper.getkey(this,"user_name").equalsIgnoreCase("")){

            if(!Shared_Helper.getkey(this,"user_name").equalsIgnoreCase("admin")){
                suggestions_recyle_view.setVisibility(View.GONE);

            }else{
                suggestions_card_view.setVisibility(View.GONE);
                get_all_suggestions_progress_dialog.start_dialog();
                startRetrofitForGetAllSuggestions(LIMIT);
            }
        }
    }

    private void startRetrofitToInsertSuggestion(int id_of_user,String suggestion,String suggestion_date){

        Retrofit_connection.getRetrofit_connection_instance().insertSuggestion(id_of_user,suggestion,suggestion_date).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse_message().equalsIgnoreCase("ADDED SUGGESTION SUCCESSFULLY")){
                        Toasty.success(Base_suggestions_activity.this,getResources().getString(R.string.sending_successfully), Toast.LENGTH_SHORT,true).show();
                        suggestions_edit_text.setText("");
                        Base_suggestions_activity.this.finish();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(Base_suggestions_activity.this,getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.errorColor,Toasty.LENGTH_LONG,true,true).show();
                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(Base_suggestions_activity.this,getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.errorColor,Toasty.LENGTH_LONG,true,true).show();

                    }
                    insert_suggestion_progrss_dialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(Base_suggestions_activity.this,t.getMessage());
                try {
                    insert_suggestion_progrss_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    insert_suggestion_progrss_dialog.dismiss();
                    Toast.makeText(Base_suggestions_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startRetrofitForGetAllSuggestions(int limit){
        Retrofit_connection.getRetrofit_connection_instance().getAllSuggestions(limit).enqueue(new Callback<List<Items_of_base_suggestions>>() {
            @Override
            public void onResponse(Call<List<Items_of_base_suggestions>> call, Response<List<Items_of_base_suggestions>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()<1){
                        Toasty.custom(Base_suggestions_activity.this,getString(R.string.no_more_suggestions),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                        call.cancel();

                    }
                    items_of_base_suggestions.addAll(response.body());
                    suggestions_adapter.notifyItemRangeChanged(suggestions_adapter.getItemCount(),items_of_base_suggestions.size()-1);
                    if(get_all_suggestions_progress_dialog.isShoen()){
                        get_all_suggestions_progress_dialog.dismiss();
                    }

                    load_more_progress_bar.setIndeterminate(false);
                    load_more_progress_bar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<Items_of_base_suggestions>> call, Throwable t) {

                load_more_progress_bar.setIndeterminate(false);
                load_more_progress_bar.setVisibility(View.GONE);
                //LIMIT-=2;
                reload_image_view.setVisibility(View.VISIBLE);

                retrofit_on_failure_actions =new Retrofit_on_failure_actions(Base_suggestions_activity.this,t.getMessage());
                try {
                    get_all_suggestions_progress_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_all_suggestions_progress_dialog.dismiss();
                    Toast.makeText(Base_suggestions_activity.this, "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Base_suggestions_activity.this,"right-to-left");

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

        //stopChattingService();

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
        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
