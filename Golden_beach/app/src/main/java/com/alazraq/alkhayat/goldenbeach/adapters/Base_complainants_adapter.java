package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_complainants;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_suggestions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class Base_complainants_adapter extends RecyclerView.Adapter<Base_complainants_adapter.complainants_view_holder> {

    private Context c;
    private ArrayList<Items_of_base_complainants> complainants_items_arr;



    public Base_complainants_adapter(Context context, ArrayList<Items_of_base_complainants> complainants_items_arr){

        this.c=context;
        this.complainants_items_arr=complainants_items_arr;

    }



    @NonNull
    @Override
    public complainants_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.base_complainants_items,parent,false);

        return new complainants_view_holder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final complainants_view_holder holder, final int position) {
        final Items_of_base_complainants current_items=complainants_items_arr.get(position);
        //String name_of_shop=current_items.getName_of_shop();
        //String note=current_items.getNote();

        Picasso.get().load("http://goldenbeachye.com/"+current_items.getUser_name_of_image()).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.user_image_view);


        holder.user_name.setText(current_items.getUser_name());
        holder.user_id.setText(String.valueOf(current_items.getUser_id()));
        holder.complainant.setText(current_items.getComplainant());

        holder.complainant_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(c, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //Collections.swap(complainants_items_arr,position,0);
                //notifyItemMoved(position,0);
                //int p=holder.get();
                //Toast.makeText(c, String.valueOf(p), Toast.LENGTH_SHORT).show();


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


    @Override
    public int getItemCount() {
        return complainants_items_arr.size();
    }


    public class complainants_view_holder extends RecyclerView.ViewHolder{

        CircleImageView user_image_view;

        public TextView user_name,user_id,complainant,date;


        public CardView complainant_card_view;




        public complainants_view_holder(@NonNull View itemView) {
            super(itemView);




            complainant_card_view=(CardView) itemView.findViewById(R.id.base_complainants_items_complainant_card_view);

            user_image_view=(CircleImageView)itemView.findViewById(R.id.base_complainants_items_image_of_user);

            user_name=(TextView) itemView.findViewById(R.id.base_complainants_items_user_name_text_view);
            user_id=(TextView)itemView.findViewById(R.id.base_complainants_items_user_id_text_view);
            complainant=(TextView)itemView.findViewById(R.id.base_complainants_items_complainant_text_view);


        }

        public int get(){
            int po=getAdapterPosition();
            return po;
        }
    }


}
