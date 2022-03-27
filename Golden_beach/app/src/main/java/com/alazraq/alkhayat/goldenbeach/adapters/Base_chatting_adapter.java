package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_chatting;

import java.util.List;

public class Base_chatting_adapter extends RecyclerView.Adapter<Base_chatting_adapter.Base_chatting_view_holder> {

    private Context c;
    private List<Items_of_base_chatting> base_chatting_items_arr;

    public static final int LEFT_VIEW =0;
    public static final int RIGHT_VIEW =1;

    public String spichial_name;



    public Base_chatting_adapter(Context context, List<Items_of_base_chatting> base_chatting_items_arr,String spichial_name){

        this.c=context;
        this.base_chatting_items_arr=base_chatting_items_arr;
        this.spichial_name=spichial_name;

    }



    @NonNull
    @Override
    public Base_chatting_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType== LEFT_VIEW){
            View v= LayoutInflater.from(c).inflate(R.layout.base_chatting_items_left,parent,false);
            return new Base_chatting_view_holder(v);

        }else{
            View v= LayoutInflater.from(c).inflate(R.layout.base_chatting_items_right,parent,false);
            return new Base_chatting_view_holder(v);
        }

    }



    @Override
    public void onBindViewHolder(@NonNull final Base_chatting_view_holder holder, final int position) {
        final Items_of_base_chatting current_items= base_chatting_items_arr.get(position);

        holder.show_message.setText(current_items.getMessage());






    }

    @Override
    public int getItemCount() {
        return base_chatting_items_arr.size();

    }


    public class Base_chatting_view_holder extends RecyclerView.ViewHolder{

        TextView show_message;
        public Base_chatting_view_holder(@NonNull View itemView) {
            super(itemView);

            show_message=(TextView)itemView.findViewById(R.id.base_chatting_items_show_message_text_view);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if(base_chatting_items_arr.get(position).getSender().equalsIgnoreCase(Shared_Helper.getkey(c,"user_name"))){
            return LEFT_VIEW;
        }else{
            return RIGHT_VIEW;
        }


    }
}
