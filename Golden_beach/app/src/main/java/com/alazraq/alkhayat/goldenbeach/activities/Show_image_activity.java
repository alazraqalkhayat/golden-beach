package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.Testing_activity;
import com.squareup.picasso.Cache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import maes.tech.intentanim.CustomIntent;

public class Show_image_activity extends AppCompatActivity {
    ImageView imageView;
    String from,name_of_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_activity);
        imageView=(ImageView)findViewById(R.id.show_image_activity_image_view) ;

        Bundle b=getIntent().getExtras();
        from = b.getString("from");

       try {
           if(from.equalsIgnoreCase("about_all_activity")){
               name_of_image=b.getString("name_of_image");
               //Picasso.get().load("http://goldenbetch.aba.vg/"+name_of_image).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageView);
               Picasso.get().load("http://goldenbeachye.com/"+name_of_image).fit().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageView);


           }else if(from.equalsIgnoreCase("base_chatting_activity")){
               String user_name=b.getString("user_name");
               //Picasso.get().load("http://goldenbetch.aba.vg/users_images/"+user_name+".jpg").fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageView);
               Picasso.get().load("http://goldenbeachye.com/users_images/"+user_name+".jpg").fit().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageView);

           }



       }catch (Exception e){}






    }




    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Show_image_activity.this,"fadein-to-fadeout");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
