package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_suite_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_advertisements;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;

import java.util.ArrayList;

public class All_adds_grid_view_list_adapter extends BaseAdapter  {

    TextView id,name_of_owner,description,count;
    CardView cardView;
    My_suite_alert_dialog alert_dialog;




    ArrayList<Items_of_advertisements> arr;
    Context context;

    int iposition;

   public All_adds_grid_view_list_adapter(Context context, ArrayList<Items_of_advertisements> arr) {
        this.context=context;

        this.arr = arr;
    }


    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        Items_of_advertisements current_items=arr.get(position);



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.all_adds_list_items,null);

        id = (TextView) v.findViewById(R.id.all_adds_items_id_of_add_text_view);
        name_of_owner = (TextView) v.findViewById(R.id.all_adds_items_name_of_add_owner_text_view);
        description = (TextView) v.findViewById(R.id.all_adds_items_description_of_add_text_view);
        count = (TextView) v.findViewById(R.id.all_adds_items_count_text_view);
        cardView=(CardView)v.findViewById(R.id.all_adds_fragment_card_view);


        //________________________________________
        if(current_items.getDescription().equalsIgnoreCase("null")){
            startNoAddsDialog();
            cardView.setVisibility(View.GONE);
        }else{
            id.setText(String.valueOf(current_items.getAdd_id()));
            name_of_owner.setText(current_items.getName_of_owner());
            description.setText(current_items.getDescription());
            count.setText(String.valueOf(position+1));

        }

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Items_of_advertisements current_items=arr.get(position);
                startRemoveAndEditThePost(String.valueOf(current_items.getAdd_id()),current_items.getName_of_owner(),current_items.getDescription());

                return true;
            }
        });


        return v;
    }

    private void startNoAddsDialog(){
        My_suite_alert_dialog no_adds_dialog=new My_suite_alert_dialog(context);
        no_adds_dialog.showNoAddsDialog();

    }
    private void startRemoveAndEditThePost(String id,String name_of_owner,String description){

            alert_dialog=new My_suite_alert_dialog(context,id,name_of_owner,description);
            alert_dialog.startManagementTheAddAlertDialog();
    }



}
