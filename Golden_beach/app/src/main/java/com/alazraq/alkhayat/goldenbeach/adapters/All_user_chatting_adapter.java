package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Base_chatting_activity;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_just_admin;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class All_user_chatting_adapter extends RecyclerView.Adapter<All_user_chatting_adapter.Chatting_view_holder> {

    private Context c;
    private ArrayList<Items_of_chatting_just_admin> just_admins_arr;



    public All_user_chatting_adapter(Context context, ArrayList<Items_of_chatting_just_admin> just_admins_arr){

        this.c=context;
        this.just_admins_arr=just_admins_arr;

    }



    @NonNull
    @Override
    public Chatting_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.chatting_items,parent,false);

        return new Chatting_view_holder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final Chatting_view_holder holder, final int position) {
        final Items_of_chatting_just_admin current_items=just_admins_arr.get(position);

        Picasso.get().load("http://goldenbeachye.com/"+current_items.getUser_name_of_image()).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.user_image_view);

        holder.user_name.setText(current_items.getUser_name());
        holder.user_id.setText(String.valueOf(current_items.getUser_id()));

        holder.complainant_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Items_of_chatting_just_admin items=just_admins_arr.get(position);
                //Toast.makeText(c, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //Collections.swap(complainants_items_arr,position,0);
                //notifyItemMoved(position,0);
                //int p=holder.get();
                //Toast.makeText(c, String.valueOf(p), Toast.LENGTH_SHORT).show();
                startMoveToBaseCattingActivity(items.getUser_name());



            }
        });








/*
        holder.lcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //String name_of_shop_from_holder=holder.lname_of_shop.getText().toString();
                //String brand_of_service_from_holder=holder.brand_of_service.getText().toString();
                go_to_sp_activity_intent=new Intent(c,Myaccount_tabs_activity.class);

                bundle_of_send_name_of_shop_to_sp_activity=new Bundle();
                bundle_of_send_name_of_shop_to_sp_activity.putString("sp_name_of_shop",name_of_shop_from_holder);
                bundle_of_send_name_of_shop_to_sp_activity.putString("sp_brand_of_service",brand_of_service_from_holder);

                go_to_sp_activity_intent.putExtras(bundle_of_send_name_of_shop_to_sp_activity);
                c.startActivity(go_to_sp_activity_intent);




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
        return just_admins_arr.size();
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
