package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.AlertDialog;
import android.content.Context;

import com.alazraq.alkhayat.goldenbeach.R;

import dmax.dialog.SpotsDialog;

public class My_spot_progress_dialog {

    Context context;
    AlertDialog dialog;
    public My_spot_progress_dialog(Context context) {
        this.context = context;
    }

    public void start_dialog_for_uploading_the_post(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_uploading_the_post);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void start_dialog_for_uploading_the_add(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_uploading_the_add);
        dialog.setCancelable(false);
        dialog.show();

    }


    public void start_dialog_for_deleting(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_deleting);
        dialog.setCancelable(false);
        dialog.show();

    }
    public void start_dialog_for_login(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_login);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void start_dialog_for_sign_up(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_sign_up);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void start_dialog_for_adding_new_comment(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_adding_comment);
        dialog.setCancelable(false);
        dialog.show();

    }


    public void start_dialog_for_adding_new_evaluat(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_adding_evaluating);
        dialog.setCancelable(false);
        dialog.show();

    }



    public void start_dialog_for_send(){
        dialog =new SpotsDialog(context,R.style.my_spot_alert_dialog_sending);
        dialog.setCancelable(false);
        dialog.show();

    }



    public void dismiss(){
        dialog.dismiss();
    }


}
