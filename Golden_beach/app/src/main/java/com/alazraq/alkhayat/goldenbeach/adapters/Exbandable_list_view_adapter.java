package com.alazraq.alkhayat.goldenbeach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_expandable_list_view_body;

import java.util.HashMap;
import java.util.List;

public class Exbandable_list_view_adapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> header_items;
    private HashMap<String ,List<Items_of_expandable_list_view_body>> body_items;

    public Exbandable_list_view_adapter(Context context, List<String> header_items, HashMap<String, List<Items_of_expandable_list_view_body>> body_items) {
        this.context = context;
        this.header_items = header_items;
        this.body_items = body_items;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.body_items.get(this.header_items.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Items_of_expandable_list_view_body exit_items=(Items_of_expandable_list_view_body)getChild(groupPosition,childPosition);

        LayoutInflater Inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=Inflater.inflate(R.layout.sections_list_body_items,null);


        TextView childName=(TextView)convertView.findViewById(R.id.sections_list_body_items_text_view);
        childName.setText(exit_items.name_of_category);



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.body_items.get(this.header_items.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.header_items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.header_items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String header_title=(String)getGroup(groupPosition);

        LayoutInflater Inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=Inflater.inflate(R.layout.sections_list_header_items,null);

        TextView headerItem=(TextView)convertView.findViewById(R.id.sections_list_header_item_text_view);
        headerItem.setText(header_title);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
