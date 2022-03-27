package com.alazraq.alkhayat.goldenbeach.ui.sections;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.All_list_activity;
import com.alazraq.alkhayat.goldenbeach.adapters.Exbandable_list_view_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_expandable_list_view_body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class Sections_fragment extends Fragment {

    Exbandable_list_view_adapter exbandable_list_view_adapter;
    ExpandableListView expandableListView;

    List<String> header_items;
    HashMap<String ,List<Items_of_expandable_list_view_body>> bodyItems;


    Intent move_to_movies_list_activity_intent;
    Bundle move_to_movies_list_activity_bundle;

    int group_id,child_id;

    String name_of_section,brand_of_section;
    private OnFragmentInteractionListener mListener;

    public Sections_fragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        header_items=new ArrayList<>();
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_series_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_animation_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_play_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_documentaries_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_others_title));
        header_items.add(getResources().getString(R.string.sections_fragment__expandable_list_view_games_title));


        bodyItems=new HashMap<String ,List<Items_of_expandable_list_view_body>>();

        List<Items_of_expandable_list_view_body>movies_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_foreign)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_arabic)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_indian)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_korean)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_japanese)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_chinese)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_thai)));
        movies_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_movies_body_Indonesian)));

        List<Items_of_expandable_list_view_body>series_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_foreign)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_arabic)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_indian)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_turkish)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_korean)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_japanese)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_chinese)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_thai)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_Indonesian)));
        series_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_series_body_arabic_cartons)));

        List<Items_of_expandable_list_view_body>animation_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        animation_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_animation_body_series)));
        animation_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_animation_body_movies)));

        List<Items_of_expandable_list_view_body>plays_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        plays_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_play_body_plays)));

        List<Items_of_expandable_list_view_body>documentaries_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        documentaries_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_documentaries_body_documentaries)));

        List<Items_of_expandable_list_view_body>others_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        others_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_others_body_others)));

        List<Items_of_expandable_list_view_body>games_array_list=new ArrayList<Items_of_expandable_list_view_body>();
        games_array_list.add(new Items_of_expandable_list_view_body(getResources().getString(R.string.sections_fragment__expandable_list_view_games_body_games)));

        bodyItems.put(header_items.get(0),movies_array_list);
        bodyItems.put(header_items.get(1),series_array_list);
        bodyItems.put(header_items.get(2),animation_array_list);
        bodyItems.put(header_items.get(3),plays_array_list);
        bodyItems.put(header_items.get(4),documentaries_array_list);
        bodyItems.put(header_items.get(5),others_array_list);
        bodyItems.put(header_items.get(6),games_array_list);

        move_to_movies_list_activity_intent=new Intent(getContext(), All_list_activity.class);
        move_to_movies_list_activity_bundle=new Bundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout-v21 for this fragment
        View  v=inflater.inflate(R.layout.sections_fragment, container, false);

        expandableListView=(ExpandableListView)v.findViewById(R.id.sections_fragment_expandable_list_view);

        exbandable_list_view_adapter=new Exbandable_list_view_adapter(getContext(),header_items,bodyItems);

        expandableListView.setAdapter(exbandable_list_view_adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                group_id=(int)exbandable_list_view_adapter.getGroupId(groupPosition);
                child_id=(int)exbandable_list_view_adapter.getChildId(groupPosition,childPosition);

                startFilterTheSelectedItem(group_id,child_id);



                return false;
            }
        });

        expandableListView.expandGroup(0);
        expandableListView.expandGroup(1);

        return v;
    }


    private void startFilterTheSelectedItem(int group_id,int child_id){

        if(group_id==0){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_title);

            if(child_id==0){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_foreign);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==1){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_arabic);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==2){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_indian);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==3){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_korean);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==4){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_japanese);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==5){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_chinese);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==6){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_thai);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==7){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_movies_body_Indonesian);
                startAllListListActivity(name_of_section,brand_of_section);
            }
        }else if(group_id==1){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_series_title);

            if(child_id==0){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_foreign);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==1){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_arabic);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==2){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_indian);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==3){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_turkish);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==4){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_korean);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==5){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_japanese);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==6){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_chinese);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==7){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_thai);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==8){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_Indonesian);
                startAllListListActivity(name_of_section,brand_of_section);
            }else if(child_id==9){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_series_body_arabic_cartons);
                startAllListListActivity(name_of_section,brand_of_section);
            }
        }else if(group_id==2){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_animation_title);

            if(child_id==0){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_animation_body_series);
                startAllListListActivity(name_of_section,brand_of_section);

            }else if(child_id==1){
                brand_of_section=getString(R.string.sections_fragment__expandable_list_view_animation_body_movies);
                startAllListListActivity(name_of_section,brand_of_section);

            }
        }else if(group_id==3){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_play_title);
            brand_of_section=getString(R.string.sections_fragment__expandable_list_view_play_body_plays);
            startAllListListActivity(name_of_section,brand_of_section);
        }else if(group_id==4){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_documentaries_title);
            brand_of_section=getString(R.string.sections_fragment__expandable_list_view_documentaries_body_documentaries);
            startAllListListActivity(name_of_section,brand_of_section);

        }else if(group_id==5){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_others_title);
            brand_of_section=getString(R.string.sections_fragment__expandable_list_view_others_body_others);
            startAllListListActivity(name_of_section,brand_of_section);

        }else if(group_id==6){
            name_of_section=getString(R.string.sections_fragment__expandable_list_view_games_title);
            brand_of_section=getString(R.string.sections_fragment__expandable_list_view_games_body_games);
            startAllListListActivity(name_of_section,brand_of_section);

        }
    }

    private void startAllListListActivity(String section, String brand){
        move_to_movies_list_activity_bundle.putString("name_of_section",section);
        move_to_movies_list_activity_bundle.putString("brand_of_section",brand);
        move_to_movies_list_activity_intent.putExtras(move_to_movies_list_activity_bundle);
        startActivity(move_to_movies_list_activity_intent);
        CustomIntent.customType(getContext(),"left-to-right");

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
