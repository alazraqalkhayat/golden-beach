package com.alazraq.alkhayat.goldenbeach.ui.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Admin_control_panel_activity;
import com.alazraq.alkhayat.goldenbeach.activities.All_list_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Check_administrator_dialog;
import com.alazraq.alkhayat.goldenbeach.adapters.All_user_chatting_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_all_other_users;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_just_admin;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chatting_fragment extends Fragment {

    TextView no_items_text_view;

    RecyclerView just_admin_recycle_view,all_users_recycle_view;
    ArrayList<Items_of_chatting_just_admin> items_of_user_chatting;
    All_user_chatting_adapter all_user_chatting_adapter;

    My_Flower_progress_dialog get_ready_chatting_progress_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;
    List<Items_of_chatting_all_other_users> items_of_chatting_all_other_users;



    Login_alert_dialog login_alert_dialog;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items_of_user_chatting =new ArrayList<>();
        items_of_chatting_all_other_users=new ArrayList<>();

        get_ready_chatting_progress_dialog=new My_Flower_progress_dialog(getContext());
        login_alert_dialog=new Login_alert_dialog(getContext());

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View  v=inflater.inflate(R.layout.chatting_fragment, container, false);


        no_items_text_view=(TextView)v.findViewById(R.id.chatting_fragment_no_items_text_view);

        just_admin_recycle_view=(RecyclerView)v.findViewById(R.id.chatting_fragment_just_admin_recycle_view);
        all_users_recycle_view=(RecyclerView)v.findViewById(R.id.chatting_fragment_all_users_recycle_view);



        if(!Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("")){

            if(!Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("admin")){
                get_ready_chatting_progress_dialog.start_dialog();
                startRetrofitToGetAdminDetails();

            }else{
                if(Shared_Helper.getkey(getContext(),"administrator").equals("true")){
                    get_ready_chatting_progress_dialog.start_dialog();
                    startRetrofitToGetUserDetails();
                }else{
                    startActivity(new Intent(getContext(), Check_administrator_dialog.class));
                    CustomIntent.customType(getContext(),"up-to-bottom");

                }

            }

        }else{
            all_users_recycle_view.setVisibility(View.GONE);
            just_admin_recycle_view.setVisibility(View.GONE);
            no_items_text_view.setVisibility(View.VISIBLE);
            login_alert_dialog.startAlertDialogForLogin();
        }



        return v;
    }



    private void startRetrofitToGetAdminDetails(){
        Retrofit_connection.getRetrofit_connection_instance().getJustAdminForChatting().enqueue(new Callback<List<Items_of_chatting_just_admin>>() {
            @Override
            public void onResponse(Call<List<Items_of_chatting_just_admin>> call, Response<List<Items_of_chatting_just_admin>> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
                    items_of_user_chatting = (ArrayList<Items_of_chatting_just_admin>) response.body();
                    all_user_chatting_adapter =new All_user_chatting_adapter(getContext(), items_of_user_chatting);
                    just_admin_recycle_view.setAdapter(all_user_chatting_adapter);
                    get_ready_chatting_progress_dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<List<Items_of_chatting_just_admin>> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                try {
                    get_ready_chatting_progress_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_ready_chatting_progress_dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startRetrofitToGetUserDetails(){
        Retrofit_connection.getRetrofit_connection_instance().getAllUsersForChatting().enqueue(new Callback<List<Items_of_chatting_just_admin>>() {
            @Override
            public void onResponse(Call<List<Items_of_chatting_just_admin>> call, Response<List<Items_of_chatting_just_admin>> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
                    if(response.body().size()<1){
                        Toasty.custom(getContext(),getString(R.string.not_found_any_user),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                    }
                    items_of_user_chatting = (ArrayList<Items_of_chatting_just_admin>) response.body();
                    all_user_chatting_adapter =new All_user_chatting_adapter(getContext(), items_of_user_chatting);
                    just_admin_recycle_view.setAdapter(all_user_chatting_adapter);
                    get_ready_chatting_progress_dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<List<Items_of_chatting_just_admin>> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                try {
                    get_ready_chatting_progress_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_ready_chatting_progress_dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
/*
    private void startFirebaseToGetOtherUsers(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("main_chat_rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                //long snach=snapshot.getChildrenCount();
                //Toast.makeText(Base_chatting_activity.this, String.valueOf(snach), Toast.LENGTH_SHORT).show();

                items_of_chatting_all_other_users.clear();


                for(final DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //map.put("chat_room_name",dataSnapshot.getValue());
                    //Items_of_chatting_all_other_users current_item=dataSnapshot.getValue(Items_of_chatting_all_other_users.class);
                     i=dataSnapshot.getKey();

                    items_of_chatting_all_other_users.add(new Items_of_chatting_all_other_users(i.toString()));
                    chatting_all_other_users_adapter=new Chatting_all_other_users_adapter(getContext(), items_of_chatting_all_other_users);
                    all_users_recycle_view.setAdapter(chatting_all_other_users_adapter);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


 */




}