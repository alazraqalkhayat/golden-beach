package com.alazraq.alkhayat.goldenbeach.ui.contact_us;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Contact_us_fragment extends Fragment {

    TextInputLayout phone_number,email;

       public Contact_us_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
           View v= inflater.inflate(R.layout.contact_us_fragment, container, false);

           initViews(v);

           phone_number.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(getContext(), "phone", Toast.LENGTH_SHORT).show();
               }
           });

           email.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(getContext(),"email", Toast.LENGTH_SHORT).show();
               }
           });



        return v;
    }

    private void initViews(View v){
        phone_number=(TextInputLayout)v.findViewById(R.id.contact_us_phone_number_edit_text);
        email=(TextInputLayout)v.findViewById(R.id.contact_us_email_edit_text);

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
