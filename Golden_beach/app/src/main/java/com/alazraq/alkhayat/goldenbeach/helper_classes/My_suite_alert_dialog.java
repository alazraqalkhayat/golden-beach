package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Adds_management_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Edit_adds_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Edit_posts_activity;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_suite_alert_dialog {

    Context context;
    String id,session,name_of_section,brand_of_section,
            name_of_category,name,name_of_image,year,story,trailer,activity;

    String add_id,name_of_owner, description;

    String name_of_section2;

    My_spot_progress_dialog delete_the_post_dialog;

    My_spot_progress_dialog delete_add_dialog;

    My_suite_alert_dialog no_items_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    //base constructor
    public My_suite_alert_dialog(Context context) {
        this.context = context;
    }

    //constructor fo edit adn delete the posts
    public My_suite_alert_dialog(Context context, String id, String session, String name_of_section, String brand_of_section, String name_of_category, String name, String name_of_image, String year, String story, String trailer, String activity) {

        this.context = context;
        this.id = id;
        this.session = session;
        this.name_of_section = name_of_section;
        this.brand_of_section = brand_of_section;
        this.name_of_category = name_of_category;
        this.name = name;
        this.name_of_image = name_of_image;
        this.year = year;
        this.story = story;
        this.trailer = trailer;
        this.activity=activity;
        delete_the_post_dialog=new My_spot_progress_dialog(context);


    }

    //constructor fo edit adn delete the posts

    public My_suite_alert_dialog(Context context,String add_id, String name_of_owner, String description) {
        this.context=context;
        this.add_id = add_id;
        this.name_of_owner = name_of_owner;
        this.description = description;
        delete_add_dialog=new My_spot_progress_dialog(context);
    }


    //no items constructor
    public My_suite_alert_dialog(Context context, String name_of_section2) {
        this.context = context;
        this.name_of_section2 = name_of_section2;
    }


    //ALL POSTS MANAGEMENT METHODS
    public void startManagementThePostAlertDialog(){
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_title))
                .setContentText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_message))
                .setConfirmText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_positive_button))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startGoToEditPostActivity();
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startConfirmDeleteThePostAlertDialog();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void startConfirmDeleteThePostAlertDialog(){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getResources().getString(R.string.are_you_sure))
                .setContentText(context.getResources().getString(R.string.all_list_sure_delete_the_post_alert_dialog_message))
                .setConfirmText(context.getResources().getString(R.string.all_list_sure_delete_the_post_alert_dialog_positive_button))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        delete_the_post_dialog.start_dialog_for_deleting();
                        startRetrofitToDeleteThePost(Integer.valueOf(id));

                        if(activity.equalsIgnoreCase("com.alazraq.alkhayat.goldenbeach.MainActivity")){
                            context.startActivity(new Intent(context, MainActivity.class));
                        }else if (activity.equalsIgnoreCase("com.alazraq.alkhayat.goldenbeach.activities.All_list_activity")){
                            //startRecreateTheAllListActivity();
                            //Toast.makeText(context, "all_list", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }


                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton(context.getResources().getString(R.string.all_list_sure_delete_the_post_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                    }
                })
                .show();
    }

    private void startRetrofitToDeleteThePost(int id){

        Retrofit_connection.getRetrofit_connection_instance().deletePost(id).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(context,context.getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();


                    }else if(response.body().getResponse_message().equalsIgnoreCase("DELETED SUCCESSFULLY")){
                        Toasty.success(context,context.getResources().getString(R.string.deleted_successfully),Toast.LENGTH_SHORT,true).show();
                    }
                    delete_the_post_dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(context,t.getMessage());
                try {
                    delete_the_post_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    delete_the_post_dialog.dismiss();
                    Toast.makeText(context, "catch exception", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                //delete_the_post_dialog.dismiss();
            }
        });

    }

    private void startRecreateTheAllListActivity(){

        /*
 Intent intent=new Intent(context,All_list_activity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name_of_section",name_of_section);
                            bundle.putString("brand_of_section",brand_of_section);
                            intent.putExtras(bundle);
                            context.startActivity(intent);

Intent i=new Intent(context,All_list_activity.class);
                            Bundle b=new Bundle();
                            b.putString("name_of_section",name_of_section);
                            b.putString("brand_of_section",brand_of_section);
                            i.putExtras(b);
                            context.startActivity(i);
         */



    }

    private void startGoToEditPostActivity(){
        Intent intent=new Intent(context, Edit_posts_activity.class);
        Bundle bundle=new Bundle();

        bundle.putString("id",id);
        bundle.putString("name",name);
        bundle.putString("story",story);
        bundle.putString("trailer",trailer);
        bundle.putString("year",year);
        bundle.putString("session",session);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    //ALL ADDS MANAGEMENT METHODS

    public void startManagementTheAddAlertDialog(){
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_title))
                .setContentText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_message))
                .setConfirmText(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_positive_button))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startGoToEditAddsActivity();
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton(context.getResources().getString(R.string.all_list_management_the_post_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startConfirmDeleteTheAddAlertDialog();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void startConfirmDeleteTheAddAlertDialog(){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getResources().getString(R.string.are_you_sure))
                .setContentText(context.getResources().getString(R.string.all_adds_fragment_sure_delete_the_add_alert_dialog_message))
                .setConfirmText(context.getResources().getString(R.string.all_adds_fragment_sure_delete_the_add_alert_dialog_positive_button))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        delete_add_dialog.start_dialog_for_deleting();
                        startRetrofitToDeleteTheAdd(Integer.valueOf(add_id));

                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton(context.getResources().getString(R.string.all_adds_fragment_sure_delete_the_add_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                    }
                })
                .show();
    }

    private void startRetrofitToDeleteTheAdd(int add_id){

        Retrofit_connection.getRetrofit_connection_instance().deleteAdd(add_id).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){

                    if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(context,context.getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("DELETED SUCCESSFULLY")){
                        Toasty.success(context,context.getResources().getString(R.string.deleted_successfully),Toast.LENGTH_SHORT,true).show();
                        context.startActivity(new Intent(context, Adds_management_activity.class));

                    }
                    delete_add_dialog.dismiss();


                }

            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(context,t.getMessage());
                try {
                    delete_add_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    delete_add_dialog.dismiss();
                    Toast.makeText(context, "catch exception", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                //delete_add_dialog.dismiss();
            }
        });

    }

    private void startGoToEditAddsActivity(){
        Intent intent=new Intent(context, Edit_adds_activity.class);
        Bundle bundle=new Bundle();

        bundle.putString("name_of_owner",name_of_owner);
        bundle.putString("description",description);
        bundle.putString("id",add_id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }



    //no items method
    public void showNoItemsDialog(){

            new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    .setContentText(name_of_section2+" "+context.getResources().getString(R.string.are_not_exist))
                    .setConfirmText(context.getResources().getString(R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            //startGoToEditAddsActivity();
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

    }


    //no items method
    public void showNoAddsDialog(){

        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(context.getResources().getString(R.string.there_are_no_adds))
                .setConfirmText(context.getResources().getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //startGoToEditAddsActivity();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }





}

