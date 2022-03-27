package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class YOYO {
    View view;

    public YOYO(View view) {
        this.view = view;
    }

    public void startYOYO(){
        YoYo.with(Techniques.Tada)
                .duration(600)
                .repeat(1)
                .playOn(view);
    }


}
