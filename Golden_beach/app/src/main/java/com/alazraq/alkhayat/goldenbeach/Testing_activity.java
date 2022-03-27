package com.alazraq.alkhayat.goldenbeach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Splash_activity;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Modified_items;
import com.alazraq.alkhayat.goldenbeach.services.Modifies_service;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import maes.tech.intentanim.CustomIntent;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.DiskLruCache;

public class Testing_activity extends AppCompatActivity {
    ConnectivityManager connectivityManager;
    SweetAlertDialog ss;

//http://goldenbetch.aba.vg/

    /*


               new Picasso.Builder(this).memoryCache(new Cache() {
               @Override
               public Bitmap get(String key) {

                   return null;
               }

               @Override
               public void set(String key, Bitmap bitmap) {

               }

               @Override
               public int size() {
                   return 0;
               }

               @Override
               public int maxSize() {
                   return 0;
               }

               @Override
               public void clear() {

               }

               @Override
               public void clearKeyUri(String keyPrefix) {

               }
           });

    ldpi should be 36 x 36

mdpi should be 48 x 48

hdpi should be 72 x 72

xhdpi should be 96 x 96

xxhdpi should be 144 x 144


*left-to-right
*right-to-left
*bottom-to-up
*up-to-bottom
*fadein-to-fadeout
*rotateout-to-rotatein




                    for (int i=0;i<response.body().size();i++){

                        int id=response.body().get(i).id;
                        int session=response.body().get(i).session;
                        int year=response.body().get(i).year;

                        String name=response.body().get(i).name;
                        String name_of_image=response.body().get(i).name_of_image;
                        String name_of_section=response.body().get(i).name_of_section;
                        String brand_of_section=response.body().get(i).brand_of_section;
                        String name_of_category=response.body().get(i).name_of_category;
                        String story=response.body().get(i).story;
                        String trailer=response.body().get(i).trailer;

                        items_of_all_lists.add(new Items_of_all_list(name_of_section,brand_of_section,name_of_category,name,name_of_image,story,trailer,id,session,year));

                    }

                    recycle_view_list_adapter.notifyDataSetChanged();




        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputY",200);
        intent.putExtra("outputY",200);
        intent.putExtra("return_data",true);

     */

    ImageView imageView;
    EditText s;

    String test_activity;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_activity);






                //Toast.makeText(Testing_activity.this, "moaid khaled", Toast.LENGTH_SHORT).show();



        /*
        new Picasso.Builder(this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
            }
        });
        new Picasso.Builder(this).downloader(new Downloader() {
            @NonNull
            @Override
            public Response load(@NonNull Request request) throws IOException {

                return null;
            }

            @Override
            public void shutdown() {

            }
        });

        //Bundle b=getIntent().getExtras();
     //test_activity=b.getString("registerNetworkConnection");


     //startCheckTheInternetConnection();
     //startActivity(new Intent(this,));

/*
        //Button v=(Button)findViewById(R.id.ed);
        s=(EditText) findViewById(R.id.ed);
        s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length()==0){
                    Toast.makeText(Testing_activity.this, "on chang clear", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==0){
                    Toast.makeText(Testing_activity.this, "cleared", Toast.LENGTH_SHORT).show();
                }

            }
        });
        s.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.isPrintingKey()){
                    Toast.makeText(Testing_activity.this, "moaid", Toast.LENGTH_SHORT).show();
                }
                return true;

            }
        });


        //getSupportActionBar().setTitle("asdfasdf");
        //getActionBar().setTitle("my actionbar");
        //getActionBar().setIcon(getResources().getDrawable(R.drawable-v21.add_icon));

 */
/*
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.loadUrl("https://www.youtube.com/watch?v=7mvFtb9cMFU");

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // do something in landscape
        } else {
            //do in potrait
        }

 */


    }






    private void startCheckTheInternetConnection(){

        //IntentFilter filter=new IntentFilter();
        //filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //registerReceiver(new Network_connection_broadcaster_receiver(),filter);

        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo !=null && networkInfo.isConnected()==true){
            //Toast.makeText(Splash_activity.this, "connected", Toast.LENGTH_SHORT).show();
            Testing_activity.this.finish();

            //startActivity(new Intent(Testing_activity.this, MainActivity.class));

        }else{
            startAlertDialogForNoInternetConnection();
        }




    }


    public void startAlertDialogForNoInternetConnection(){
       ss= new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                ss.setCustomImage(R.drawable.network_icon);
                ss.setContentText(this.getResources().getString(R.string.no_internet_connection));
                ss.hideConfirmButton();
                ss.setCancelable(false);
                ss.setCancelButton(this.getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        //Testing_activity.this.finish();
                        //startActivity(Testing_activity.this,Testing_activity.class);
                        recreate();
                    }
                });
                ss.show();
    }


    public void go(View view) {

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Testing_activity.this,"fadein-to-fadeout");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Testing_activity.this.recreate();
    }

}
