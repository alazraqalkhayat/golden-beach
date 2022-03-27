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
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_comments;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Commentes_adapter extends RecyclerView.Adapter<Commentes_adapter.Suggestions_view_holder> {

    private Context c;
    private ArrayList<Items_of_comments> comments_items_arr;



    public Commentes_adapter(Context context, ArrayList<Items_of_comments> comments_items_arr){

        this.c=context;
        this.comments_items_arr=comments_items_arr;

    }



    @NonNull
    @Override
    public Suggestions_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.comments_items,parent,false);

        return new Suggestions_view_holder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final Suggestions_view_holder holder, int position) {
        Items_of_comments current_items=comments_items_arr.get(position);
        //String name_of_shop=current_items.getName_of_shop();
        //String note=current_items.getNote();

        if(current_items.getUser_name().equalsIgnoreCase("null")){
            holder.user_image_view.setVisibility(View.GONE);
            holder.user_name.setVisibility(View.GONE);
            holder.comment.setText(c.getResources().getString(R.string.no_comment));

        }else{
            Picasso.get().load("http://goldenbeachye.com/"+current_items.getUser_name_of_image()).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.user_image_view);
            holder.user_name.setText(current_items.getUser_name());
            holder.user_id.setText(String.valueOf(current_items.getUser_id()));
            holder.comment.setText(current_items.getComment());


        }
        /*
        if(current_items.getUser_name_of_image().equalsIgnoreCase("null")){

            holder.user_image_view.setImageDrawable(c.getResources().getDrawable(R.drawable-v21.sad_icon));
        }else{
            Picasso.get().load("http://goldenbetch.aba.vg/users_images/"+current_items.getUser_name_of_image()).fit().centerCrop().into(holder.user_image_view);

        }

        holder.user_name.setText(current_items.getUser_name());
        holder.user_id.setText(String.valueOf(current_items.getUser_id()));
        holder.comment.setText(current_items.getComment());

         */







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
        return comments_items_arr.size();
    }


    public class Suggestions_view_holder extends RecyclerView.ViewHolder{

        public CircleImageView user_image_view;

        public TextView user_name,user_id, comment,date;


        public CardView suggestion_card_view;


        public Suggestions_view_holder(@NonNull View itemView) {
            super(itemView);




            user_image_view=(CircleImageView)itemView.findViewById(R.id.comments_items_image_of_user);

            user_name=(TextView)itemView.findViewById(R.id.comments_items_user_name_text_view);
            user_id=(TextView)itemView.findViewById(R.id.comments_items_user_id_text_view);
            comment =(TextView)itemView.findViewById(R.id.comments_items_comments_text_view);


        }
    }
}
