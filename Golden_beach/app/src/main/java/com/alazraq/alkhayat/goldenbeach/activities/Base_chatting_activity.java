package com.alazraq.alkhayat.goldenbeach.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.Base_chatting_adapter;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_connection_broadcaster_receiver;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_chatting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class Base_chatting_activity extends AppCompatActivity {

    Toolbar base_chatting_tool_bar;

    CircleImageView user_image_view;
    TextView user_name_text_view;
    RecyclerView base_chatting_recycle_view;
    ImageButton send_button;
    EditText message_edit_text;

    String get_user_name_from_chatting_fragment_string,get_user_name_from_shared_preference;

    DatabaseReference DatabaseReference,addDatabaseReference;
    Base_chatting_adapter base_chatting_adapter;
    List<Items_of_base_chatting> items_of_base_chatting;

    Map<String,Object> add_message_hash_map;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference2;

    Network_connection_broadcaster_receiver connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_chatting_activity);



        try {
            get_user_name_from_shared_preference=Shared_Helper.getkey(this,"user_name");
            Intent i=this.getIntent();
            //Bundle get_user_name_from_chatting_fragment_bundel=getIntent().getExtras();
            get_user_name_from_chatting_fragment_string =i.getExtras().getString("user_name");

        }catch (Exception e){
            e.getMessage();
        }

        initObjects();

        initViews();

        startSettingActionBar();

        startSetUsernameAndUserImage(get_user_name_from_chatting_fragment_string);



        base_chatting_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if(get_user_name_from_shared_preference.equalsIgnoreCase("admin")){
            startReceiveMessages();
        }else{
            startReceiveMessages();
        }


    user_image_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            show_image();
        }
    });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!message_edit_text.getText().toString().equalsIgnoreCase("")){
                    if(get_user_name_from_shared_preference.equalsIgnoreCase("admin")){
                        startSendMessages(message_edit_text.getText().toString());
                        message_edit_text.setText("");
                    }else{
                        startSendMessages(message_edit_text.getText().toString());
                        message_edit_text.setText("");

                    }

                }

            }
        });


    }

    private void initObjects() {
        items_of_base_chatting =new ArrayList<>();
        add_message_hash_map =new HashMap<>();
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);


    }

    private void initViews(){

        base_chatting_tool_bar=(Toolbar)findViewById(R.id.base_chatting_activity_tool_bar_id);
        setSupportActionBar(base_chatting_tool_bar);

        user_image_view=(CircleImageView)findViewById(R.id.base_chatting_activity_user_image_view);
        user_name_text_view=(TextView)findViewById(R.id.base_chatting_activity_user_name_text_view);

        base_chatting_recycle_view=(RecyclerView)findViewById(R.id.base_chatting_activity_recycle_view);
        base_chatting_recycle_view.setHasFixedSize(true);
        base_chatting_recycle_view.setLayoutManager(linearLayoutManager);

        message_edit_text=(EditText)findViewById(R.id.base_chatting_activity_message_edit_text);
        send_button=(ImageButton)findViewById(R.id.base_chatting_activity_image_button);


    }

    private void startSettingActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

    }

    private void startSetUsernameAndUserImage(String user_name) {

        user_name_text_view.setText(user_name);
        Picasso.get().load("http://goldenbeachye.com/users_images/"+user_name+".jpg").fit().centerCrop().into(user_image_view);

    }

    private void startReceiveMessages(){

        DatabaseReference = FirebaseDatabase.getInstance().getReference().child("main_chating_rooms");
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                items_of_base_chatting.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Items_of_base_chatting current_item=(Items_of_base_chatting) dataSnapshot.getValue(Items_of_base_chatting.class);
                    if(get_user_name_from_shared_preference.equalsIgnoreCase("admin")){


                        if(current_item.getSender().equalsIgnoreCase(get_user_name_from_shared_preference)&&current_item.getReceiver().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)
                                ||current_item.getSender().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)&&current_item.getReceiver().equalsIgnoreCase(get_user_name_from_shared_preference)){
                            items_of_base_chatting.add(current_item);
                            if(items_of_base_chatting.get(items_of_base_chatting.size()-1).getReceiver().equalsIgnoreCase("admin")&&items_of_base_chatting.get(items_of_base_chatting.size()-1).getRead().equalsIgnoreCase("no")&&items_of_base_chatting.get(items_of_base_chatting.size()-1).getSender().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)){
                                DatabaseReference ref=DatabaseReference.child(dataSnapshot.getKey());
                                Map<String,Object> update1=new HashMap<>();

                                update1.put("read","yes");
                                ref.updateChildren(update1);


                            }

                            base_chatting_adapter=new Base_chatting_adapter(Base_chatting_activity.this, items_of_base_chatting, get_user_name_from_chatting_fragment_string);
                            base_chatting_recycle_view.setAdapter(base_chatting_adapter);

                        }

                    }else{

                        if(current_item.getSender().equalsIgnoreCase(get_user_name_from_shared_preference)&&current_item.getReceiver().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)
                                ||current_item.getSender().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)&&current_item.getReceiver().equalsIgnoreCase(get_user_name_from_shared_preference)){
                            items_of_base_chatting.add(current_item);
                            if(items_of_base_chatting.get(items_of_base_chatting.size()-1).getReceiver().equalsIgnoreCase(get_user_name_from_shared_preference)&&items_of_base_chatting.get(items_of_base_chatting.size()-1).getRead().equalsIgnoreCase("no")&&items_of_base_chatting.get(items_of_base_chatting.size()-1).getSender().equalsIgnoreCase(get_user_name_from_chatting_fragment_string)){
                                DatabaseReference ref=DatabaseReference.child(dataSnapshot.getKey());
                                Map<String,Object> update=new HashMap<>();

                                update.put("read","yes");
                                ref.updateChildren(update);


                            }

                            base_chatting_adapter=new Base_chatting_adapter(Base_chatting_activity.this, items_of_base_chatting, get_user_name_from_chatting_fragment_string);
                            base_chatting_recycle_view.setAdapter(base_chatting_adapter);

                        }



                    }



                }






                    base_chatting_recycle_view.scrollToPosition(items_of_base_chatting.size());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void startSendMessages(String message) {

        DatabaseReference = FirebaseDatabase.getInstance().getReference().child("main_chating_rooms");

        Map<String,Object> map=new HashMap<>();
        String temp_key= DatabaseReference.push().getKey();
        DatabaseReference.updateChildren(map);

        DatabaseReference reference=DatabaseReference.child(temp_key);
        add_message_hash_map.put("sender",get_user_name_from_shared_preference);
        add_message_hash_map.put("receiver", get_user_name_from_chatting_fragment_string);
        add_message_hash_map.put("message",message);
        add_message_hash_map.put("read","no");
        reference.updateChildren(add_message_hash_map);

        //startReceiveMessages(user_name);


    }

    private void show_image(){
        Intent openImageIntent=new Intent(Base_chatting_activity.this, Show_image_activity.class);
        Bundle openImageBundle=new Bundle();
        openImageBundle.putString("user_name", get_user_name_from_chatting_fragment_string);
        openImageBundle.putString("from","base_chatting_activity");
        openImageIntent.putExtras(openImageBundle);
        startActivity(openImageIntent);
        CustomIntent.customType(Base_chatting_activity.this,"fadein-to-fadeout");

    }



    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Base_chatting_activity.this,"right-to-left");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //startChattingService();

        //register internet broadcast receiver
        connection=new Network_connection_broadcaster_receiver();
        registerReceiver(connection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (Shared_Helper.getkey(this,"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //stopChattingService();

        try {
            if(connection!=null){
                unregisterReceiver(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
