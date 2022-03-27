package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Base_chatting_activity;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_chatting;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_all_other_users;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class Chatting_all_other_users_adapter extends RecyclerView.Adapter<Chatting_all_other_users_adapter.Chatting_view_holder> {

    private Context c;
    private List<Items_of_chatting_all_other_users> all_other_users_arr;

    private DatabaseReference databaseReference;



    public Chatting_all_other_users_adapter(Context context, List<Items_of_chatting_all_other_users> all_other_users_arr){

        this.c=context;
        this.all_other_users_arr=all_other_users_arr;


    }



    @NonNull
    @Override
    public Chatting_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.chatting_items,parent,false);

        return new Chatting_view_holder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final Chatting_view_holder holder, final int position) {
        final Items_of_chatting_all_other_users current_items=all_other_users_arr.get(position);

        Picasso.get().load("http://goldenbeachye.com/users_images/"+current_items.getChat_room_name()+".jpg").fit().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.user_image_view);

        holder.user_name.setText(current_items.getChat_room_name());
        //startReceiveMessages(current_items.getChat_room_name(),position);


        holder.complainant_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Items_of_chatting_all_other_users items=all_other_users_arr.get(position);
                //Toast.makeText(c, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //Collections.swap(complainants_items_arr,position,0);
                //notifyItemMoved(position,0);
                //int p=holder.get();
                //Toast.makeText(c, String.valueOf(p), Toast.LENGTH_SHORT).show();
                startMoveToBaseCattingActivity(items.getChat_room_name());



            }
        });

/*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("main_chat_rooms");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Toast.makeText(c, "added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Toast.makeText(c, snapshot.getKey(), Toast.LENGTH_SHORT).show();
                all_other_users_arr.add(0,new Items_of_chatting_all_other_users(snapshot.getKey()));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


 */



    }

    private void startMoveToBaseCattingActivity(String user_name){
        Intent move_to_base_chatting_intent=new Intent(c, Base_chatting_activity.class);
        move_to_base_chatting_intent.putExtra("user_name",user_name);
        c.startActivity(move_to_base_chatting_intent);
        CustomIntent.customType(c,"left-to-right");


    }




    @Override
    public int getItemCount() {
        return all_other_users_arr.size();
    }


    public class Chatting_view_holder extends RecyclerView.ViewHolder{

        public CircleImageView user_image_view;

        public TextView user_name,user_id;


        public CardView complainant_card_view;


        public Chatting_view_holder(@NonNull View itemView) {
            super(itemView);




            complainant_card_view=(CardView)itemView.findViewById(R.id.chatting_items_chatting_card_view);

            user_image_view=(CircleImageView)itemView.findViewById(R.id.chatting_items_image_of_user);

            user_name=(TextView)itemView.findViewById(R.id.chatting_items_user_name_text_view);
            user_id=(TextView)itemView.findViewById(R.id.chatting_items_user_id_text_view);


        }




    }




}
