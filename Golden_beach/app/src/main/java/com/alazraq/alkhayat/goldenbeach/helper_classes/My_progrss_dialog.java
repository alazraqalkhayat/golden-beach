package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.alazraq.alkhayat.goldenbeach.R;

public class My_progrss_dialog {

    String progress_title,progress_message;
    Context context;

    ProgressDialog progressDialog;


    public My_progrss_dialog(Context context, String progress_title, String progress_message) {

        this.context = context;
        this.progress_title = progress_title;
        this.progress_message = progress_message;
    }

    public void startProgressDialog(){

        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle(progress_title);
        progressDialog.setProgressStyle(R.style.no_action_bar);
        progressDialog.setMessage(progress_message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismisProgressDialog(){
        progressDialog.dismiss();
    }


}
