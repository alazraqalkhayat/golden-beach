package com.alazraq.alkhayat.goldenbeach.ui.about_app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;

public class About_app_fragment extends Fragment {

    TextView evaluation_app,share_app;
    public About_app_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //register internet broadcast receiver
        Network_broadcast_receiver_register register =new Network_broadcast_receiver_register(getContext());
        register.registerNetworkConnection();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.about_app_fragment, container, false);

        initViews(v);

        evaluation_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenAppStore();
            }
        });

        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startShareApp();
            }
        });


        return v;
    }


    private void initViews(View v){
        evaluation_app=(TextView)v.findViewById(R.id.about_app_fragment_evaluation_app_text_view);
        share_app=(TextView)v.findViewById(R.id.about_app_fragment_share_app_text_view);

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void startOpenAppStore(){
        try {
            Intent open_play_store=new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+ getContext().getPackageName()));
            startActivity(open_play_store);

        }catch (ActivityNotFoundException e){
            Intent open_play_store_with_error=new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store.apps/details?id="+getContext().getPackageName()));

            //Intent open_play_store_with_error=new Intent(Intent.ACTION_VIEW,Uri.parse("http://goldenbeachye.com/download%20web%20site/download_the_app.html"));

            startActivity(open_play_store_with_error);
        }

    }

    private void startShareApp(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Your body here";
        String shareSub = "Your subject here";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Shared_Helper.getkey(getContext(),"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }

}
