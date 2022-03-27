package com.alazraq.alkhayat.goldenbeach.ui.watching_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.MainActivity;
import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.Splash_activity;
import com.alazraq.alkhayat.goldenbeach.broad_cast_resivers.Network_broadcast_receiver_register;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Login_alert_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Sqlite_Database_Connection;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_watching_list;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class Watching_list_fragment extends Fragment {

    ListView watching_list_list_view;
    ArrayList<Items_of_watching_list> items_of_watching_lists;
    ArrayList<Items_of_watching_list>delete_arr;

    Watching_list_list_adapter watching_list_view_adapter;

    TextView no_items;

    Sqlite_Database_Connection db;
    Login_alert_dialog login_alert_dialog;

    DatabaseReference databaseReference;
    Map<String,Object> add_message_hash_map;

    String get_user_name_from_shared_preference;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    View parentLayOut;
    Snackbar snackbar;

    private boolean CHECKBOX_IS_CHECKED;


    public Watching_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        get_user_name_from_shared_preference=Shared_Helper.getkey(getContext(),"user_name");

        db=new Sqlite_Database_Connection(getContext());

        items_of_watching_lists=new ArrayList<>();
        delete_arr=new ArrayList<>();

        items_of_watching_lists=db.getAllWatchingLists();
        CHECKBOX_IS_CHECKED=false;


        watching_list_view_adapter =new Watching_list_list_adapter(getContext(),items_of_watching_lists);
        login_alert_dialog=new Login_alert_dialog(getContext());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("main_chating_rooms");
        add_message_hash_map=new HashMap<>();

        connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo=connectivityManager.getActiveNetworkInfo();





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.watching_list_fragment, container, false);


        initViews(v);
        //parentLayOut=v.findViewById(android.R.id.content);
        //snackbar=Snackbar.make(parentLayOut,getResources().getString(R.string.no_internet_connection),Snackbar.LENGTH_LONG);
        //snackbar.setDuration(5000);

        if(items_of_watching_lists.size()==0){
            no_items.setVisibility(View.VISIBLE);
        }else{
            watching_list_list_view.setAdapter(watching_list_view_adapter);
            watching_list_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }

        watching_list_list_view.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                CHECKBOX_IS_CHECKED=true;
                watching_list_view_adapter.notifyDataSetChanged();

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater=actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.multiple_items_menu,menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                int id=menuItem.getItemId();

                if(id==R.id.multiple_items_menu_delete_items){
                    if(delete_arr.size()==0){
                        Toasty.custom(getContext(),getResources().getString(R.string.watching_list_no_item_selected),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                    }else{
                        startRemoveAllCheckedItemsAlertDialog();
                        //startRemoveAllCheckedItemsAlertDialog();
                    }
                    return true;
                }else if(id==R.id.multiple_items_menu_send_items){


                    if(delete_arr.size()==0){
                        Toasty.custom(getContext(),getResources().getString(R.string.watching_list_no_item_selected),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                    }else{
                        if(get_user_name_from_shared_preference.equalsIgnoreCase("admin")){
                            Toasty.custom(getContext(),getResources().getString(R.string.watching_list_you_are_the_admin),R.drawable.info_icon_golden,R.color.dark_golden,Toasty.LENGTH_LONG,true,true).show();
                        }else{
                            startSendAllCheckedItemsAlertDialog();
                        }
                    }
                    return true;
                }


                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                delete_arr.clear();
                CHECKBOX_IS_CHECKED=false;
                watching_list_view_adapter.notifyDataSetChanged();

            }
        });








        return v;
    }



    private void initViews(View v){
        no_items=(TextView)v.findViewById(R.id.watching_list_no_items_text_view);
        watching_list_list_view=(ListView)v.findViewById(R.id.watching_list_list_view);

    }
    //ALL REMOVE METHODS
    public void startRemoveAllCheckedItemsAlertDialog(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getContext().getResources().getString(R.string.are_you_sure))
                .setContentText(getContext().getResources().getString(R.string.all_list_sure_delete_the_watching_list_items_alert_dialog_message))
                .setConfirmButton( getContext().getResources().getString(R.string.all_list_sure_delete_the_watching_list_items_alert_dialog_positive_button),new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startRemoveAllCheckedItems();
                        sDialog.hideConfirmButton();
                        sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.setTitle(getResources().getString(R.string.deleted_successfully));
                        sDialog.setContentText("");
                        sDialog.setCancelText(getResources().getString(R.string.ok));
                        sDialog.getButton(SweetAlertDialog.BUTTON_CANCEL).setBackgroundColor(getResources().getColor(R.color.main_green_color));

                    }
                })
                .setCancelButton(getContext().getResources().getString(R.string.all_list_sure_delete_the_watching_list_items_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        sDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if(items_of_watching_lists.size()==0){
                                    Watching_list_fragment context=Watching_list_fragment.this;
                                    getFragmentManager().beginTransaction().detach(context).attach(context).commit();
                                }                            }
                        });
                    }
                })
                .show();
    }

    private void startRemoveAllCheckedItems(){
        int total_delete=delete_arr.size();
        for(int i=0;i<delete_arr.size();i++){
            String  check_delete_name=delete_arr.get(i).getName();
            int     check_delete_session=delete_arr.get(i).getSession();

            for(int j=0;j<items_of_watching_lists.size();j++){
                String  check_delete_name1=items_of_watching_lists.get(j).getName();
                int     check_delete_session1=items_of_watching_lists.get(j).getSession();
                if(check_delete_name.equalsIgnoreCase(check_delete_name1)&&
                        check_delete_session==check_delete_session1){
                    startRemoveAllCheckedFromDataBase(check_delete_name,check_delete_session);
                    items_of_watching_lists.remove(j);

                }

            }

        }

        if(total_delete>1){
            Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_deleted_items),Toast.LENGTH_SHORT,true).show();

        }else{
            Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_deleted_item),Toast.LENGTH_SHORT,true).show();
        }
        delete_arr.clear();
        CHECKBOX_IS_CHECKED=false;
        watching_list_view_adapter.notifyDataSetChanged();


    }

    private void startRemoveAllCheckedFromDataBase(String name,int session){
        db.deleteLimitedWatchingListItems(name,session);
    }



    //ALL SEND METHODS

    public void startSendAllCheckedItemsAlertDialog(){

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.all_list_sure_send_the_watching_list_items_alert_dialog_title))
                .setContentText(getContext().getResources().getString(R.string.all_list_sure_send_the_watching_list_items_alert_dialog_message))
                .setConfirmButton(getContext().getResources().getString(R.string.all_list_sure_send_the_watching_list_items_alert_dialog_positive_button),new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        if(networkInfo !=null && networkInfo.isConnected()==true){
                            if(Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("")){
                                login_alert_dialog.startAlertDialogForLogin();
                                sDialog.dismissWithAnimation();
                            }else{
                                startSendSllCheckedItemsToAdminWithOutDelete();
                                sDialog.hideConfirmButton();
                                sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                sDialog.setTitle(getResources().getString(R.string.sending_successfully));
                                sDialog.setContentText("");
                                sDialog.setCancelText(getResources().getString(R.string.ok));
                                sDialog.getButton(SweetAlertDialog.BUTTON_CANCEL).setBackgroundColor(getResources().getColor(R.color.main_green_color));

                            }

                        }else{

                            startAlertDialogForNoInternetConnection();
                        }

                    }
                })
                .setCancelButton(getContext().getResources().getString(R.string.all_list_sure_send_the_watching_list_items_alert_dialog_negative_button), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (sDialog.getCancelText().equalsIgnoreCase(getContext().getResources().getString(R.string.ok))){
                            sDialog.dismissWithAnimation();
                        }else {
                            if(networkInfo !=null && networkInfo.isConnected()==true){
                                if(Shared_Helper.getkey(getContext(),"user_name").equalsIgnoreCase("")){
                                    login_alert_dialog.startAlertDialogForLogin();
                                    sDialog.dismissWithAnimation();
                                }else{
                                    startSendSllCheckedItemsToAdminWithDelete();
                                    sDialog.hideConfirmButton();
                                    sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setTitle(getResources().getString(R.string.sending_successfully));
                                    sDialog.setContentText("");
                                    sDialog.setCancelText(getResources().getString(R.string.ok));
                                    sDialog.getButton(SweetAlertDialog.BUTTON_CANCEL).setBackgroundColor(getResources().getColor(R.color.main_green_color));

                                }

                            }else{
                                startAlertDialogForNoInternetConnection();

                            }
                        }



                        sDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                                if(items_of_watching_lists.size()==0){
                                    Watching_list_fragment context=Watching_list_fragment.this;
                                    getFragmentManager().beginTransaction().detach(context).attach(context).commit();
                                }
                            }
                        });
                    }
                })
                .show();
    }

    private void startSendSllCheckedItemsToAdminWithOutDelete(){

            int total_delete=delete_arr.size();
            for(int i=0;i<delete_arr.size();i++){
                String  check_delete_name=delete_arr.get(i).getName();
                int     check_delete_session=delete_arr.get(i).getSession();

                for(int j=0;j<items_of_watching_lists.size();j++){
                    String  check_delete_name1=items_of_watching_lists.get(j).getName();
                    int     check_delete_session1=items_of_watching_lists.get(j).getSession();
                    if(check_delete_name.equalsIgnoreCase(check_delete_name1)&&
                            check_delete_session==check_delete_session1){
                        startSendMessages(get_user_name_from_shared_preference,check_delete_name+" "+"SEASON"+" "+check_delete_session);
                    }

                }

            }

            if(total_delete>1){
                Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_sent_items),Toast.LENGTH_SHORT,true).show();


            }else{
                Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_sent_item),Toast.LENGTH_SHORT,true).show();

            }
            delete_arr.clear();
            CHECKBOX_IS_CHECKED=false;
            watching_list_view_adapter.notifyDataSetChanged();
        }


    private void startSendSllCheckedItemsToAdminWithDelete(){

            int total_delete=delete_arr.size();
            for(int i=0;i<delete_arr.size();i++){
                String  check_delete_name=delete_arr.get(i).getName();
                int     check_delete_session=delete_arr.get(i).getSession();

                for(int j=0;j<items_of_watching_lists.size();j++){
                    String  check_delete_name1=items_of_watching_lists.get(j).getName();
                    int     check_delete_session1=items_of_watching_lists.get(j).getSession();
                    if(check_delete_name.equalsIgnoreCase(check_delete_name1)&&
                            check_delete_session==check_delete_session1){
                        startSendMessages(get_user_name_from_shared_preference,check_delete_name+" "+"SEASON"+" "+check_delete_session);
                        startRemoveAllCheckedFromDataBase(check_delete_name,check_delete_session);
                        items_of_watching_lists.remove(j);

                    }

                }

            }

            if(total_delete>1){
                Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_sent_items),Toast.LENGTH_LONG,true).show();
            }else{
                Toasty.success(getContext(),total_delete+" "+getResources().getString(R.string.watching_list_sent_item),Toast.LENGTH_LONG,true).show();
            }
            delete_arr.clear();
            CHECKBOX_IS_CHECKED=false;
            watching_list_view_adapter.notifyDataSetChanged();


    }

    private void startSendMessages(String user_name,String message) {


        Map<String,Object> map=new HashMap<>();
        String temp_key= databaseReference.push().getKey();
        databaseReference.updateChildren(map);

        DatabaseReference reference=databaseReference.child(temp_key);
        add_message_hash_map.put("sender",user_name);
        add_message_hash_map.put("receiver","admin");
        add_message_hash_map.put("message",message);
        add_message_hash_map.put("read","no");

        reference.updateChildren(add_message_hash_map);

        //startReceiveMessages(user_name);


    }

    public void startAlertDialogForNoInternetConnection(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.network_icon)
                .setContentText(getResources().getString(R.string.no_internet_connection))
                .hideConfirmButton()
                .setCancelButton(getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        getActivity().finish();
                    }
                })
                .show();
    }



    public class Watching_list_list_adapter extends BaseAdapter {

        TextView name,session,count;
        CheckBox checkBox;

        ArrayList<Items_of_watching_list> arr;
        Context context;

        public Watching_list_list_adapter(Context context, ArrayList<Items_of_watching_list> arr) {
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

            Items_of_watching_list current_items=arr.get(position);



            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.watching_list_items,null);

            name = (TextView) v.findViewById(R.id.watching_list_items_name_text_view);
            session = (TextView) v.findViewById(R.id.watching_list_items_session_text_view);
            count = (TextView) v.findViewById(R.id.watching_list_items_count_text_view);

            checkBox=(CheckBox)v.findViewById(R.id.watching_list_items_check_box);

            final CardView cardView=(CardView)v.findViewById(R.id.watching_list_items_card_view);





            //________________________________________

            name.setText(String.valueOf(current_items.getName()));
            session.setText(String.valueOf(current_items.getSession()));
            count.setText(String.valueOf(position+1));

            if(CHECKBOX_IS_CHECKED){
                checkBox.setVisibility(View.VISIBLE);
            }else{
                checkBox.setVisibility(View.INVISIBLE);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if(isChecked){
                        delete_arr.add(new Items_of_watching_list(arr.get(position).getName(),arr.get(position).getSession()));
                        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.light_black));

                        }else{
                            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.dark_golden));

                        }

                    }else{

                        if(delete_arr.size()>0){
                            for (int i=0;i<delete_arr.size();i++){
                                String loop_name=delete_arr.get(i).getName();
                                int loop_session=delete_arr.get(i).getSession();
                                if(loop_name.equalsIgnoreCase(arr.get(position).getName())&&loop_session==arr.get(position).getSession()){
                                    delete_arr.remove(i);
                                    cardView.setCardBackgroundColor(context.getResources().getColor(R.color.light_gray));


                                }

                            }
                        }
                    }

                }
            });


            return v;
        }



    }

}
