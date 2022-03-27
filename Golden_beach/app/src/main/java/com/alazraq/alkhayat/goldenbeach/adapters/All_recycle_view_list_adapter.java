package com.alazraq.alkhayat.goldenbeach.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alazraq.alkhayat.goldenbeach.Load_more_interface;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.activities.Check_administrator_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_suite_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Sqlite_Database_Connection;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;


public class All_recycle_view_list_adapter extends RecyclerView.Adapter<All_recycle_view_list_adapter.All_view_holder> {

    Intent move_to_about_all_activity_intent;
    Bundle move_to_about_all_activity_bundle;
    My_suite_alert_dialog alert_dialog;

    ProgressBar loading_progress_bar;


    ArrayList<Items_of_all_list> arr;
    public Context context;


    public  final int VIEW_TYPE_ITEM = 0;
    public  final int VIEW_TYPE_LOADING = 1;

    Load_more_interface loadmore_interface;
    Activity activity;

    boolean isLoading;

    int visible_thres_holder=5;
    int last_visible_item,total_item_counts;

    public All_recycle_view_list_adapter(Context context, ArrayList<Items_of_all_list> arr) {

        this.context = context;
        this.arr = arr;

    }


    @NonNull
    @Override
    public All_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.all_list_items, parent, false);
        return new All_view_holder(v);


    }


    @Override
    public void onBindViewHolder(@NonNull final All_view_holder holder, int position) {
        final Items_of_all_list current_items = arr.get(position);

        if(current_items.getId()==0){
            //startShowNoItemsDialog();
            holder.cardView.setVisibility(View.GONE);
            String name_of_section=nameOfSectionNotExist(arr.get(position).getName_of_section());
            Toasty.custom(context, name_of_section+" "+context.getResources().getString(R.string.are_not_exist), R.drawable.info_icon_golden, R.color.dark_golden, Toasty.LENGTH_LONG, true, true).show();


            //startNoItemsDialog(name_of_section);
            //holder.no_items_image_view.setVisibility(View.VISIBLE);
            //holder.relativeLayout.setBackground(context.getResources().getDrawable(R.drawable-v21.logo));

        }else{
            if(current_items.getYear()==0){
                holder.year.setVisibility(View.GONE);
            }
            holder.id.setText(String.valueOf(current_items.getId()));
            holder.name_of_section.setText(current_items.getName_of_section());
            holder.brand_of_section.setText(String.valueOf(current_items.getBrand_of_section()));
            holder.name_of_category.setText(current_items.getName_of_category());
            holder.name.setText(current_items.getName());

            /*
            if(holder.name.getText().length()>20){
                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            }else if(holder.name.getText().length()>25){
                holder.name.setTextSize(10);
            }

             */

            holder.session.setText(String.valueOf(current_items.getSession()));
            holder.name_of_image.setText(current_items.getName_of_image());
            holder.year.setText(String.valueOf(String.valueOf(current_items.getYear())));
            holder.story.setText(current_items.getStory());
            holder.trailer.setText(current_items.getTrailer());

            //Picasso.get().load("http://goldenbetch.aba.vg/" + current_items.getName_of_image()).fit().centerCrop().into(holder.all_image_view);
            Picasso.get().load("http://goldenbeachye.com/"+ current_items.getName_of_image()).fit().memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.all_image_view);


        }


        holder.all_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                move_to_about_all_activity_intent = new Intent(context, About_all_activity.class);
                move_to_about_all_activity_bundle = new Bundle();

                startAboutAllActivity(move_to_about_all_activity_bundle, move_to_about_all_activity_intent,
                        holder.name_of_section.getText().toString(), holder.brand_of_section.getText().toString(),
                        holder.name_of_category.getText().toString(), holder.id.getText().toString(),
                        holder.name.getText().toString(), holder.name_of_image.getText().toString(), holder.session.getText().toString(),
                        holder.year.getText().toString(), holder.story.getText().toString(), holder.trailer.getText().toString());

                //startAddAllToSqliteDatabase(holder.name.getText().toString(), Integer.valueOf(holder.session.getText().toString()));


            }
        });


        holder.all_image_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (Shared_Helper.getkey(context, "user_name").equalsIgnoreCase("admin")) {
                    if(Shared_Helper.getkey(context,"administrator").equals("true")){

                        startRemoveAndEditThePost(holder.name_of_section.getText().toString(), holder.brand_of_section.getText().toString(),
                                holder.name_of_category.getText().toString(), holder.id.getText().toString(),
                                holder.name.getText().toString(), holder.name_of_image.getText().toString(), holder.session.getText().toString(),
                                holder.year.getText().toString(), holder.story.getText().toString(), holder.trailer.getText().toString());

                    }else{
                        context.startActivity(new Intent(context, Check_administrator_dialog.class));
                        CustomIntent.customType(context,"up-to-bottom");

                    }

                }

                return true;
            }
        });


    }

    private void startAboutAllActivity(Bundle bundle, Intent intent, String name_of_section, String brand_of_section, String name_of_category
            , String id, String name, String name_of_image, String session, String year, String story,
                                       String trailer) {

        bundle.putString("name_of_section", name_of_section);
        bundle.putString("brand_of_section", brand_of_section);
        bundle.putString("name_of_category", name_of_category);
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("name_of_image", name_of_image);
        bundle.putString("session", session);
        bundle.putString("year", year);
        bundle.putString("story", story);
        bundle.putString("trailer", trailer);
        intent.putExtras(bundle);
        context.startActivity(intent);
        CustomIntent.customType(context,"fadein-to-fadeout");



    }

    private void startRemoveAndEditThePost(String name_of_section, String brand_of_section, String name_of_category
            , String id, String name, String name_of_image, String session, String year, String story,
                                           String trailer) {


        //alert_dialog = new My_suite_alert_dialog(context, id, session, name_of_section, brand_of_section, name_of_category, name, name_of_image, year, story, trailer, "all_list_activity");
        alert_dialog = new My_suite_alert_dialog(context, id, session, name_of_section, brand_of_section, name_of_category, name, name_of_image, year, story, trailer, context.getClass().getName());
        alert_dialog.startManagementThePostAlertDialog();
        //context.startActivity(new Intent(context, MainActivity.class));


    }

    /*
    private void startAddAllToSqliteDatabase(String name, int session) {
        Sqlite_Database_Connection db = new Sqlite_Database_Connection(context);
        boolean result = db.insertNameAndSessionOfAll(name, session);


    }

    private void startNoItemsDialog(String name_of_section){
        My_suite_alert_dialog no_items_dialog=new My_suite_alert_dialog(context,name_of_section);
        no_items_dialog.showNoItemsDialog();

    }

     */

    private String nameOfSectionNotExist(String name_of_section){

        if(name_of_section.equalsIgnoreCase("MOVIES")){
            name_of_section=context.getResources().getString(R.string.section_movies);
        }else if(name_of_section.equalsIgnoreCase("SERIES")){
            name_of_section=context.getResources().getString(R.string.section_series);

        }else if(name_of_section.equalsIgnoreCase("ANIMATIONS")){
            name_of_section=context.getResources().getString(R.string.animations_text_view);

        }else if(name_of_section.equalsIgnoreCase("PLAYS")){
            name_of_section=context.getResources().getString(R.string.section_plays);

        }else if(name_of_section.equalsIgnoreCase("DOCUMENTARIES")){
            name_of_section=context.getResources().getString(R.string.section_documentaries);

        }else if(name_of_section.equalsIgnoreCase("OTHERS")){
            name_of_section=context.getResources().getString(R.string.section_others);

        }else if(name_of_section.equalsIgnoreCase("GAMES")){
            name_of_section=context.getResources().getString(R.string.section_games);

        }

        return name_of_section;
    }

    public void clearItems(){
        arr.clear();

    }

    public void add(ArrayList<Items_of_all_list> arr){
        arr.addAll(arr);
    }
    @Override
    public int getItemCount() {
        return arr.size();
    }


    public class All_view_holder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView all_image_view;

        TextView name_of_section, brand_of_section, name_of_category,
                name, name_of_image, story, trailer, id, session, year, all_position;

        Button see_more_details_button;
        RelativeLayout relativeLayout;

        public All_view_holder(@NonNull View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.all_list_items_card_view);

            id = (TextView) itemView.findViewById(R.id.items_id_text_view);
            name_of_section = (TextView) itemView.findViewById(R.id.items_name_of_section_text_view);
            brand_of_section = (TextView) itemView.findViewById(R.id.items_brand_of_section_text_view);
            name_of_category = (TextView) itemView.findViewById(R.id.items_name_of_category_text_view);
            name = (TextView) itemView.findViewById(R.id.items_name_text_view);
            session = (TextView) itemView.findViewById(R.id.items_session_text_view);
            name_of_image = (TextView) itemView.findViewById(R.id.items_name_of_image_text_view);
            year = (TextView) itemView.findViewById(R.id.items_year_text_view);
            story = (TextView) itemView.findViewById(R.id.items_story_text_view);
            trailer = (TextView) itemView.findViewById(R.id.items_trailer_uri_text_view);
            all_image_view = (ImageView) itemView.findViewById(R.id.items_image_image_view);

            see_more_details_button = itemView.findViewById(R.id.items_see_more_details_button);

        }


    }
}
