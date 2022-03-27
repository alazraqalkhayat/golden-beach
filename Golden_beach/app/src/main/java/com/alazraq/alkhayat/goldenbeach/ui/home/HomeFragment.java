package com.alazraq.alkhayat.goldenbeach.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alazraq.alkhayat.goldenbeach.R;
import com.alazraq.alkhayat.goldenbeach.activities.About_all_activity;
import com.alazraq.alkhayat.goldenbeach.adapters.All_recycle_view_list_adapter;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_Flower_progress_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.My_progrss_dialog;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_connection;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Retrofit_on_failure_actions;
import com.alazraq.alkhayat.goldenbeach.helper_classes.Sqlite_Database_Connection;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_advertisements;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ImageSlider adds_image_slider;

    List<Items_of_advertisements> item_Of_Advertisements;
    List<SlideModel> image_slider_list;


    All_recycle_view_list_adapter movies_adapter;
    All_recycle_view_list_adapter series_adapter;
    All_recycle_view_list_adapter animation_adapter;
    All_recycle_view_list_adapter plays_adapter;
    All_recycle_view_list_adapter documentaries_adapter;
    All_recycle_view_list_adapter others_adapter;


    ArrayList<Items_of_all_list> items_of_movies_lists;
    ArrayList<Items_of_all_list> items_of_series_lists;
    ArrayList<Items_of_all_list> items_of_animations_lists;
    ArrayList<Items_of_all_list> items_of_plays_lists;
    ArrayList<Items_of_all_list> items_of_documentaries_lists;
    ArrayList<Items_of_all_list> items_of_others_lists;


    RecyclerView movies_recycle_view;
    RecyclerView series_recycle_view;
    RecyclerView animations_recycle_view;
    RecyclerView plays_recycle_view;
    RecyclerView documentaries_recycle_view;
    RecyclerView others_recycle_view;

    SwipeRefreshLayout swipeRefreshLayout;

    Sqlite_Database_Connection db;

    My_progrss_dialog posts_dialog;



    Intent move_to_about_all_activity_intent;
    Bundle move_to_about_all_activity_bundle;

    int REFRESH_CODE;
    static int limit=0;

    GridLayoutManager manager;
    My_Flower_progress_dialog dialog;
    //ACProgressFlower  dialog;

    Retrofit_on_failure_actions retrofit_on_failure_actions;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //register internet broadcast receiver
        //Network_broadcast_receiver_register register =new Network_broadcast_receiver_register(getContext());
        //register.registerNetworkConnection();

        item_Of_Advertisements =new ArrayList<>();
        image_slider_list=new ArrayList<>();

        items_of_movies_lists =new ArrayList<>();
        items_of_series_lists =new ArrayList<>();
        items_of_animations_lists =new ArrayList<>();
        items_of_documentaries_lists =new ArrayList<>();
        items_of_plays_lists =new ArrayList<>();

        move_to_about_all_activity_intent=new Intent(getContext(), About_all_activity.class);
        move_to_about_all_activity_bundle=new Bundle();

        db=new Sqlite_Database_Connection(getContext());




        //dialog=new My_Flower_progress_dialog(getContext());


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);



        initViews(v);

        //dialog=new My_Flower_progress_dialog(getActivity().getBaseContext());



        adds_image_slider=(ImageSlider)v.findViewById(R.id.home_fragment_image_slider);
        adds_image_slider.setImageList(image_slider_list,true);

        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.home_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_golden));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getActivity().recreate();
            }
        });


/*
       recycle_view_list_adapter=new Post_recycle_view_list_adapter(getContext(), items_of_series_lists);
        movies_recycle_view.setAdapter(recycle_view_list_adapter);
        movies_recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastItem=((LinearLayoutManager)movies_recycle_view.getLayoutManager()).findFirstVisibleItemPosition();

                if(lastItem==recyclerView.getAdapter().getItemCount()-1){
                    Toast.makeText(getContext(), String.valueOf(lastItem), Toast.LENGTH_SHORT).show();
                    limit=movies_recycle_view.getAdapter().getItemCount()+1;
                    startRetrofitForGetLastMovies(limit);
                }
            }
        });

 */


        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startRetrofitForGetAllAdds();

        startRetrofitForGetLastMovies("MOVIES");
        startRetrofitForGetLastSeries("SERIES");
        startRetrofitForGetLastAnimations("ANIMATIONS");
        startRetrofitForGetLastPlays("PLAYS");
        startRetrofitForGetLastDocumentries("DOCUMENTARIES");
        startRetrofitForGetLastOthers("OTHERS");

    }

    private void initViews(View v){
        movies_recycle_view =(RecyclerView) v.findViewById(R.id.movies_recycle_view);
        series_recycle_view=(RecyclerView)v.findViewById(R.id.series_recycle_view);
        animations_recycle_view=(RecyclerView)v.findViewById(R.id.animation_recycle_view);
        plays_recycle_view =(RecyclerView)v.findViewById(R.id.plays_recycle_view);
        documentaries_recycle_view=(RecyclerView)v.findViewById(R.id.documentaries_recycle_view);
        others_recycle_view=(RecyclerView)v.findViewById(R.id.others_recycle_view);

    }

    private void startRetrofitForGetAllAdds(){
        Retrofit_connection.getRetrofit_connection_instance().getAllAdds().enqueue(new Callback<List<Items_of_advertisements>>() {
            @Override
            public void onResponse(Call<List<Items_of_advertisements>> call, Response<List<Items_of_advertisements>> response) {
                if(response.isSuccessful()){
                    item_Of_Advertisements =(ArrayList<Items_of_advertisements>)response.body();

                    for(Items_of_advertisements adds : item_Of_Advertisements){

                        image_slider_list.add(new SlideModel("http://goldenbeachye.com/"+adds.getName_of_image_of_add(),adds.getDescription()));
                        adds_image_slider.setImageList(image_slider_list,false);


                    }

                }
            }

            @Override
            public void onFailure(Call<List<Items_of_advertisements>> call, Throwable t) {
                //Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void startRetrofitForGetLastMovies(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_movies_lists = (ArrayList<Items_of_all_list>) response.body();
                    movies_adapter=new All_recycle_view_list_adapter(getContext(), items_of_movies_lists);
                    movies_recycle_view.setAdapter(movies_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                //dialog.dismiss();
            }
        });

    }

    private void startRetrofitForGetLastSeries(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_series_lists = (ArrayList<Items_of_all_list>) response.body();
                    series_adapter=new All_recycle_view_list_adapter(getContext(), items_of_series_lists);
                    series_recycle_view.setAdapter(series_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                //dialog.dismiss();
            }
        });

    }

    /*
     try {
                    retrofit_on_failure_actions =new Retrofit_on_failure_actions(getContext());
                    retrofit_on_failure_actions.showAction2(t.getMessage());
                }catch (Exception e){
                    Toast.makeText(getContext(), "catch exception", Toast.LENGTH_SHORT).show();
                }
     */
    private void startRetrofitForGetLastAnimations(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_animations_lists = (ArrayList<Items_of_all_list>) response.body();
                    animation_adapter=new All_recycle_view_list_adapter(getContext(), items_of_animations_lists);
                    animations_recycle_view.setAdapter(animation_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

            }
        });

    }

    private void startRetrofitForGetLastDocumentries(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_documentaries_lists = (ArrayList<Items_of_all_list>) response.body();
                    documentaries_adapter=new All_recycle_view_list_adapter(getContext(), items_of_documentaries_lists);
                    documentaries_recycle_view.setAdapter(documentaries_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                //dialog.dismiss();
            }
        });

    }

    private void startRetrofitForGetLastPlays(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_plays_lists = (ArrayList<Items_of_all_list>) response.body();
                    plays_adapter =new All_recycle_view_list_adapter(getContext(), items_of_plays_lists);
                    plays_recycle_view.setAdapter(plays_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                //dialog.dismiss();
            }
        });

    }


    private void startRetrofitForGetLastOthers(String name_of_section){
        //dialog.start_dialog();
        Retrofit_connection.getRetrofit_connection_instance().getLast30OfAllHomeFragment(name_of_section).enqueue(new Callback<List<Items_of_all_list>>() {
            @Override
            public void onResponse(Call<List<Items_of_all_list>> call, Response<List<Items_of_all_list>> response) {
                if(response.isSuccessful()){
                    items_of_others_lists = (ArrayList<Items_of_all_list>) response.body();
                    others_adapter =new All_recycle_view_list_adapter(getContext(), items_of_others_lists);
                    others_recycle_view.setAdapter(others_adapter);

                    /*
                    for(Items_of_all_list items :items_of_movies_lists){
                        items_of_series_lists.add(new Items_of_all_list(items.name_of_section,items.brand_of_section,items.name_of_category,items.name,items.name_of_image,items.story,items.trailer,items.id,items.year,items.session));
                    }

                     */


                    //dialog.dismiss();

                }
                // recycle_view_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Items_of_all_list>> call, Throwable t) {

                //dialog.dismiss();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        //dialog.start_dialog();
        if(REFRESH_CODE !=0){
            getActivity().recreate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        REFRESH_CODE =2;

        //Toast.makeText(getContext(), "paused", Toast.LENGTH_SHORT).show();
        //HomeFragment context=HomeFragment.this;
        //getFragmentManager().beginTransaction().detach(context).attach(context).commit();

    }




    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Toast.makeText(getContext(), "attach", Toast.LENGTH_SHORT).show();
        REFRESH_CODE =0;


    }

}