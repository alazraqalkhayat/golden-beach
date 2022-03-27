package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_suggestions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Base_suggestions_adapter extends RecyclerView.Adapter<Base_suggestions_adapter.Suggestions_view_holder> {

    private Context c;
    private ArrayList<Items_of_base_suggestions> suggestions_items_arr;



    public Base_suggestions_adapter(Context context, ArrayList<Items_of_base_suggestions> suggestions_items_arr){

        this.c=context;
        this.suggestions_items_arr=suggestions_items_arr;

    }



    @NonNull
    @Override
    public Suggestions_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.base_suggestions_items,parent,false);

        return new Suggestions_view_holder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final Suggestions_view_holder holder, int position) {
        final Items_of_base_suggestions current_items=suggestions_items_arr.get(position);
        //String name_of_shop=current_items.getName_of_shop();
        //String note=current_items.getNote();

        Picasso.get().load("http://goldenbeachye.com/"+current_items.getUser_name_of_image()).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.user_image_view);

        holder.user_name.setText(current_items.getUser_name());
        holder.user_id.setText(String.valueOf(current_items.getUser_id()));
        holder.suggestion.setText(current_items.getSuggestion());

        holder.suggestion_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(c,holder.suggestion.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        });

        holder.suggestion_card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //Toast.makeText(c,c.getPackageName(), Toast.LENGTH_LONG).show();

                return true;
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
        return suggestions_items_arr.size();
    }


    public class Suggestions_view_holder extends RecyclerView.ViewHolder{

        public CircleImageView user_image_view;

        public TextView user_name,user_id,suggestion,date;


        public CardView suggestion_card_view;


        public Suggestions_view_holder(@NonNull View itemView) {
            super(itemView);



            suggestion_card_view=(CardView)itemView.findViewById(R.id.base_suggestions_items_suggestion_card_view);

            user_image_view=(CircleImageView)itemView.findViewById(R.id.base_suggestions_items_image_of_user);

            user_name=(TextView)itemView.findViewById(R.id.base_suggestions_items_user_name_text_view);
            user_id=(TextView)itemView.findViewById(R.id.base_suggestions_items_user_id_text_view);
            suggestion=(TextView)itemView.findViewById(R.id.base_suggestions_items_suggestion_text_view);


        }
    }
}
