package com.alazraq.alkhayat.goldenbeach.ui.suggestions_and_complainants;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Admin_control_panel_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Base_complainants_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Base_suggestions_activity;
import com.alazraq.alkhayat.goldenbeach.adapters.Viewpageradapter;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.google.android.material.tabs.TabLayout;

import maes.tech.intentanim.CustomIntent;

public class Suggestions_and_complainants_fragment extends Fragment {

    int REQUESTCODE;
    static int  REFRESH_CODE;

    TextView suggestions,complainants;





    //Suggestions_and_complainants_fragment context=Suggestions_and_complainants_fragment.this;
    //getFragmentManager().beginTransaction().detach(context).attach(context).commit();



    public Suggestions_and_complainants_fragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.suggestions_fragment, container, false);

        initViews(v);
        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Base_suggestions_activity.class));
                CustomIntent.customType(getContext(),"left-to-right");

            }
        });

        complainants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Base_complainants_activity.class));
                CustomIntent.customType(getContext(),"left-to-right");

            }
        });

        return v;
    }





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Toast.makeText(getContext(), String.valueOf(REFRESH_CODE), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Toast.makeText(getContext(), "detach", Toast.LENGTH_SHORT).show();
    }

    private void initViews(View v){
        suggestions=(TextView)v.findViewById(R.id.suggestions_text_view);
        complainants=(TextView)v.findViewById(R.id.complainants_text_view);

    }
    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), String.valueOf(REFRESH_CODE), Toast.LENGTH_SHORT).show();

        if(REFRESH_CODE !=0){
            REFRESH_CODE=0;
            //suggestionsTabLayout.refreshDrawableState();
            //viewpageradapter.notifyDataSetChanged();

            // Suggestions_and_complainants_fragment context=Suggestions_and_complainants_fragment.this;
            //getFragmentManager().beginTransaction().detach(context).attach(context).commit();

            //getActivity().recreate();


        }
    }


    @Override
    public void onPause() {
        super.onPause();
        REFRESH_CODE =2;

        //Toast.makeText(getContext(), String.valueOf(REFRESH_CODE), Toast.LENGTH_SHORT).show();

        //getFragmentManager().beginTransaction().detach(base_suggestions_fragment).attach(base_suggestions_fragment).commit();
        //getFragmentManager().beginTransaction().detach(base_complainants_activity).attach(base_complainants_activity).commit();


    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onFragmentInteraction(Uri uri);

    }


}
