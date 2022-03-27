package com.alazraq.alkhayat.goldenbeach.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.alazraq.alkhayat.goldenbeach.Load_more_interface;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.activities.All_list_activity;
import com.alazraq.alkhayat.goldenbeach.adapters.All_recycle_view_list_adapter;
import com.alazraq.alkhayat.goldenbeach.adapters.Commentes_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.EndlessRecyclerViewScrollListener;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_comments;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Testing extends AppCompatActivity {


    String uri;

    Button button;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        button=(Button)findViewById(R.id.butest);
        videoView=(VideoView) findViewById(R.id.test_vedio);

        //VideoView videoView = (FastVideoView)findViewById(R.id.video);
        videoView.setMediaController(new MediaController(this));


        //categories_array=getResources().getStringArray(R.array.categories_series_and_movies_array);


        /*
        this will make the spinner like radio button
        spinner_array_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        this will make the spinner like check box
        spinner_array_adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

        */




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                uri="http://goldenbetch.aba.vg/all_images/ASD2.jpg";
//                download2(uri);
                //http://goldenbeachye.com/test/VID_24320627_132818_953.mp4

                videoView.setVisibility(View.VISIBLE);
                String video_url="http://goldenbeachye.com/test/VID_24320627_132818_953.mp4" ;

                videoView.setVideoPath(video_url);

                videoView.start();



                //Picasso.get().load("http://goldenbeachye.com/test/VID_24320627_132818_953.mp4").fit().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into((VideoView)videoView);



            }
        });
    }

    public void downloadFile(String uRl) {

        File file=new File(Environment.getExternalStorageDirectory()+"/golden_beach");

        if(!file.exists()){
            file.mkdir();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);



        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/golden_beach", "fileName.jpg");

        mgr.enqueue(request);

    }

    private void download2(String uri){

        Picasso.get().load(uri).into(new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/golden");

                    if (!myDir.exists()) {
                        myDir.mkdirs();
                    }

                    String name = new Date().toString() + ".jpg";
                    myDir = new File(myDir, name);
                    FileOutputStream out = new FileOutputStream(myDir);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    Toast.makeText(Testing.this, "loaded", Toast.LENGTH_SHORT).show();

                    out.flush();
                    out.close();
                } catch(Exception e){
                    // some action
                }

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(Testing.this,"",Toast.LENGTH_LONG).show();
                Toast.makeText(Testing.this,"",Toast.LENGTH_LONG).show();
                Toast.makeText(Testing.this,"",Toast.LENGTH_LONG).show();
                Toast.makeText(Testing.this,"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Toast.makeText(Testing.this, "prepare", Toast.LENGTH_SHORT).show();
            }
        });

    }

}