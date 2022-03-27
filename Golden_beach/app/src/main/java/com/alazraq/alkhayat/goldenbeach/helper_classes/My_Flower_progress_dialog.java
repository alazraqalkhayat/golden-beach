package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alazraq.alkhayat.goldenbeach.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class My_Flower_progress_dialog {

     Context context;
     ACProgressFlower dialog;


    public My_Flower_progress_dialog(Context context) {
        this.context = context;
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .text(context.getResources().getString(R.string.please_wait))
                .fadeColor(context.getResources().getColor(R.color.light_golden))
                .build();

    }

    public void start_dialog(){

                dialog.show();
    }

    public void dismiss(){
            dialog.dismiss();
    }

    public boolean isShoen(){
        if(dialog.isShowing()){
            return true;
        }else
            return false;
    }


}
