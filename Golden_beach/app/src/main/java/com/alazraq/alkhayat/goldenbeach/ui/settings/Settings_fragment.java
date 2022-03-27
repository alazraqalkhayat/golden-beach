
package com.alazraq.alkhayat.goldenbeach.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Chang_language_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.github.zagum.switchicon.SwitchIconView;

import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class Settings_fragment extends Fragment {

    SwitchIconView notifications_switch_icon,dark_mode;

    TextView language;

    int REQUEST_CODE;
    private OnFragmentInteractionListener mListener;

    public Settings_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        REQUEST_CODE=1101;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.settings_fragment, container, false);

        initViews(v);

        startCheckTheSwitchesStatus();

        notifications_switch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notifications_switch_icon.isIconEnabled()){
                    notifications_switch_icon.setIconEnabled(false,true);
                    Shared_Helper.putKey(getContext(),"notifications","off");
                }else{
                    notifications_switch_icon.setIconEnabled(true,true);
                    Shared_Helper.putKey(getContext(),"notifications","on");
                }
            }
        });

        dark_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dark_mode.isIconEnabled()){
                    dark_mode.setIconEnabled(false,true);
                    Shared_Helper.putKey(getContext(),"dark_mode","off");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));


                }else{
                    dark_mode.setIconEnabled(true,true);
                    Shared_Helper.putKey(getContext(),"dark_mode","on");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));


                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), Chang_language_dialog.class),REQUEST_CODE);
                CustomIntent.customType(getContext(),"up-to-bottom");

            }
        });

        return v;
    }

    private void initViews(View v){
        notifications_switch_icon=(SwitchIconView)v.findViewById(R.id.settings_fragment_notifications_switch);
        dark_mode=(SwitchIconView)v.findViewById(R.id.settings_fragment_dark_mode_switch);

        language=(TextView)v.findViewById(R.id.settings_fragment_language_text_view);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void startCheckTheSwitchesStatus(){
        if (Shared_Helper.getkey(getContext(),"notifications").equalsIgnoreCase("on")){
            notifications_switch_icon.setIconEnabled(true,true);

        }else{
            notifications_switch_icon.setIconEnabled(false,true);
        }


        if (Shared_Helper.getkey(getContext(),"dark_mode").equalsIgnoreCase("on")){
            dark_mode.setIconEnabled(true,true);

        }else{
            dark_mode.setIconEnabled(false,true);
        }
    }



}
