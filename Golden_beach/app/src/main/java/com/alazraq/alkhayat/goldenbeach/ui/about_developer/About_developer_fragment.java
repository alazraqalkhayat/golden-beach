package com.alazraq.alkhayat.goldenbeach.ui.about_developer;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;

public class About_developer_fragment extends Fragment {


    public About_developer_fragment() {
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
        // Inflate the layout-v21 for this fragment
        return inflater.inflate(R.layout.about_developer_fragment, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
