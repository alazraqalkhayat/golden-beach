package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Login_activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login_alert_dialog {

    Context context;

    public Login_alert_dialog(Context context) {
        this.context = context;
    }


    public void startAlertDialogForLogin(){
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.login_icon)
                .setContentText(context.getResources().getString(R.string.login_alert_dialog_message))
                .setConfirmText(context.getResources().getString(R.string.login_alert_dialog_ok_button))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        context.startActivity(new Intent(context, Login_activity.class));
                    }
                })
                .setCancelButton(context.getResources().getString(R.string.login_alert_dialog_cancel_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }



}
