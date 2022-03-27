package com.alazraq.alkhayat.goldenbeach.adds_management_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class New_add_fragment extends Fragment {


    ImageView add_image;
    EditText name_of_owner,description;
    Button upload_add_button;

    Bitmap bitmap;

    My_spot_progress_dialog new_add_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;


    int REQUEST_CODE;
    boolean imageIsNotNull;

    String name_of_image;
    String date;


    public New_add_fragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        REQUEST_CODE=2;
        imageIsNotNull=false;

        new_add_dialog =new My_spot_progress_dialog(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.new_add_fragment, container, false);

        initViews(v);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenGallery();
            }
        });

        upload_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name_of_owner.getText().toString().equalsIgnoreCase("")
                        ||description.getText().toString().equalsIgnoreCase("")){
                    Toasty.custom(getContext(),getResources().getString(R.string.check_input_all_date),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();

                }else{
                    if(imageIsNotNull==false){
                        Toasty.custom(getContext(),getResources().getString(R.string.add_the_image),R.drawable.info_icon_golden,R.color.light_golden,Toasty.LENGTH_LONG,true,true).show();
                    }else{
                         new_add_dialog.start_dialog_for_uploading_the_add();
                         date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                         name_of_image=startConvertBitmapToString();
                         startRetrofitToAddNewAdd(name_of_owner.getText().toString().toUpperCase(),name_of_image,description.getText().toString().toUpperCase(),date);


                    }
                }
            }
        });


        return v;
    }


    private void initViews(View v){
        add_image=(ImageView)v.findViewById(R.id.new_add_fragment_add_image_view);
        name_of_owner=(EditText)v.findViewById(R.id.new_add_fragment_name_of_owner_edit_text);
        description=(EditText)v.findViewById(R.id.new_add_fragment_description_edit_text);
        upload_add_button =(Button)v.findViewById(R.id.new_add_fragment_add_button);

    }


    private void startOpenGallery(){

        Intent intent=new Intent().setType("image/*")
              .setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==getActivity().RESULT_OK && data!=null){
            Uri path=data.getData();

            //name_of_produce.setText(String.valueOf(path));
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                add_image.setImageBitmap(bitmap);

                imageIsNotNull=true;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private String startConvertBitmapToString(){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte [] byteArray=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray,Base64.DEFAULT);





    }


    private void startRetrofitToAddNewAdd(String name_of_owner,String name_of_image_of_add,
                                          String description,String date_of_add){

        Retrofit_connection.getRetrofit_connection_instance().addNewAdd(name_of_owner,name_of_image_of_add,description,date_of_add).enqueue(new Callback<Response_messages>() {
            @Override
            public void onResponse(Call<Response_messages> call, Response<Response_messages> response) {
                if(response.isSuccessful()){


                   if(response.body().getResponse_message().equalsIgnoreCase("AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!")){
                        Toasty.custom(getContext(),getResources().getString(R.string.an_error_occurred),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else if(response.body().getResponse_message().equalsIgnoreCase("Required fields is missing")){
                        Toasty.custom(getContext(),getResources().getString(R.string.required_fields_is_missing),R.drawable.error_icon,R.color.color_error,Toasty.LENGTH_LONG,true,true).show();

                    }else{
                       Toasty.success(getContext(),getResources().getString(R.string.uploading_successful),Toast.LENGTH_SHORT,true).show();
                       startActivity(new Intent(getContext(), MainActivity.class));
                       getActivity().finish();
                    }


                    new_add_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response_messages> call, Throwable t) {
                try {
                    new_add_dialog.dismiss();
                    retrofit_on_failure_actions=new Retrofit_on_failure_actions(getContext(),t.getMessage());

                    if(t.getMessage().equalsIgnoreCase("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $")){
                        Toasty.success(getContext(),getResources().getString(R.string.uploading_successful),Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();

                    }else{
                        retrofit_on_failure_actions.showAction();
                    }


                }catch (Exception e){
                    new_add_dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();

                }


            }
        });

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
