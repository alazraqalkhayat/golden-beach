package com.alazraq.alkhayat.goldenbeach.adds_management_fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.adapters.All_adds_grid_view_list_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_spot_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Shared_Helper;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_advertisements;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_adds_fragment extends Fragment {

    ArrayList<Items_of_advertisements> item_Of_Advertisements;
    All_adds_grid_view_list_adapter grid_view_adapter;
    GridView gridView;

    My_spot_progress_dialog delete_add_dialog;
    Retrofit_on_failure_actions retrofit_on_failure_actions;
    My_Flower_progress_dialog get_all_adds_details_dialog;

    TextView no_adds_text_view;

    public All_adds_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item_Of_Advertisements =new ArrayList<>();
        get_all_adds_details_dialog =new My_Flower_progress_dialog(getContext());
        delete_add_dialog =new My_spot_progress_dialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.all_adds_fragment, container, false);

        initViews(v);

        get_all_adds_details_dialog.start_dialog();
        startRetrofitForGetAllAdds();


        return v;
    }


    private void initViews(View v){
        gridView=(GridView)v.findViewById(R.id.all_adds_fragment_grid_view);
        no_adds_text_view=(TextView)v.findViewById(R.id.all_adds_no_adds_text_view);

    }
    private void startRetrofitForGetAllAdds(){
        Retrofit_connection.getRetrofit_connection_instance().getAllAdds().enqueue(new Callback<List<Items_of_advertisements>>() {
            @Override
            public void onResponse(Call<List<Items_of_advertisements>> call, Response<List<Items_of_advertisements>> response) {
                if(response.isSuccessful()){
                    if(response.isSuccessful()){
                        item_Of_Advertisements =(ArrayList<Items_of_advertisements>)response.body();
                        grid_view_adapter=new All_adds_grid_view_list_adapter(getContext(), item_Of_Advertisements);
                        gridView.setAdapter(grid_view_adapter);
                        get_all_adds_details_dialog.dismiss();

                        if(item_Of_Advertisements.size()==1&& item_Of_Advertisements.get(0).getDescription().equalsIgnoreCase("null")){
                                no_adds_text_view.setVisibility(View.VISIBLE);
                                gridView.setVisibility(View.GONE);
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Items_of_advertisements>> call, Throwable t) {
                retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext(),t.getMessage());
                try {
                    get_all_adds_details_dialog.dismiss();
                    retrofit_on_failure_actions.showAction();
                }catch (Exception e){
                    get_all_adds_details_dialog.dismiss();
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        if (Shared_Helper.getkey(getContext(),"dark_mode").equalsIgnoreCase("on")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
