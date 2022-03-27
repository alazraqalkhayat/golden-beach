package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.content.Context;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Retrofit_on_failure_actions {
    Context context;
    String failure_message;

    public Retrofit_on_failure_actions(Context context, String failure_message) {
        this.context = context;
        this.failure_message = failure_message;
    }

    public Retrofit_on_failure_actions(Context context) {
        this.context = context;
    }

    public void showAction(){
        if(failure_message.equalsIgnoreCase("timeout")){
            startAlertDialogForBadInternetConnection();
        }else{
            //Toast.makeText(context, failure_message, Toast.LENGTH_SHORT).show();
            startAlertDialogForForOtherErrors();

        }

    }

    public void showAction2(String failure_message){
        if(failure_message.equalsIgnoreCase("timeout")){
            startAlertDialogForBadInternetConnection();
        }else{
            //Toast.makeText(context, failure_message, Toast.LENGTH_SHORT).show();
            startAlertDialogForForOtherErrors();
        }

    }

    private void startAlertDialogForBadInternetConnection(){
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.network_icon)
                .setContentText(context.getResources().getString(R.string.bad_internet_connection))
                .hideConfirmButton()
                .setCancelButton(context.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void startAlertDialogForForOtherErrors(){
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.server_connection_icon)
                .setContentText(context.getResources().getString(R.string.other_errors))
                .hideConfirmButton()
                .setCancelButton(context.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

}
